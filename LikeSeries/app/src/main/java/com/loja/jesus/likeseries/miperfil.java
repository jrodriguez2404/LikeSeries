package com.loja.jesus.likeseries;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class miperfil extends AppCompatActivity {
private TextView nombre_miperfil,email_miperfil;
private Button contrasenaolvidada;
private FirebaseUser user;
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
        contexto=this;
        Toolbar appToolbar = (Toolbar) findViewById(R.id.toolbar);
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
                        user = mAuth.getCurrentUser();
                        db = FirebaseFirestore.getInstance();
                        DocumentReference docRef = db.collection("usuarios").document(user.getUid());
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
    }
    @Override
    protected void onStart() {
        super.onStart();
        llamarinsertardatosUsuario();
    }

    private void llamarinsertardatosUsuario()
    {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("usuarios").document(user.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            @SuppressLint("StringFormatMatches")
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario usuario = documentSnapshot.toObject(Usuario.class);
                nombre_miperfil.setText(getResources().getString(R.string.nombre_perfil,usuario.getNombre()));
                email_miperfil.setText(getResources().getString(R.string.email_perfil,usuario.getEmail()));
            }
        });
}
}