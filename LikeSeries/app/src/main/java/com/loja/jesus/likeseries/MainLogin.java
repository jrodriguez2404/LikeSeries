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
    private Button isesion,iregistro,iolvidar,inoverificado;
    //Login
    private View view;
    private EditText temail, tcontra;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        Toolbar appToolbar = (Toolbar) findViewById(R.id.appbar);
// *************************************************
// Pongo el titulo en la toolbar
        appToolbar.setTitle(getResources().getString(R.string.LOGIN));
        view=this.getCurrentFocus();
        isesion = findViewById(R.id.login);
        iolvidar = findViewById(R.id.olvidarcontraseña);
        inoverificado = findViewById(R.id.sielusuarionoestaverificado);
        inoverificado.setVisibility(View.GONE);
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
                                try {
                                    logearUsuarioFirebase(temail.getText().toString(), tcontra.getText().toString());
                                } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                                    Toast.makeText(getApplicationContext(),
                                            "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                                }
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
                try {
                    updateUI();
                } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                    Toast.makeText(getApplicationContext(),
                            "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                }
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
    private void logearUsuarioFirebase(final String email, final String password) throws LikeSeriesExceptionClass {
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
                                try {
                                    updateUI();
                                } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                                    Toast.makeText(getApplicationContext(),
                                            "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                try {
                                    Toast.makeText(getApplicationContext(),
                                            "Usuario no verificado", Toast.LENGTH_LONG).show();
                                    inoverificado.setVisibility(View.VISIBLE);

                                        inoverificado.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(!user.isEmailVerified()) {
                                                    Toast.makeText(getApplicationContext(),
                                                            "Verificacion enviada correctamente", Toast.LENGTH_LONG).show();
                                                    user.sendEmailVerification();
                                                }
                                                else
                                                {
                                                    Toast.makeText(getApplicationContext(),
                                                            "Usted ya ha verificado su cuenta", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }


                        } else {
                            // No se registra correctamente
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            try {
                                throw task.getException();

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(),
                                        "Error al logear usuario", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
    /**
     * Metodo encargado de abrir la SplashScrren

     */
    private void abrirSplashScreen() throws LikeSeriesExceptionClass
    {
        Intent intent = new Intent(getApplicationContext(),SplashScreen.class);
        intent.putExtra("cambioclase", true);
        startActivity(intent);
    }
    /**
     * Método que se encarga de abrir la nueva pantalla de la actividad principal una vez logeado
     */
    private void updateUI() throws LikeSeriesExceptionClass
    {
        abrirSplashScreen();
        cerrarPagina();
    }
}
