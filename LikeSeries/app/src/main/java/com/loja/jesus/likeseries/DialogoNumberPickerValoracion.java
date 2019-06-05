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
    private FirebaseUser user;
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
                AlertDialog.Builder builder = new AlertDialog.Builder(dialogo.getContext());
                builder.setMessage(R.string.estaseguro);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mAuth = FirebaseAuth.getInstance();
                        db = FirebaseFirestore.getInstance();
                        user= mAuth.getCurrentUser();

                        DocumentReference docRef = db.collection(collection).document(idcontenido);
                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                ArrayList<Votacion_media> votapersona = new ArrayList<>();
                                Pelicula peli = documentSnapshot.toObject(Pelicula.class);
                                Serie ser = documentSnapshot.toObject(Serie.class);
                                Votacion_media vm;
                                try {
                                    if (collection.equals("peliculas")) {
                                        Boolean noentrarmas = false;
                                        double votacionmedia = 0;
                                        for (int i = 0; i < peli.getVotacion_media().size(); i++) {
                                            //Aqui se insertan los que no son del usuario
                                                if(peli.getVotacion_media().get(i).getUid().equals(user.getUid())&&peli.getVotacion_media().get(i).getVotado()==1)
                                                {
                                                    vm = new Votacion_media(user.getUid(), valoracion.getValue(),1);
                                                    votapersona.add(vm);
                                                    noentrarmas=true;

                                                }
                                                if(!peli.getVotacion_media().get(i).getUid().equals(user.getUid())&&peli.getVotacion_media().get(i).getVotado()==1)
                                                {
                                                    vm = new Votacion_media(peli.getVotacion_media().get(i).getUid(), peli.getVotacion_media().get(i).getNota(),peli.getVotacion_media().get(i).getVotado());
                                                    votapersona.add(vm);
                                                }


                                        }

                                        if (peli.getVotacion_media().size() == 0) {
                                            vm = new Votacion_media(user.getUid(), valoracion.getValue(),1);
                                            votapersona.add(vm);
                                            noentrarmas=true;

                                        }
                                        if(noentrarmas==false)
                                        {
                                            vm = new Votacion_media(user.getUid(), valoracion.getValue(),1);
                                            votapersona.add(vm);
                                        }



                                        for (int x = 0; x < votapersona.size(); x++) {
                                            votacionmedia += votapersona.get(x).getNota();
                                        }
                                        votacionmedia = votacionmedia / votapersona.size();
                                        Pelicula pelicula = new Pelicula(peli.getCollection_Pelicula(), idcontenido, peli.getTitulo_Pelicula(), peli.getDescripcion_Pelicula(), peli.getProductora_Pelicula(), peli.getDirector_Pelicula(), peli.getFechaEstreno_Pelicula(), peli.getTrailer_Pelicula(), peli.getDuración_Pelicula(), peli.getGenero_Pelicula(), peli.getImagen_Pelicula(), peli.getVotosPositivos_Pelicula(), peli.getVotosNegativos_Pelicula(),votapersona.size(), votacionmedia, peli.getVotosusuarios(), peli.getComentarios(), votapersona,peli.getActores_peliculas());
                                        db.collection("peliculas").document(pelicula.getID_Pelicula()).set(pelicula);


                                    } else if (collection.equals("series")) {
                                        double votacionmedia = 0;
                                        for (int i = 0; i < ser.getVotacion_media().size(); i++) {
                                            //Aqui se insertan los que no son del usuario
                                            if(ser.getVotacion_media().size()>0)
                                            {
                                                if(ser.getVotacion_media().get(i).getUid().equals(user.getUid()))
                                                {
                                                    vm = new Votacion_media(user.getUid(), valoracion.getValue(),1);
                                                    votapersona.add(vm);

                                                }
                                                if(!ser.getVotacion_media().get(i).getUid().equals(user.getUid())&&ser.getVotacion_media().get(i).getVotado()==1)
                                                {
                                                    vm = new Votacion_media(peli.getVotacion_media().get(i).getUid(), peli.getVotacion_media().get(i).getNota(),peli.getVotacion_media().get(i).getVotado());
                                                    votapersona.add(vm);
                                                }

                                            }


                                        }
                                        if (ser.getVotacion_media().size() == 0) {
                                            vm = new Votacion_media(user.getUid(), valoracion.getValue(),1);
                                            votapersona.add(vm);
                                        }


                                        for (int x = 0; x < votapersona.size(); x++) {
                                            votacionmedia += votapersona.get(x).getNota();

                                        }
                                        votacionmedia = votacionmedia / votapersona.size();
                                        Serie serie = new Serie(ser.getCollection_Serie(), idcontenido, ser.getTitulo_Serie(), ser.getDescripcion_Serie(), ser.getProductora_Serie(), ser.getDirector_Serie(), ser.getPrimeraEmision_Serie(), ser.getTrailer_Serie(), ser.getDuración_Serie(), ser.getGenero_Serie(), ser.getImagen_Serie(), ser.getVotosPositivos_Serie(), ser.getVotosNegativos_Serie(), votapersona.size(), ser.getNCapitulos(), votacionmedia, ser.getVotosusuarios(), ser.getComentarios(), votapersona,ser.getActores_series());
                                        db.collection("series").document(ser.getID_Serie()).set(serie);
                                    }

                                }catch(Exception e)
                                {

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


