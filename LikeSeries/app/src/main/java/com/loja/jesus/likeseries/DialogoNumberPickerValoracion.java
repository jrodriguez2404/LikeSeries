package com.loja.jesus.likeseries;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DialogoNumberPickerValoracion {
    private NumberPicker valoracion;
    private Button aceptarvaloracion;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public interface finalizarDialog {
        void resultado(int num);
    }

    private Class<ContenedorMultimedia> interfaz;

    public DialogoNumberPickerValoracion(Context contexto, Class<ContenedorMultimedia> actividad, final ImageView imagenvoto, final String collection, final String idcontenido) {

        interfaz = actividad;
        final Dialog dialogo = new Dialog(contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setContentView(R.layout.numberpicker_valoracion);


        // NumberPicker's:
        valoracion = dialogo.findViewById(R.id.valoracion);
        valoracion.setMinValue(0);
        valoracion.setMaxValue(10);
        aceptarvaloracion=dialogo.findViewById(R.id.aceptarvotacion);
        aceptarvaloracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("hola , me han elegido como ,"+valoracion.getValue());

                AlertDialog.Builder builder = new AlertDialog.Builder(dialogo.getContext());
                builder.setMessage(R.string.estaseguro);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        mAuth = FirebaseAuth.getInstance();
                        db = FirebaseFirestore.getInstance();

                        DocumentReference docRef = db.collection(collection).document(idcontenido);
                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(collection.equals("peliculas"))
                                {
                                    Pelicula peli = documentSnapshot.toObject(Pelicula.class);
                                    int votante = peli.getVotantes_Pelicula();
                                    votante++;
                                    if(votante==1)
                                    {
                                        Pelicula pelicula = new Pelicula(peli.getCollection_Pelicula(),idcontenido, peli.getTitulo_Pelicula(), peli.getDescripcion_Pelicula(), peli.getProductora_Pelicula(), peli.getGenero_Pelicula(),peli.getImagen_Pelicula(), peli.getVotosPositivos_Pelicula(),peli.getVotosNegativos_Pelicula(), votante,valoracion.getValue(), peli.getVotosusuarios(), peli.getComentarios());
                                        db.collection("peliculas").document(pelicula.getID_Pelicula()).set(pelicula);
                                    }
                                    else if (votante >1)
                                    {
                                        int media=((int)peli.getNotamedia_Pelicula()+valoracion.getValue())/votante;
                                        Pelicula pelicula = new Pelicula(peli.getCollection_Pelicula(),idcontenido, peli.getTitulo_Pelicula(), peli.getDescripcion_Pelicula(), peli.getProductora_Pelicula(), peli.getGenero_Pelicula(),peli.getImagen_Pelicula(), peli.getVotosPositivos_Pelicula(),peli.getVotosNegativos_Pelicula(), votante,media, peli.getVotosusuarios(), peli.getComentarios());
                                        db.collection("peliculas").document(pelicula.getID_Pelicula()).set(pelicula);
                                    }
                                }
                                else if(collection.equals("series"))
                                {
                                    Serie ser =documentSnapshot.toObject(Serie.class);
                                    int votante = ser.getVotantes_Serie();
                                    votante++;
                                    if(votante==1)
                                    {
                                        Serie serie = new Serie(ser.getCollection_Serie(),idcontenido, ser.getTitulo_Serie(), ser.getDescripcion_Serie(), ser.getProductora_Serie(), ser.getGenero_Serie(),ser.getImagen_Serie(), ser.getVotosPositivos_Serie(),ser.getVotosNegativos_Serie(), votante,ser.getNCapitulos(),valoracion.getValue(), ser.getVotosusuarios(), ser.getComentarios());
                                        db.collection("series").document(ser.getID_Serie()).set(serie);
                                    }
                                    else if (votante >1)
                                    {
                                        int media=((int)ser.getNotamedia_Serie()+valoracion.getValue())/votante;
                                        Serie serie = new Serie(ser.getCollection_Serie(),idcontenido, ser.getTitulo_Serie(), ser.getDescripcion_Serie(), ser.getProductora_Serie(), ser.getGenero_Serie(),ser.getImagen_Serie(), ser.getVotosPositivos_Serie(),ser.getVotosNegativos_Serie(), votante,ser.getNCapitulos(),media, ser.getVotosusuarios(), ser.getComentarios());
                                        db.collection("series").document(ser.getID_Serie()).set(serie);
                                    }
                                }

                            }
                        });




                        imagenvoto.setImageResource(R.drawable.estrella_on);
                        dialogo.dismiss();
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
        dialogo.show();
    }



}


