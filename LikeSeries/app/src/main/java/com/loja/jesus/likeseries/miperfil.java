package com.loja.jesus.likeseries;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class miperfil extends AppCompatActivity {
private TextView nombre_miperfil,email_miperfil;
private Button contrasenaolvidada,cambiarnombre;
private FirebaseUser usuario;
private FirebaseAuth mAuth;
private FirebaseFirestore db;
Context contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miperfil);
        nombre_miperfil=findViewById(R.id.nombre_perfil);
        email_miperfil=findViewById(R.id.email_perfil);
        contrasenaolvidada = findViewById(R.id.olvido_miperfil);
        cambiarnombre=findViewById(R.id.cambiarnombre_perfil);
        contexto=this;
        Toolbar appToolbar = (Toolbar) findViewById(R.id.appbar);
// *************************************************
// Pongo el titulo en la toolbar
        appToolbar.setTitle(getResources().getString(R.string.miperfil));
// Asigno la flecha de atras a la toolbar
        appToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
// Hago que cuando se pulse la flecha de atras se cierre la actividad
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        contrasenaolvidada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                builder.setTitle(getResources().getString(R.string.cambiarcontraseña));
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mAuth = FirebaseAuth.getInstance();
                        usuario = mAuth.getCurrentUser();
                        db = FirebaseFirestore.getInstance();
                        DocumentReference docRef = db.collection("usuarios").document(usuario.getUid());
                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            @SuppressLint("StringFormatMatches")
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Usuario usuario = documentSnapshot.toObject(Usuario.class);
                                mAuth.sendPasswordResetEmail(usuario.getEmail())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    Snackbar.make(v.getRootView(), "Email enviado correctamente", Snackbar.LENGTH_LONG)
                                                            .setActionTextColor(Color.CYAN)
                                                            .setActionTextColor(Color.GRAY)
                                                            .show();
                                                }
                                            }
                                        });
                            }
                        });
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                builder.show();
            }
        });
        cambiarnombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                final View view = getLayoutInflater().inflate(R.layout.cambiarnombre, null);
                builder.setView(view);
                builder.setTitle(getResources().getString(R.string.cambiarnombre_perfil));
                final EditText nombrecambiar = view.findViewById(R.id.cogernombre);
                final AlertDialog alerta = builder.create();
                final Button solicitarcambio = view.findViewById(R.id.botonenviar);
                solicitarcambio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        mAuth = FirebaseAuth.getInstance();
                        db = FirebaseFirestore.getInstance();
                        usuario=mAuth.getCurrentUser();
                        db = FirebaseFirestore.getInstance();
                        db.collection("usuarios")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            ArrayList<String> array = new ArrayList<>();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Usuario user = document.toObject(Usuario.class);
                                                array.add(user.getNombre());
                                            }
                                            if(array.contains(nombrecambiar.getText().toString()))
                                            {
                                                Toast.makeText(getApplicationContext(),
                                                        "Este nombre ya esta exite", Toast.LENGTH_LONG).show();
                                                nombrecambiar.setText("");
                                            }
                                            else {
                                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                        .setDisplayName(nombrecambiar.getText().toString())
                                                        .build();

                                                usuario.updateProfile(profileUpdates)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                }
                                                            }
                                                        });
                                                db.collection("usuarios").document(usuario.getUid())
                                                        .update(
                                                                "nombre", nombrecambiar.getText().toString()
                                                        );
                                                nombrecambiar.setText("");
                                                alerta.dismiss();
                                                Toast.makeText(getApplicationContext(),
                                                        "Nombre cambiado correctamente", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                });


                            }
                        });

                        alerta.show();
                    }
                });
            }

    @Override
    protected void onStart() {
        super.onStart();
        llamarinsertardatosUsuario();
    }

    private void llamarinsertardatosUsuario()
    {
        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("usuarios").document(usuario.getUid());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    Usuario usuario = snapshot.toObject(Usuario.class);
                    nombre_miperfil.setText(getResources().getString(R.string.nombre_perfil,usuario.getNombre()));
                    email_miperfil.setText(getResources().getString(R.string.email_perfil,usuario.getEmail()));
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
}
}