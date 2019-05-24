package com.loja.jesus.likeseries;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class Like extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private TextView bienvenida,hola, mispositivos,misnegativos;
private ImageView imagenlogo;
private String usuario;
private RecyclerView RVP,RVS,RVVP,RVVN;
private FirebaseAuth mAuth;
private FirebaseUser user;
private FirebaseFirestore db;
private LinearLayout Peliculas,Series,principal_like;
private Spinner spiner;
private ArrayList<Multimedia> multipositiva,multinegativa;
Context contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        cargarNombreUsuario();
        imagenlogo = findViewById(R.id.imagenlogo_like);
        int ancho = 250;
        int alto = 250;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ancho, alto);
        imagenlogo.setLayoutParams(params);
        principal_like = findViewById(R.id.principal_like);
        principal_like.setVisibility(View.VISIBLE);
        //Cargo los recyclerview
        //cargarRecycleview();
        //agregarSeriesRapidamente();
        //agregarPeliculasRapidamente();
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        //Pantalla de peliculas
        Peliculas=findViewById(R.id.Peliculas);
        //Pantalla de series
        Series=findViewById(R.id.Series);
        contexto = this;

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            @SuppressLint("StringFormatMatches")
            public void onDrawerOpened(@NonNull View view) {
                bienvenida = drawer.findViewById(R.id.bienvenida);
                mAuth = FirebaseAuth.getInstance();
                user = mAuth.getCurrentUser();
                db= FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("usuarios").document(user.getUid());
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Usuario user = documentSnapshot.toObject(Usuario.class);
                        bienvenida.setText(getResources().getString(R.string.bienvenida,user.getNombre()));
                    }
                });




            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mispositivos = findViewById(R.id.mispositivos);
        misnegativos = findViewById(R.id.misnegativos);
        cargarRecycleview();
        cargarRecycerViewVotos();
        cargarVotosPositivosenListaHorizontal();
        cargarVotosNegativosenListaHorizontal();
    }

    public void cerrarActividad()
    {
        finish();
    }
    private void cargarNombreUsuario()
    {
        hola = findViewById(R.id.hola);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db= FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("usuarios").document(user.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            @SuppressLint("StringFormatMatches")
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario user = documentSnapshot.toObject(Usuario.class);
                usuario=user.getNombre();
                hola.setText(getResources().getString(R.string.bienvenida,usuario));
            }
        });
    }

    private void agregarPeliculasRapidamente()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add("Acción");
        array.add("Romance");
        array.add("Ciencia Ficción");
        array.add("Realista");

        ArrayList<Votos> votos = new ArrayList<>();
        Votos v = new Votos("PR1n0WS7eGepWHObst5MXEbTZbB2","1");
        votos.add(v);

        ArrayList<Comentario> comentarios = new ArrayList<>();
        Comentario c = new Comentario("Jesus","PR1n0WS7eGepWHObst5MXEbTZbB2","hola");
