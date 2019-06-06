package com.loja.jesus.likeseries;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static android.support.constraint.Constraints.TAG;

public class ContenedorMultimedia extends AppCompatActivity implements View.OnClickListener {
private TextView titulo_multi,descripcion_multi,genero0_multi,genero1_multi,genero2_multi,votopositivo,votonegativo,productora_multi,capitulos_multi ,director_multi,fechaestreno_multi,trailer_multi,duracion_multi,comentarios,mostrarmas,votacionmedia_multi,votantes_multi,compartir_multimedia,actor1_multimedia,actor2_multimedia,actor3_multimedia;
private ImageView imagenvotopositivo,imagenvotonegativos,imagen_multi,like,nolike;
private Boolean vnegativo,vpositivo,mostrarmas_boolean,negativo,positivo;
private Integer votantes,votomas,votomenos,usuariovotopositivo,usuariovotonegativo,capitulos;
private FirebaseUser user;
private FirebaseAuth mAuth;
private long media;
private FirebaseFirestore db;
private RecyclerView RVComentarios;
private ImageView imagenvoto;
private Context contexto = this;
private String titulo,descripcion,productora,imagen,id,reglas,director,fechaestreno,trailer,duracion,collection;
private ArrayList<Comentario> coment;
private ArrayList<Votos> votos;
private ArrayList<String> arrayusuariovoto,arrayusuarios,genero,actores;
private Intent intentlike;
private Button insertarcomentario;
private EditText cajacomentario;
private LinearLayout mostrar,linearlayoutactores;
private View dividercapitulos1,dividercapitulos2,divideractores;
 @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(intentlike);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_multimedia);
        try {
            declaraciones();
        } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
            Toast.makeText(getApplicationContext(),
                    "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
        }
        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        like=findViewById(R.id.like);
        nolike=findViewById(R.id.nolike);
        RVComentarios = findViewById(R.id.RVComentarios);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Like.class);
                startActivity(intentlike);
            }
        });


    }
