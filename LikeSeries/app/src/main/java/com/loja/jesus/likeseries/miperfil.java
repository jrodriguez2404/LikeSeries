package com.loja.jesus.likeseries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class miperfil extends AppCompatActivity {
private TextView nombre_miperfil,email_miperfil;
private FirebaseUser user;
private FirebaseAuth mAuth;
private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miperfil);
        nombre_miperfil=findViewById(R.id.nom_miperfil);
        email_miperfil=findViewById(R.id.em_miperfil);
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
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario usuario = documentSnapshot.toObject(Usuario.class);
                nombre_miperfil.setText(usuario.getNombre());
                email_miperfil .setText(usuario.getEmail());
            }
        });
}
}