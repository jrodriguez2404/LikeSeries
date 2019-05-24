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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class ContenedorMultimedia extends AppCompatActivity implements View.OnClickListener {
private TextView titulo_multi,descripcion_multi,genero0_multi,genero1_multi,genero2_multi,votopositivo,votonegativo,productora_multi,capitulos_multi ,comentarios,mostrarmas;
private ImageView imagenvotopositivo,imagenvotonegativos,imagen_multi,like,nolike;
private Boolean vnegativo,vpositivo,mostrarmas_boolean;
private Integer votantes,votomas,votomenos,usuariovotopositivo,usuariovotonegativo;
private FirebaseUser user;
private FirebaseAuth mAuth;
private long media;
private FirebaseFirestore db;
private RecyclerView RVComentarios,RVP,RVS;
private ImageView imagenvoto;
private int capitulos;
Context contexto = this;
private String titulo,descripcion,productora,imagen,id,reglas;
private ArrayList<String> genero;
private ArrayList<Multimedia>peliculasyseries;
private ArrayList<Comentario> coment;
private ArrayList<Votos> votos;
private ArrayList<String> arrayusuariovoto;
private ArrayList<String> arrayusuarios;
private Intent intentlike;
private String collection;
private Button insertarcomentario;
private EditText cajacomentario;
private LinearLayout mostrar;
 @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(intentlike);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_multimedia);
        declaraciones();
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

public void refrescarRecyclers(final Pelicula peli , final Serie ser)
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
                                    AdaptadorPeliculas adaptadorPeliculas = new AdaptadorPeliculas(contexto,pe);
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
                                    AdaptadorSeries adaptadorSeries = new AdaptadorSeries(contexto,se);
                                    adaptadorSeries.addItem(ser,intent.getIntExtra("numero",0));
                                }
                            }
                        }
                    });

        }

        }
    @SuppressLint("StringFormatMatches")
    private void actualizarRecyclerComentarios() {
        AdaptadorComentarios adaptadorComentarios = new AdaptadorComentarios(this,collection,id,coment);
        RVComentarios.setAdapter(adaptadorComentarios);
        adaptadorComentarios.refrescar();
        comentarios.setText(getResources().getString(R.string.comentario,coment.size()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mostrarmas_boolean=true;
        declaraciones();
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

    private void declaraciones()
    {

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

        cogerExtras();
    }
    public void metodoCambio()
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

                    }
                    else if(collection.equals("series"))
                    {
                        Serie ser = snapshot.toObject(Serie.class);
                        comentarios.setText(getResources().getString(R.string.comentario,ser.getComentarios().size()));
                    }
                } else {
                }
            }
        });

    }
    @SuppressLint("StringFormatMatches")
    private void cogerExtras()
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
            capitulos_multi.setText(getResources().getString(R.string.capitulos,intent.getIntExtra("capitulos",0)));
            capitulos_multi.setVisibility(View.VISIBLE);

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


        productora=intent.getStringExtra("productora");
        productora_multi.setText(getResources().getString(R.string.productora,intent.getStringExtra("productora")));


        votomas = intent.getIntExtra("votosmas",0);
        votomenos = intent.getIntExtra("votosmenos",0);
        //Para la votacion de la media
        votantes = intent.getIntExtra("votantes",0);
        media = intent.getLongExtra("nmedia",0);

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



        imagenvotopositivo.setOnClickListener(this);
        imagenvotonegativos.setOnClickListener(this);

        metodoCambio();
    }
    private void actualizarVotos()
    {
        votopositivo.setText(votomas.toString());
        votonegativo.setText(votomenos.toString());

        actualizarDatosBD();
    }
    private void insertarComentarios()
    {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        Comentario comentario = new Comentario(user.getDisplayName(),user.getUid(),cajacomentario.getText().toString());
        coment.add(comentario);
        actualizarDatosBD();
        actualizarRecyclerComentarios();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.like:
                if(vpositivo) {
                    imagenvotopositivo.setImageResource(R.drawable.like);
                    vpositivo = false;
                    reglas="1";
                    votomas++;
                    imagenvotonegativos.setEnabled(false);
                    vnegativo = true;
                    /**

*/

                    actualizarVotos();
            }
                else if (!vpositivo)
            {
                imagenvotopositivo.setImageResource(R.drawable.unlike);
                imagenvotonegativos.setEnabled(true);
                reglas="0";
                vpositivo=true;
                votomas--;
                actualizarVotos();
            }
                break;

            case R.id.nolike:

                if(vnegativo)
                {
                    imagenvotonegativos.setImageResource(R.drawable.nolike);
                    votomenos++;
                    reglas="2";
                    imagenvotopositivo.setEnabled(false);
                    vpositivo=true;
                    vnegativo = false;
                    actualizarVotos();
                }
                else if (!vnegativo)
            {
                imagenvotonegativos.setImageResource(R.drawable.unnolike);
                vnegativo = true;
                reglas="0";
                votomenos--;
                imagenvotopositivo.setEnabled(true);
                actualizarVotos();
            }
                break;
            case R.id.imagenvoto:

                new DialogoNumberPickerValoracion(contexto,ContenedorMultimedia.class,imagenvoto,collection,id);
                break;
            case R.id.botonmandarComentario:
                if(!cajacomentario.getText().equals(""))
                {
                    insertarComentarios();
                    cajacomentario.setText("");
                }
                else
                {

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
        }

    }
    /**
     * Metodo que llama a una función de firebase para subir los nuevos datos actualizados
     */
    private void actualizarDatosBD()
    {
        Intent intent = getIntent();
        String pelioserie = intent.getStringExtra("pelioserie");
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db.collection(pelioserie)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                            }
                        }
                    }
                });

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

                        Pelicula pelicula = new Pelicula(peli.getCollection_Pelicula(),id, titulo, descripcion, productora, genero, imagen, votomas, votomenos, votantes, 0, votos, coment);
                        db.collection("peliculas").document(pelicula.getID_Pelicula()).set(pelicula);
                        refrescarRecyclers(pelicula, null);
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

                        Serie serie = new Serie(ser.getCollection_Serie(),id, titulo, descripcion, productora, genero, imagen, votomas, votomenos, votantes,capitulos,0, votos, coment);
                        db.collection("series").document(serie.getID_Serie()).set(serie);
                        refrescarRecyclers(null, serie);
                    }
                });

            }
        }
    }
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        public SpaceItemDecoration(ContenedorMultimedia contenedorMultimedia, Object p1, boolean b, boolean b1) {
        }
    }
}
