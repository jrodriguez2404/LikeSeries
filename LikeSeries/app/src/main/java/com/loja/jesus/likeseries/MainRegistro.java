package com.loja.jesus.likeseries;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class MainRegistro extends AppCompatActivity {
    //Registro
    private Button registrar;
    private EditText temailregistro, tcontrasenaregistro, tnombre;
    //Mensajes y acuerdos
    private CheckBox acuerdolegal;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro);
        PersistenciaFirebase p = new PersistenciaFirebase();
        Toolbar appToolbar = (Toolbar) findViewById(R.id.appbar);
// *************************************************
// Pongo el titulo en la toolbar
        appToolbar.setTitle(getResources().getString(R.string.REGISTRO));
// Asigno la flecha de atras a la toolbar
        appToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
// Hago que cuando se pulse la flecha de atras se cierre la actividad
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tnombre = findViewById(R.id.nombre);
        temailregistro = findViewById(R.id.temailregistro);
        tcontrasenaregistro = findViewById(R.id.tcontrasenaregistro);


        acuerdolegal = findViewById(R.id.acuerdolegal);




        registrar = findViewById(R.id.registrar);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Partimos de que el acuerdo legal esta checheado
                if (!temailregistro.getText().toString().equals("")) {
                    if (!tcontrasenaregistro.getText().toString().equals("")) {
                        if (acuerdolegal.isChecked() == true) {
                            //Registramos un usuario con firebase
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
                                                    if(array.contains(tnombre.getText().toString()))
                                                    {
                                                        Toast.makeText(getApplicationContext(),
                                                                "Este nombre ya esta elegido", Toast.LENGTH_LONG).show();
                                                        tnombre.setText("");
                                                    }
                                                    else {
                                                        try {
                                                            registrarUsuarioFirebase(temailregistro.getText().toString(), tcontrasenaregistro.getText().toString(), tnombre.getText().toString(), 0);
                                                        } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                                                            Toast.makeText(getApplicationContext(),
                                                                    "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                }
                                            }
                                        });







                        }
                        //Este else simplemente ejecuta un Toast que indica que el acuerdo legal es necesario
                        else {

                            tcontrasenaregistro.setText("");
                            Snackbar.make(getCurrentFocus(), getResources().getString(R.string.acuerdolegal), Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.CYAN)
                                    .setActionTextColor(Color.GRAY)
                                    .show();
                        }
                    } else {
                        tcontrasenaregistro.setText("");
                        Snackbar.make(getCurrentFocus(), getResources().getString(R.string.contraseñarequerida), Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.CYAN)
                                .setActionTextColor(Color.GRAY)
                                .show();
                    }
                } else {
                    tcontrasenaregistro.setText("");
                    Snackbar.make(getCurrentFocus(), getResources().getString(R.string.emailrequerido), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.CYAN)
                            .setActionTextColor(Color.GRAY)
                            .show();
                }
            }
        });
    }

    /**
     * Registra el usuario , el valor de administrador se da directamente por el Creador
     * @param email
     * @param password
     * @param nombre
     * @param administrador
     */
    public void registrarUsuarioFirebase(String email , String password, final String nombre, final int administrador) throws LikeSeriesExceptionClass{
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplication(), SplashScreen.class);
                            intent.putExtra("cambioclase", false);
                            try {
                                enviarAuthEmail1(user.getUid(), nombre, administrador);
                            } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                                Toast.makeText(getApplicationContext(),
                                        "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                            }
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nombre)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                            }
                                        }
                                    });

                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                Snackbar.make(getCurrentFocus(), getResources().getString(R.string.contraseñadebil), Snackbar.LENGTH_LONG)
                                        .show();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Snackbar.make(getCurrentFocus(), getResources().getString(R.string.emailinvalido), Snackbar.LENGTH_LONG)
                                        .show();
                            } catch (FirebaseAuthUserCollisionException e) {
                                Snackbar.make(getCurrentFocus(), getResources().getString(R.string.usuarioregistrado), Snackbar.LENGTH_LONG)
                                        .show();

                            } catch (FirebaseAuthInvalidUserException e) {
                                Snackbar.make(getCurrentFocus(), getResources().getString(R.string.usuarioinvalido), Snackbar.LENGTH_LONG)
                                        .setActionTextColor(Color.CYAN)
                                        .setActionTextColor(Color.CYAN)
                                        .show();
                            } catch (Exception e) {
                                Snackbar.make(getCurrentFocus(), getResources().getString(R.string.errousuario), Snackbar.LENGTH_LONG)
                                        .setActionTextColor(Color.CYAN)
                                        .setActionTextColor(Color.GRAY)
                                        .show();
                            }
                        }
                    }
                });
    }

    /**
     * Envia un correo de verificación , a parte inserta los datos en la BD , no obstante hasta que no nos registremos no podemos entrar
     * @param uid
     */
    private void enviarAuthEmail1(final String uid, final String nombre, final int administrador) throws LikeSeriesExceptionClass {
        user = mAuth.getInstance().getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Creo un usuario y lo agrego a la base de datos de FireBase Cloud


                            Usuario usuario = new Usuario(uid,nombre, user.getEmail(),administrador,0,0);

                            try {
                                insertarBasedeDatosFireBaseUsuario(usuario);
                            } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                                Toast.makeText(getApplicationContext(),
                                        "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                            }


                            //Cierro la actividad
                            finish();

                        }
                    }
                });
    }
    /**
     * Este metodo se encargara de guardar nuestros datos registrados en la nube
     * @param usuario
     */
    private void insertarBasedeDatosFireBaseUsuario(Usuario usuario) throws LikeSeriesExceptionClass{
        // Creamos un usuario y lo guardamos en la base de datos
        db = FirebaseFirestore.getInstance();
        db.collection("usuarios").document(usuario.getUID())
                .set(usuario, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
}