comentarios.add(c);



        db= FirebaseFirestore.getInstance();
        Pelicula pelicula = new Pelicula("peliculas","Alita:Ángeldecombate","Alita: Ángel de combate","Cuando Alita se despierta sin recordar quién es en un mundo futuro que no reconoce, Ido , un médico compasivo, se da cuenta de que en algún lugar de ese caparazón de cyborg abandonado, está el corazón y alma de una mujer joven con un pasado extraordinario. Mientras Alita toma las riendas de su nueva vida y aprende a adaptarse a las peligrosas calles de Iron City, Ido tratará de protegerla de su propio pasado, mientras que su nuevo amigo Hugo se ofrecerá, en cambio, a ayudarla a desenterrar sus recuerdos. Cuando las fuerzas mortales y corruptas que manejan la ciudad comienzan a perseguir a Alita, ella descubre una pista crucial sobre su pasado: posee habilidades de combate únicas que los que ostentan el poder querrán controlar a toda costa. Sólo manteniéndose fuera de su alcance, podrá salvar a sus amigos, a su familia y el mundo que ha aprendido a amar.","Twenty Century Fox",array,"gs://likeseries-c426a.appspot.com/imagenesPeliculas/alita.jpg",0,0,0,0,votos,comentarios);
        db.collection("peliculas").document(pelicula.getID_Pelicula()).set(pelicula);
    }
    private void agregarSeriesRapidamente()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add("Acción");
        array.add("Aventura");
        array.add("SuperHeroes");
        array.add("Anime");

        ArrayList<Votos> votos = new ArrayList<>();
        Votos v = new Votos("PR1n0WS7eGepWHObst5MXEbTZbB2","0");
        votos.add(v);

        ArrayList<Comentario> comentarios = new ArrayList<>();
        Comentario c = new Comentario("Jesus","PR1n0WS7eGepWHObst5MXEbTZbB2","hola");
        comentarios.add(c);



        db= FirebaseFirestore.getInstance();
        Serie serie = new Serie("series","BokunoHero","Boku no Hero","Un día, tras conocer personalmente a All Might, este le ofrece heredar sus poderes al ver la gran determinación de Midoriya aunque no tenga poderes; desde entonces, Midoriya accede y empieza a estudiar en la U.A; donde hace nuevos amigos, conoce otros héroes profesionales, aprende a dominar sus poderes y hasta hacer frente a auténticos villanos.","Bones",array,"gs://likeseries-c426a.appspot.com/imagenesSeries/bokunohero.jpg",0,0,0,25,0,votos,comentarios);
        db.collection("series").document(serie.getID_Serie()).set(serie);
    }
    private void cargarRecycleview(){
        RVP = findViewById(R.id.RVP);
        RVS = findViewById(R.id.RVS);
        RVVP = findViewById(R.id.RVVP);
        RVVN = findViewById(R.id.RVVN);

        LinearLayoutManager llmRVVP = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RVVP.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space, true, true));
        RVVP.setHasFixedSize(true);
        RVVP.setLayoutManager(llmRVVP);

        LinearLayoutManager llmRVVN = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RVVN.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space, true, true));
        RVVN.setHasFixedSize(true);
        RVVN.setLayoutManager(llmRVVN);

        LinearLayoutManager llmRVP = new LinearLayoutManager(this);
        RVP.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space, true, true));
        RVP.setHasFixedSize(true);
        RVP.setLayoutManager(llmRVP);

        LinearLayoutManager llmRVS = new LinearLayoutManager(this);
        RVS.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space, true, true));
        RVS.setHasFixedSize(true);
        RVS.setLayoutManager(llmRVS);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void cargarRecycerViewVotos()
    {
        multipositiva = new ArrayList<>();
        multinegativa = new ArrayList<>();
        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
//Aqui falta algo
        db.collection("peliculas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                for (int i = 0; i <= task.getResult().size(); i++) {
                                    Multimedia multimedia = document.toObject(Multimedia.class);
                                    try {
                                        if (multimedia.getVotosusuarios().get(i).getReglas().equals("1") && multimedia.getVotosusuarios().get(i).getUsuariovoto().equals(user.getUid()) && multimedia.getVotosusuarios() != null) {
                                            multipositiva.add(multimedia);
                                        } else if (multimedia.getVotosusuarios().get(i).getReglas().equals("2") && multimedia.getVotosusuarios().get(i).getUsuariovoto().equals(user.getUid()) && multimedia.getVotosusuarios() != null) {
                                            multinegativa.add(multimedia);
                                        }
                                    } catch (Exception e) {
                                    }
                                    AdaptadorMutimediaLike adaptadorlike = new AdaptadorMutimediaLike(contexto, multipositiva);
                                    RVVP.setAdapter(adaptadorlike);
                                    RVVP.setHasFixedSize(true);
                                    adaptadorlike.refrescar();

                                    AdaptadorMutimediaDisLike adaptadornolike = new AdaptadorMutimediaDisLike(contexto, multinegativa);
                                    RVVN.setAdapter(adaptadornolike);
                                    RVVN.setHasFixedSize(true);
                                    adaptadornolike.refrescar();
                                }
                            }
                        }
                    }
                });
        db.collection("series")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                for (int i = 0; i <= task.getResult().size(); i++) {
                                    Multimedia multimedia = document.toObject(Multimedia.class);


                                    if (multimedia.getVotosusuarios().get(i).getReglas().equals("1") && multimedia.getVotosusuarios().get(i).getUsuariovoto().equals(user.getUid())) {
                                        multipositiva.add(multimedia);
                                    } else if (multimedia.getVotosusuarios().get(i).getReglas().equals("2") && multimedia.getVotosusuarios().get(i).getUsuariovoto().equals(user.getUid())) {
                                        multinegativa.add(multimedia);
                                    }

                                }
                                AdaptadorMutimediaLike adaptadorlike = new AdaptadorMutimediaLike(contexto, multipositiva);
                                RVVP.setAdapter(adaptadorlike);
                                RVVP.setHasFixedSize(true);
                                adaptadorlike.refrescar();

                                AdaptadorMutimediaDisLike adaptadornolike = new AdaptadorMutimediaDisLike(contexto, multinegativa);
                                RVVN.setAdapter(adaptadornolike);
                                RVVN.setHasFixedSize(true);
                                adaptadornolike.refrescar();
                            }
                        }
                    }
                });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.votacion)
        {
            Peliculas.setVisibility(View.GONE);
            Series.setVisibility(View.GONE);
            principal_like.setVisibility(View.VISIBLE);
        }
        else if (id == R.id.pelicula) {

            Peliculas.setVisibility(View.VISIBLE);
            Series.setVisibility(View.GONE);
            principal_like.setVisibility(View.GONE);
            spiner = findViewById(R.id.spinerPelicula);
            rellenarSpinner(spiner);

        } else if (id == R.id.series) {
            Peliculas.setVisibility(View.GONE);
            Series.setVisibility(View.VISIBLE);
            principal_like.setVisibility(View.GONE);
            spiner = findViewById(R.id.spinerSerie);
            rellenarSpinner(spiner);
        }
        else if (id == R.id.mi_perfil) {
            Intent miperfil = new Intent(this, miperfil.class);
            startActivity(miperfil);
        }
        else if (id == R.id.cerrar_sesion) {
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            db= FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("usuarios").document(user.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Usuario user = documentSnapshot.toObject(Usuario.class);
                    AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                    builder.setTitle(getResources().getString(R.string.cerrarsesion,user.getNombre()));
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent login = new Intent(getApplication(), MainLogin.class);
                            startActivity(login);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Rellena el spiner de todas las categorias
     * @return
     */
    private ArrayList<String> categoriasPeliculasySeries()
{
    final ArrayList<String> array = new ArrayList<>();
    array.add("Sin filtro");
    array.add("Ciencia Ficción");
    array.add("Romance");
    array.add("Realista");
    array.add("Anime");
    array.add("Aventura");
    array.add("Acción");
    array.add("SuperHeroes");
    array.add("Terror");
    array.add("Escolares");
    array.add("Más Votados");
    return array;
}







    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        public SpaceItemDecoration(Like like, Object p1, boolean b, boolean b1) {
        }
    }
    private void rellenarSpinner(Spinner spiner)
    {
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoriasPeliculasySeries());
        //Spiner
        spiner.setAdapter(adapter);
        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final ArrayList<Pelicula>peliculas = new ArrayList<>();
                db = FirebaseFirestore.getInstance();
                final ArrayList<Serie>series = new ArrayList<>();
                if(!parent.getItemAtPosition(position).toString().equals("Más Votados") && !parent.getItemAtPosition(position).toString().equals("Sin filtro")) {
                    db.collection("peliculas")
                            .whereArrayContains("genero_Pelicula", parent.getItemAtPosition(position).toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            System.out.println(document.getData());
                                            Pelicula pelicula = document.toObject(Pelicula.class);
                                            peliculas.add(pelicula);
                                            AdaptadorPeliculas adaptadorPeliculas = new AdaptadorPeliculas(contexto,peliculas);
                                            RVP.setAdapter(adaptadorPeliculas);
                                            adaptadorPeliculas.refrescar();
                                        }
                                    }
                                }
                            });
                    db.collection("series")
                            .whereArrayContains("genero_Serie", parent.getItemAtPosition(position).toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Serie serie = document.toObject(Serie.class);
                                            series.add(serie);
                                            AdaptadorSeries adaptadorSeries = new AdaptadorSeries(contexto,series);
                                            RVS.setAdapter(adaptadorSeries);
                                            adaptadorSeries.refrescar();
                                        }
                                    }
                                }
                            });

                }
                else if(parent.getItemAtPosition(position).toString().equals("Sin filtro"))
                {

                    db.collection("peliculas")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            System.out.println(document.getData());
                                            Pelicula pelicula = document.toObject(Pelicula.class);
                                            peliculas.add(pelicula);
                                            AdaptadorPeliculas adaptadorPeliculas = new AdaptadorPeliculas(contexto,peliculas);
                                            RVP.setAdapter(adaptadorPeliculas);
                                            adaptadorPeliculas.refrescar();
                                        }
                                    }
                                }
                            });
                    db.collection("series")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Serie serie = document.toObject(Serie.class);
                                            series.add(serie);
                                            AdaptadorSeries adaptadorSeries = new AdaptadorSeries(contexto,series);
                                            RVS.setAdapter(adaptadorSeries);
                                            adaptadorSeries.refrescar();
                                        }
                                    }
                                }
                            });
                }
                else if(parent.getItemAtPosition(position).toString().equals("Más Votados"))
                {

                    db.collection("peliculas")
                            .orderBy("votosPositivos_Pelicula", Query.Direction.ASCENDING)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            System.out.println(document.getData());
                                            Pelicula pelicula = document.toObject(Pelicula.class);
                                            peliculas.add(pelicula);
                                            AdaptadorPeliculas adaptadorPeliculas = new AdaptadorPeliculas(contexto,peliculas);
                                            RVP.setAdapter(adaptadorPeliculas);
                                            adaptadorPeliculas.refrescar();
                                        }
                                    }
                                }
                            });
                    db.collection("series")
                            .orderBy("votosPositivos_Series", Query.Direction.ASCENDING)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Serie serie = document.toObject(Serie.class);
                                            series.add(serie);
                                            AdaptadorSeries adaptadorSeries = new AdaptadorSeries(contexto,series);
                                            RVS.setAdapter(adaptadorSeries);
                                            adaptadorSeries.refrescar();
                                        }
                                    }
                                }
                            });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @SuppressLint("StringFormatMatches")
    private void cargarVotosPositivosenListaHorizontal() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("usuarios").document(user.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Usuario user = documentSnapshot.toObject(Usuario.class);
                    mispositivos.setText(getString(R.string.mispositivos,));
            }
        });


    }
        @SuppressLint("StringFormatMatches")
        private void cargarVotosNegativosenListaHorizontal() {
            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("usuarios").document(user.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Usuario user = documentSnapshot.toObject(Usuario.class);
                    misnegativos.setText(getString(R.string.misnegativos,));
                }
            });
        }
}