public void refrescarRecyclers(final Pelicula peli , final Serie ser) throws LikeSeriesExceptionClass
    {
        final Intent intent = getIntent();
        if(intent.getStringExtra("pelioserie").equals("peliculas")) {
            db.collection("peliculas")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Pelicula pel = document.toObject(Pelicula.class);
                                    ArrayList<Pelicula> pe = new ArrayList<>();
                                    pe.add(pel);
                                    AdaptadorPeliculas adaptadorPeliculas = new AdaptadorPeliculas(contexto,pe,"");
                                    adaptadorPeliculas.addItem(peli,intent.getIntExtra("numero",0));

                                }
                            }
                        }
                    });

        }
        else {

            db.collection("series")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Serie ser = document.toObject(Serie.class);
                                    ArrayList<Serie> se = new ArrayList<>();
                                    se.add(ser);
                                    AdaptadorSeries adaptadorSeries = new AdaptadorSeries(contexto,se,"");
                                    adaptadorSeries.addItem(ser,intent.getIntExtra("numero",0));
                                }
                            }
                        }
                    });

        }

        }
    @SuppressLint("StringFormatMatches")
    private void actualizarRecyclerComentarios() throws LikeSeriesExceptionClass{
        AdaptadorComentarios adaptadorComentarios = new AdaptadorComentarios(this,collection,id,coment);
        RVComentarios.setAdapter(adaptadorComentarios);
        adaptadorComentarios.refrescar();
        comentarios.setText(getResources().getString(R.string.comentario,coment.size()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        PersistenciaFirebase p = new PersistenciaFirebase();
        p.persistenciaFirebase();
        mostrarmas_boolean=true;
        try {
            declaraciones();
            refrescarDatosPantalla();
        } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
            Toast.makeText(getApplicationContext(),
                    "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
        }
        if(reglas.equals("0"))
      {
          vpositivo=true;
          vnegativo=true;
          imagenvotopositivo.setEnabled(true);
          imagenvotonegativos.setEnabled(true);
          imagenvotopositivo.setImageResource(R.drawable.unlike);
          imagenvotonegativos.setImageResource(R.drawable.unnolike);
      }
      else if(reglas.equals("1"))
      {
          vpositivo=false;
          vnegativo=true;
          imagenvotopositivo.setEnabled(true);
          imagenvotonegativos.setEnabled(false);
          imagenvotopositivo.setImageResource(R.drawable.like);
          imagenvotonegativos.setImageResource(R.drawable.unnolike);
      }
      else if(reglas.equals("2"))
      {
          vpositivo=true;
          vnegativo=false;
          imagenvotopositivo.setEnabled(false);
          imagenvotonegativos.setEnabled(true);
          imagenvotopositivo.setImageResource(R.drawable.unlike);
          imagenvotonegativos.setImageResource(R.drawable.nolike);
      }
        votopositivo.setText(votomas.toString());
        votonegativo.setText(votomenos.toString());
    }

    private void declaraciones() throws LikeSeriesExceptionClass
    {
        dividercapitulos1 = findViewById(R.id.dividercapitulos1);
        dividercapitulos2 = findViewById(R.id.dividercapitulos2);
        divideractores = findViewById(R.id.divideractores);
        compartir_multimedia=findViewById(R.id.compartirresultado);
        votantes_multi = findViewById(R.id.votantes);
        votacionmedia_multi = findViewById(R.id.votacionmedia);
        mostrar=findViewById(R.id.mostrar);
        mostrarmas = findViewById(R.id.mostrarmasomenos);
        mostrarmas.setOnClickListener(this);
        cajacomentario = findViewById(R.id.cajaTexto);
        insertarcomentario = findViewById(R.id.botonmandarComentario);
        insertarcomentario.setOnClickListener(this);
        usuariovotopositivo=0;
        usuariovotonegativo=0;
        comentarios=findViewById(R.id.comentarios);
        coment=new ArrayList<>();
        votos = new ArrayList<>();
        intentlike = new Intent(this,Like.class);
        capitulos_multi = findViewById(R.id.capitulos_Multimedia);
        titulo_multi = findViewById(R.id.titulo_Multimedia);
        descripcion_multi = findViewById(R.id.descripcion_Multimedia);
        genero0_multi = findViewById(R.id.genero1_multimedia);
        genero1_multi = findViewById(R.id.genero2_multimedia);
        genero2_multi = findViewById(R.id.genero3_multimedia);
        imagen_multi =findViewById(R.id.imagen_Multimedia);
        imagenvotopositivo = findViewById(R.id.like);
        imagenvotonegativos = findViewById(R.id.nolike);
        votopositivo = findViewById(R.id.votoslike);
        votonegativo = findViewById(R.id.votosnolike);
        productora_multi = findViewById(R.id.productora_Multimedia);
        imagenvoto= findViewById(R.id.imagenvoto);
        imagenvoto.setOnClickListener(this);
        RVComentarios = findViewById(R.id.RVComentarios);
        director_multi = findViewById(R.id.Direccion_Multimedia);
        fechaestreno_multi= findViewById(R.id.fechaEstreno_Multimedia);
        trailer_multi= findViewById(R.id.trailer_Multimedia);
        trailer_multi.setOnClickListener(this);
        duracion_multi= findViewById(R.id.duracion_Multimedia);
        actor1_multimedia=findViewById(R.id.actor1_multimedia);
        actor2_multimedia=findViewById(R.id.actor2_multimedia);
        actor3_multimedia=findViewById(R.id.actor3_multimedia);
        linearlayoutactores=findViewById(R.id.linearlayoutactores);
        cogerExtras();
    }
    private void abrirCompartir()
    {
        db=FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(collection).document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            @SuppressLint("StringFormatMatches")
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(collection.equals("peliculas")) {
                    Pelicula peli = documentSnapshot.toObject(Pelicula.class);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TITLE, user.getDisplayName() + " te ha compartido la pelicula "+ peli.getTitulo_Pelicula() + " desde LikeSeries");
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, user.getDisplayName() + " te ha compartido la pelicula "+ peli.getTitulo_Pelicula() + " desde LikeSeries");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "*********************\n"+
                            "LIKESERIES RECOMIENDA\n"+
                            "*********************\n"+
                            user.getDisplayName() + " te ha compartido la pelicula "+ peli.getTitulo_Pelicula() + " desde LikeSeries."+"\n"+"Esta película tiene "+ peli.getVotosPositivos_Pelicula()  + " voto/s positivo/s y "+peli.getVotosNegativos_Pelicula() + " voto/s negativo/s. " + "\n" +
                            "Ademas está valorada en un "+peli.getNotamedia_Pelicula() + " de media , de "+peli.getVotantes_Pelicula()+" votante/s."+ "\n+"+"---------------------------------"+"\n" +
                            user.getDisplayName() + " gracias por compartir esta información con tus amigos");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
                else if(collection.equals("series"))
                {
                    Serie ser = documentSnapshot.toObject(Serie.class);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TITLE, user.getDisplayName() + " te ha compartido la serie "+ ser.getTitulo_Serie() + " desde LikeSeries");
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, user.getDisplayName() + " te ha compartido la serie "+ ser.getTitulo_Serie() + " desde LikeSeries");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "*********************\n"+
                            "LIKESERIES RECOMIENDA\n"+
                            "*********************\n"+
                            user.getDisplayName() + " te ha compartido la pelicula "+ ser.getTitulo_Serie() + " desde LikeSeries."+"\n"+"Esta serie tiene "+ ser.getVotosPositivos_Serie()  + " voto/s positivo/s y "+ser.getVotosNegativos_Serie() + " voto/s negativo/s. " + "\n" +
                            "Ademas está valorada en un "+ser.getNotamedia_Serie() + " de media , de "+ser.getVotantes_Serie()+" votante/s."+ "\n+"+"---------------------------------"+"\n" +
                            user.getDisplayName() + " gracias por compartir esta información con tus amigos");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
            }
        });
    }
    private void refrescarDatosPantalla() throws LikeSeriesExceptionClass
    {
        db=FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection(collection).document(id);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    if(collection.equals("peliculas")) {
                        Pelicula peli = snapshot.toObject(Pelicula.class);
                        comentarios.setText(getResources().getString(R.string.comentario,peli.getComentarios().size()));
                        DecimalFormat formato = new DecimalFormat("#.#");
                        votacionmedia_multi.setText(getResources().getString(R.string.votacionmedia,formato.format(peli.getNotamedia_Pelicula())));
                        votantes_multi.setText(getResources().getString(R.string.votantes,peli.getVotantes_Pelicula()));

                    }
                    else if(collection.equals("series"))
                    {
                        Serie ser = snapshot.toObject(Serie.class);
                        comentarios.setText(getResources().getString(R.string.comentario,ser.getComentarios().size()));
                        DecimalFormat formato = new DecimalFormat("#.#");
                        votacionmedia_multi.setText(getResources().getString(R.string.votacionmedia,formato.format(ser.getNotamedia_Serie())));
                        votantes_multi.setText(getResources().getString(R.string.votantes,ser.getVotantes_Serie()));
                    }
                } else {
                }
            }
        });

    }
    @SuppressLint("StringFormatMatches")
    private void cogerExtras() throws LikeSeriesExceptionClass
    {

        reglas="0";
        Intent intent = getIntent();
        collection = intent.getStringExtra("pelioserie");
        capitulos =intent.getIntExtra("capitulos",0);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        id = intent.getStringExtra("ID");
        if(intent.getStringExtra("pelioserie").equals("series"))
        {
            duracion_multi.setText(getResources().getString(R.string.duracionminutos,duracion));
            capitulos_multi.setText(getResources().getString(R.string.capitulos,intent.getIntExtra("capitulos",0)));
            capitulos_multi.setVisibility(View.VISIBLE);
            dividercapitulos1.setVisibility(View.VISIBLE);
            dividercapitulos2.setVisibility(View.VISIBLE);
        }
        else
        {
            dividercapitulos1.setVisibility(View.GONE);
            duracion_multi.setText(getResources().getString(R.string.duracionhoras,duracion));
        }

        titulo =intent.getStringExtra("titulo");
        titulo_multi.setText(titulo);

        descripcion = intent.getStringExtra("descripcion");
        descripcion_multi.setText(descripcion);

        genero = new ArrayList<>();
        genero.add(intent.getStringArrayListExtra("genero").get(0));
        genero.add(intent.getStringArrayListExtra("genero").get(1));
        genero.add(intent.getStringArrayListExtra("genero").get(2));
        genero.add(intent.getStringArrayListExtra("genero").get(3));

        genero0_multi.setText(intent.getStringArrayListExtra("genero").get(0));
        genero1_multi.setText(intent.getStringArrayListExtra("genero").get(1));
        genero2_multi.setText(intent.getStringArrayListExtra("genero").get(2));

        if(intent.getStringExtra("actoresno").equals("no"))
        {
            linearlayoutactores.setVisibility(View.GONE);
            divideractores.setVisibility(View.GONE);
        }
        else {
            divideractores.setVisibility(View.VISIBLE);
            linearlayoutactores.setVisibility(View.VISIBLE);

            actores = new ArrayList<>();
            actores.add(intent.getStringArrayListExtra("actores").get(0));
            actores.add(intent.getStringArrayListExtra("actores").get(1));
            actores.add(intent.getStringArrayListExtra("actores").get(2));

            actor1_multimedia.setText(intent.getStringArrayListExtra("actores").get(0));
            actor2_multimedia.setText(intent.getStringArrayListExtra("actores").get(1));
            actor3_multimedia.setText(intent.getStringArrayListExtra("actores").get(2));
        }
        productora=intent.getStringExtra("productora");
        productora_multi.setText(getResources().getString(R.string.productora,intent.getStringExtra("productora")));


        votomas = intent.getIntExtra("votosmas",0);
        votomenos = intent.getIntExtra("votosmenos",0);
        //Para la votacion de la media
        votantes = intent.getIntExtra("votantes",0);
        media = intent.getIntExtra("nmedia",0);

        //Cojo todos los comentarios de la BD
        ArrayList<String> arraynombres = intent.getStringArrayListExtra("arraynombres");
        arrayusuarios = intent.getStringArrayListExtra("arrayusuarios");
        ArrayList<String> arraycomentarios = intent.getStringArrayListExtra("arraycomentarios");

        arrayusuariovoto = intent.getStringArrayListExtra("arrayvotantes");
        ArrayList<String> arrayregla = intent.getStringArrayListExtra("arrayreglas");
        //Para agregar nuevo comentario

        for (int i = 0 ; i<arraycomentarios.size();i++)
        {
            Comentario com = new Comentario(arraynombres.get(i),arrayusuarios.get(i),arraycomentarios.get(i));
            coment.add(com);
        }

        for (int x = 0 ; x<arrayusuariovoto.size();x++)
        {
            if(arrayusuariovoto.get(x).equals(user.getUid()))
            {
                reglas=(String)arrayregla.get(x).toString();
            }
            Votos vot = new Votos(arrayusuariovoto.get(x),arrayregla.get(x));
            votos.add(vot);

        }


        //Cargamos el recyclerview

        LinearLayoutManager llmRVCO = new LinearLayoutManager(this);
        RVComentarios.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space, true, true));
        RVComentarios.setHasFixedSize(true);
        RVComentarios.setLayoutManager(llmRVCO);
        AdaptadorComentarios adaptadorComentarios = new AdaptadorComentarios(this,collection,id,coment);
        RVComentarios.setAdapter(adaptadorComentarios);
        adaptadorComentarios.refrescar();


        imagen = intent.getStringExtra("urlimagen");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl(imagen);
        final long ONE_MEGABYTE = 1024 * 1024;
        gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imagen_multi.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
        int ancho = 250;
        int alto = 250;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ancho, alto);
        imagen_multi.setLayoutParams(params);


        director=intent.getStringExtra("director");
        director_multi.setText(getResources().getString(R.string.direccion,director));
        fechaestreno=intent.getStringExtra("fechaestreno");
        fechaestreno_multi.setText(getResources().getString(R.string.fechae,fechaestreno));
        trailer=intent.getStringExtra("trailer");
        trailer_multi.setText(R.string.trailer);
        duracion=intent.getStringExtra("duracion");




        imagenvotopositivo.setOnClickListener(this);
        imagenvotonegativos.setOnClickListener(this);
        //Arreglar los votantes y la media
        votacionmedia_multi.setText(getResources().getString(R.string.votacionmedia,media));
        votantes_multi.setText(getResources().getString(R.string.votantes,votantes));
        compartir_multimedia.setOnClickListener(this);
        metodoVotacionActiva();
    }
    private void actualizarVotos() throws LikeSeriesExceptionClass
    {
            votopositivo.setText(votomas.toString());
            votonegativo.setText(votomenos.toString());

            actualizarDatosBD();

    }
    private void insertarComentarios() throws LikeSeriesExceptionClass
    {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        Comentario comentario = new Comentario(user.getDisplayName(),user.getUid(),cajacomentario.getText().toString());
        coment.add(comentario);
        actualizarDatosBD();
        actualizarRecyclerComentarios();
    }


    @Override
    public void onClick(final View v) {
                switch (v.getId())
                {
                    case R.id.like:
                        if(vpositivo) {
                            like.setEnabled(false);
                            nolike.setEnabled(false);
                            imagenvotopositivo.setImageResource(R.drawable.like);
                            vpositivo = false;
                            vnegativo=false;
                            reglas="1";
                            votomas++;
                            imagenvotonegativos.setEnabled(false);

                            try {
                                actualizarVotosUsuario(null,null);
                                actualizarVotos();
                            } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                                Toast.makeText(getApplicationContext(),
                                        "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                            }

                        }
                        else if (!vpositivo)
                        {
                            like.setEnabled(false);
                            nolike.setEnabled(false);
                            imagenvotopositivo.setImageResource(R.drawable.unlike);
                            imagenvotonegativos.setEnabled(true);
                            reglas="0";
                            vpositivo=true;
                            vnegativo=true;
                            negativo = false;
                            positivo = true;
                            votomas--;



                            try {
                                actualizarVotosUsuario(positivo,negativo);
                                actualizarVotos();
                            } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                                Toast.makeText(getApplicationContext(),
                                        "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                            }

                        }
                        break;

                    case R.id.nolike:

                        if(vnegativo)
                        {
                            nolike.setEnabled(false);
                            like.setEnabled(false);
                            imagenvotonegativos.setImageResource(R.drawable.nolike);
                            votomenos++;
                            reglas="2";
                            imagenvotopositivo.setEnabled(false);
                            vnegativo=false;
                            vpositivo=false;

                            try {
                                actualizarVotosUsuario(null,null);
                                actualizarVotos();
                            } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                                Toast.makeText(getApplicationContext(),
                                        "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                            }

                        }
                        else if (!vnegativo)
                        {
                            like.setEnabled(false);
                            nolike.setEnabled(false);
                            imagenvotonegativos.setImageResource(R.drawable.unnolike);
                            vnegativo = true;
                            vpositivo=true;
                            reglas="0";
                            votomenos--;
                            imagenvotopositivo.setEnabled(true);
                            negativo = true;
                            positivo = false;

                            try {
                                actualizarVotosUsuario(positivo,negativo);
                                actualizarVotos();

                            } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                                Toast.makeText(getApplicationContext(),
                                        "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                            }
                        }
                        break;
                    case R.id.imagenvoto:
                        new DialogoNumberPickerValoracion(contexto,ContenedorMultimedia.class,imagenvoto,collection,id);
                        try {
                            refrescarDatosPantalla();
                        } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                            Toast.makeText(getApplicationContext(),
                                    "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.compartirresultado:
                        abrirCompartir();
                        break;
                    case R.id.botonmandarComentario:
                        if(!cajacomentario.getText().toString().equals(""))
                        {
                            try {
                                insertarComentarios();
                            } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                                Toast.makeText(getApplicationContext(),
                                        "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                            }
                            cajacomentario.setText("");
                        }
                        break;
                    case R.id.mostrarmasomenos:

                        if(mostrarmas_boolean==true)
                        {
                            mostrar.setVisibility(View.VISIBLE);
                            mostrarmas.setText(R.string.mostrarmenos);
                            mostrarmas_boolean=false;
                        }
                        else if(mostrarmas_boolean == false)
                        {
                            mostrar.setVisibility(View.GONE);
                            mostrarmas.setText(R.string.mostrarmas);
                            mostrarmas_boolean=true;
                        }
                        break;
                    case R.id.trailer_Multimedia:
                        Intent intent = new Intent(contexto,FullScreenYoutube.class);
                        intent.putExtra("trailer",trailer);
                        startActivity(intent);
                        break;
                }



    }

    /**
     * Este metodo comprueba si el usuario a votado alguna vez en la app , si esto es positivo la imagen se vuelve de color
     */
    private void metodoVotacionActiva() throws LikeSeriesExceptionClass
    {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user= mAuth.getCurrentUser();

        DocumentReference docRef = db.collection(collection).document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Pelicula peli = documentSnapshot.toObject(Pelicula.class);
                Serie ser =documentSnapshot.toObject(Serie.class);

                if(collection.equals("peliculas"))
                {

                    for(int i = 0 ; i<peli.getVotacion_media().size();i++) {
                        if(peli.getVotacion_media().get(i).getVotado()==1&&peli.getVotacion_media().get(i).getUid().equals(user.getUid()))
                        {
                            imagenvoto.setImageResource(R.drawable.estrella_on);

                        }
                    }

                }
                else if(collection.equals("series"))
                {

                    for(int i = 0 ; i<ser.getVotacion_media().size();i++) {
                        if(ser.getVotacion_media().get(i).getVotado()==1&&ser.getVotacion_media().get(i).getUid().equals(user.getUid()))
                        {
                            imagenvoto.setImageResource(R.drawable.estrella_on);
                        }
                    }

                }
            }
        });

    }
    /**
     * Este metodo se va a encargar de actualizar los votos segun el usuario pulse positivo o negativo
     */
    private void actualizarVotosUsuario(final Boolean positivo, final Boolean negativo) throws LikeSeriesExceptionClass
    {
        mAuth = FirebaseAuth.getInstance();
        user= mAuth.getCurrentUser();
                if(reglas.equals("0")&&negativo)
                {

//quitar 1 al negativo
                    db= FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("usuarios").document(user.getUid());
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Usuario usuario = documentSnapshot.toObject(Usuario.class);
                            if(usuario.getAdministrador()==1) {
                                Usuario usua = new Usuario(usuario.getUID(), usuario.getNombre(), usuario.getEmail(), usuario.getAdministrador(), usuario.getVotosPositivos(), usuario.getVotosNegativos() - 1);
                                db.collection("usuarios").document(usuario.getUID()).set(usua);
                                like.setEnabled(true);
                                nolike.setEnabled(true);
                            }
                            else {
                                Usuario usua = new Usuario(usuario.getUID(), usuario.getNombre(), usuario.getEmail(), 0, usuario.getVotosPositivos(), usuario.getVotosNegativos() - 1);
                                db.collection("usuarios").document(usuario.getUID()).set(usua);
                                like.setEnabled(true);
                                nolike.setEnabled(true);
                            }

                        }
                    });


                }
                else if(reglas.equals("0") && positivo)
                {
                    //quitar 1 al positivo
                    db= FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("usuarios").document(user.getUid());
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Usuario usuario = documentSnapshot.toObject(Usuario.class);
                            if(usuario.getAdministrador()==1) {
                                Usuario usua = new Usuario(usuario.getUID(), usuario.getNombre(), usuario.getEmail(), usuario.getAdministrador(), usuario.getVotosPositivos() - 1, usuario.getVotosNegativos());
                                db.collection("usuarios").document(usuario.getUID()).set(usua);
                                like.setEnabled(true);
                                nolike.setEnabled(true);
                            }
                            else
                            {
                                Usuario usua = new Usuario(usuario.getUID(), usuario.getNombre(), usuario.getEmail(), 0, usuario.getVotosPositivos() - 1, usuario.getVotosNegativos());
                                db.collection("usuarios").document(usuario.getUID()).set(usua);
                                like.setEnabled(true);
                                nolike.setEnabled(true);
                            }

                        }
                    });
                }
                else if(reglas.equals("1")&&!vpositivo)
                {
                    //sumar 1 al positivo
                    db= FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("usuarios").document(user.getUid());
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Usuario usuario = documentSnapshot.toObject(Usuario.class);
                            if(usuario.getAdministrador()==1) {
                                Usuario usua = new Usuario(usuario.getUID(), usuario.getNombre(), usuario.getEmail(), usuario.getAdministrador(), usuario.getVotosPositivos() + 1, usuario.getVotosNegativos());
                                db.collection("usuarios").document(usuario.getUID()).set(usua);
                                like.setEnabled(true);

                            }
                            else
                            {
                                Usuario usua = new Usuario(usuario.getUID(), usuario.getNombre(), usuario.getEmail(), 0, usuario.getVotosPositivos() + 1, usuario.getVotosNegativos());
                                db.collection("usuarios").document(usuario.getUID()).set(usua);
                                like.setEnabled(true);

                            }

                        }
                    });
                }
                else if(reglas.equals("2")&&!vnegativo)
                {
                    //sumar 1 al negativo
                    db= FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("usuarios").document(user.getUid());
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Usuario usuario = documentSnapshot.toObject(Usuario.class);
                            if(usuario.getAdministrador()==1) {
                                Usuario usua = new Usuario(usuario.getUID(), usuario.getNombre(), usuario.getEmail(), usuario.getAdministrador(), usuario.getVotosPositivos(), usuario.getVotosNegativos() + 1);
                                db.collection("usuarios").document(usuario.getUID()).set(usua);

                                nolike.setEnabled(true);
                            }
                            else
                            {
                                Usuario usua = new Usuario(usuario.getUID(), usuario.getNombre(), usuario.getEmail(), 0, usuario.getVotosPositivos(), usuario.getVotosNegativos() + 1);
                                db.collection("usuarios").document(usuario.getUID()).set(usua);

                                nolike.setEnabled(true);
                            }
                        }
                    });
                }
    }
    /**
     * Metodo que llama a una función de firebase para subir los nuevos datos actualizados
     */
    private void actualizarDatosBD() throws LikeSeriesExceptionClass
    {
        Intent intent = getIntent();
        final String pelioserie = intent.getStringExtra("pelioserie");
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if(pelioserie.equals("peliculas")) {
            db = FirebaseFirestore.getInstance();
            for (int i = 0 ; i<votos.size();i++)
            {
                if(votos.get(i).getUsuariovoto().equals(user.getUid())) {
                    votos.remove(i);
                }
            }
            if(!votos.contains(user.getUid()))
            {
                Votos vo = new Votos(user.getUid(), reglas);
                votos.add(vo);
                db = FirebaseFirestore.getInstance();

                DocumentReference docRef = db.collection("peliculas").document(id);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Pelicula peli = documentSnapshot.toObject(Pelicula.class);

                        Pelicula pelicula = new Pelicula(peli.getCollection_Pelicula(),id, titulo, descripcion, productora,director,fechaestreno,trailer,duracion, genero, imagen, votomas, votomenos, votantes, peli.getNotamedia_Pelicula(), votos, coment,peli.getVotacion_media(),actores);
                        db.collection("peliculas").document(pelicula.getID_Pelicula()).set(pelicula);
                        try {
                            refrescarRecyclers(pelicula, null);
                        } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                            Toast.makeText(getApplicationContext(),
                                    "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        }
        else if(pelioserie.equals("series"))
        {
            db = FirebaseFirestore.getInstance();
            for (int i = 0 ; i<votos.size();i++)
            {
                if(votos.get(i).getUsuariovoto().equals(user.getUid())) {
                    votos.remove(i);
                }
            }

            if(!votos.contains(user.getUid()))
            {
                Votos vo = new Votos(user.getUid(), reglas);
                votos.add(vo);
                db = FirebaseFirestore.getInstance();


                DocumentReference docRef = db.collection("series").document(id);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Serie ser = documentSnapshot.toObject(Serie.class);

                        Serie serie = new Serie(ser.getCollection_Serie(),id, titulo, descripcion, productora,director,fechaestreno,trailer,duracion,genero, imagen, votomas, votomenos, votantes,capitulos,ser.getNotamedia_Serie(), votos, coment,ser.getVotacion_media(),actores);
                        db.collection("series").document(serie.getID_Serie()).set(serie);
                        try {
                            refrescarRecyclers(null, serie);
                        } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                            Toast.makeText(getApplicationContext(),
                                    "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        }
    }
    //Comenzar compartir contenido
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        public SpaceItemDecoration(ContenedorMultimedia contenedorMultimedia, Object p1, boolean b, boolean b1) throws LikeSeriesExceptionClass {
        }
    }
}
