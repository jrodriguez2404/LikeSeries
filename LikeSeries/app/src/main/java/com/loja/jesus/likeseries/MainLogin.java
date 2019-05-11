package com.loja.jesus.likeseries;


import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

public class MainLogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    //Declaración de elementos del login
    private Button isesion,iregistro,iolvidar;
    //Login
    private EditText temail, tcontra;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        Toolbar appToolbar = (Toolbar) findViewById(R.id.appbar);
// *************************************************
// Pongo el titulo en la toolbar
        appToolbar.setTitle(getResources().getString(R.string.LOGIN));
        isesion = findViewById(R.id.login);
        iolvidar = findViewById(R.id.olvidarcontraseña);
        temail = findViewById(R.id.temail);
        tcontra = findViewById(R.id.tcontrasena);
        iregistro=findViewById(R.id.registro);
        iregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainRegistro.class);
                startActivity(intent);
            }
        });
        isesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if (!temail.getText().toString().equals("")) {
                            if (!tcontra.getText().toString().equals("")) {
                                logearUsuarioFirebase(temail.getText().toString(), tcontra.getText().toString());
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Contraseña vacia", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Email vacio", Toast.LENGTH_LONG).show();
                            tcontra.setText("");
                        }
            }
        });
        iolvidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                final View view = getLayoutInflater().inflate(R.layout.olvidarcontrasena, null);
                builder.setView(view);
                builder.setTitle(getResources().getString(R.string.olvidocontraseña));
                builder.setMessage(getResources().getString(R.string.correo));
                final TextView erroresemail=view.findViewById(R.id.erroresemail);
                final EditText emailolvido = view.findViewById(R.id.cogeremailolvido);
                final AlertDialog alerta = builder.create();
                final Button enviarolvido = view.findViewById(R.id.botonenviar);
                enviarolvido.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        mAuth = FirebaseAuth.getInstance();
                        String email = emailolvido.getText().toString();
                    try
                    {
                        mAuth.sendPasswordResetEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Snackbar.make(v.getRootView(), getResources().getString(R.string.enviado), Snackbar.LENGTH_LONG)
                                                    .show();
                                            alerta.dismiss();

                                        }
                                        else
                                        {
                                            try {
                                                throw task.getException();
                                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                                erroresemail.setVisibility(View.VISIBLE);
                                                erroresemail.setText(R.string.emailmalformado);
                                            }  catch (Exception e) {
                                                erroresemail.setVisibility(View.VISIBLE);
                                                erroresemail.setText(R.string.errorgeneralemail);
                                            }
                                        }
                                            }
                                });
                    }
                    catch (IllegalArgumentException e)
                    {
                        erroresemail.setVisibility(View.VISIBLE);
                        erroresemail.setText(R.string.emailnovacio);
                    }
                    }
                });
                alerta.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (user.isEmailVerified()) {
                updateUI();
            }

    }
    }public void cerrarPagina()
    {
        finish();
    }
    /**
     * Método principal encargado de integrar al usuario en la aplicación si todos los valores son correctos y si el email de verificación esta true
     * @param email
     * @param password
     */
    public void logearUsuarioFirebase(final String email, final String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String TAG = "";
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            user = mAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()) {
                                updateUI();

                            }
                            else
                            {
                                Snackbar.make(getCurrentFocus(), "¿Email no verificado , reenviar email?", Snackbar.LENGTH_INDEFINITE)
                                        .setActionTextColor(Color.CYAN)
                                        .setActionTextColor(Color.GRAY)
                                        .setAction("Aceptar", new View.OnClickListener() {
                                            @Override
                                           public void onClick(View view) {

                                                user.sendEmailVerification();
                                            }
                                        })
                                        .show();
                            }


                        } else {
                            // No se registra correctamente
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            try {
                                throw task.getException();

                            } catch (Exception e) {
                                Snackbar.make(getCurrentFocus(), "Error al logear usuario", Snackbar.LENGTH_LONG)
                                        .setActionTextColor(Color.CYAN)
                                        .setActionTextColor(Color.GREEN)
                                        .show();
                            }
                        }
                    }
                });
    }
    /**
     * Metodo encargado de abrir la SplashScrren

     */
    private void abrirSplashScreen()
    {
        Intent intent = new Intent(getApplicationContext(),SplashScreen.class);
        intent.putExtra("cambioclase", true);
        startActivity(intent);
    }
    /**
     * Método que se encarga de abrir la nueva pantalla de la actividad principal una vez logeado
     */
    private void updateUI()
    {
        abrirSplashScreen();
        cerrarPagina();
    }
}
