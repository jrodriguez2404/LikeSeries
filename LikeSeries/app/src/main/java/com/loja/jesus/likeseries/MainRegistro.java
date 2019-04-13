package com.loja.jesus.likeseries;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import static android.support.constraint.Constraints.TAG;

public class MainRegistro extends AppCompatActivity {
    //Registro
    private Button registrar;
    private EditText temailregistro, tcontrasenaregistro, tnombre;
    //Mensajes y acuerdos
    private CheckBox recibir, acuerdolegal;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro);

        Toolbar appToolbar = (Toolbar) findViewById(R.id.toolbar);
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
        recibir = findViewById(R.id.mensajes);




        registrar = findViewById(R.id.registrar);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Partimos de que el acuerdo legal esta checheado
                if (!temailregistro.getText().toString().equals("")) {
                    if (!tcontrasenaregistro.getText().toString().equals("")) {
                        if (acuerdolegal.isChecked() == true) {
                            //Registramos un usuario con firebase
                            registrarUsuarioFirebase(temailregistro.getText().toString(), tcontrasenaregistro.getText().toString(), tnombre.getText().toString(), recibir.isChecked());


                        }
                        //Este else simplemente ejecuta un Toast que indica que el acuerdo legal es necesario , y pone el texto de color rojo
                        else {

                            tcontrasenaregistro.setText("");
                            Toast.makeText(getApplicationContext(),
                                    "Acuerdo Legal necesario", Toast.LENGTH_LONG).show();
                            acuerdolegal.setTextColor(getResources().getColor(R.color.rojo));
                        }
                    } else {
                        tcontrasenaregistro.setText("");
                        Toast.makeText(getApplicationContext(),
                                "Contraseña requerida", Toast.LENGTH_LONG).show();
                    }
                } else {
                    tcontrasenaregistro.setText("");
                    Toast.makeText(getApplicationContext(),
                            "Email requerido", Toast.LENGTH_LONG).show();
                    temailregistro.setTextColor(getResources().getColor(R.color.rojo));
                }
            }
        });
    }

    /**
     * Registra el usuario
     * @param email
     * @param password
     * @param nombre
     * @param recibir
     */
    public void registrarUsuarioFirebase(String email , String password, final String nombre, final Boolean recibir) {
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplication(), SplashScreen.class);
                            intent.putExtra("cambioclase", false);
                            startActivity(intent);
                            enviarAuthEmail1(user.getUid(), nombre, recibir);
                        }
                        else
                        {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Contraseña debil", Toast.LENGTH_LONG).show();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Email invalido", Toast.LENGTH_LONG).show();

                            } catch (FirebaseAuthUserCollisionException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Usuario ya registrado", Toast.LENGTH_LONG).show();

                            } catch (FirebaseAuthInvalidUserException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Usuario invalido", Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(),
                                        "Error al registrar usuario", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    /**
     * Envia un correo de verificación , a parte inserta los datos en la BD , no obstante hasta que no nos registremos no podemos entrar
     * @param uid
     */
    private void enviarAuthEmail1(final String uid, final String nombre, final Boolean recibir) {
        user = mAuth.getInstance().getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            //Creo un usuario y lo agrego a la base de datos de FireBase Cloud


                            Usuario usuario = new Usuario(nombre, user.getEmail(),uid ,recibir,0);

                            insertarBasedeDatosFireBaseUsuario(usuario);


                            //Cierro la actividad
                            MainLogin login = new MainLogin();
                            login.cerrarPagina();

                        }
                    }
                });
    }
    /**
     * Este metodo se encargara de guardar nuestros datos registrados en la nube
     *
     * @param usuario
     */
    private void insertarBasedeDatosFireBaseUsuario(Usuario usuario) {
        // Creamos un usuario y lo guardamos en la base de datos
        db = FirebaseFirestore.getInstance();
        db.collection("usuarios").document(usuario.getToken())
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
