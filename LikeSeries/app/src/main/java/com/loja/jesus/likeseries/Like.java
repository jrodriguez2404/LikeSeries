package com.loja.jesus.likeseries;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class Like extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private TextView bienvenida,hola, mispositivos,misnegativos,botoncomentario_chat,cajaTexto_chat,cerrar_chat;
private ImageView imagenlogo;
private String usuario;
private RecyclerView RVP,RVS,RVVP,RVVN,RVCR;
private FirebaseAuth mAuth;
private FirebaseUser user;
private FirebaseFirestore db;
private LinearLayout Peliculas,Series,principal_like,chatmenu_linear,pantalla_linear;
private Spinner spiner;
private ArrayList<Multimedia> multipositiva,multinegativa;
private ArrayList<ChatGeneral>arraychat;
Context contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        chatmenu_linear = findViewById(R.id.chat_linear);
        pantalla_linear = findViewById(R.id.pantalla_linear);
        arraychat=new ArrayList<>();
        try {
            cargarNombreUsuario();
        } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
            Toast.makeText(getApplicationContext(),
                    "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
        }

//Para el recyclerviewchat futuro

        try {
            FloatingActionButton chat = findViewById(R.id.chat);
            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pantalla_linear.setVisibility(View.GONE);
                    chatmenu_linear.setVisibility(View.VISIBLE);
                    actualizarTablaChat();
                    cerrar_chat = findViewById(R.id.cerrar_chat);
                    cerrar_chat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pantalla_linear.setVisibility(View.VISIBLE);
                            chatmenu_linear.setVisibility(View.GONE);
                            cajaTexto_chat.setText("");
                        }
                    });



                }

            });
        }
        catch (Exception e)
        {

        }

        imagenlogo = findViewById(R.id.imagenlogo_like);
        int ancho = 150;
        int alto = 150;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ancho, alto);
        imagenlogo.setLayoutParams(params);

        principal_like = findViewById(R.id.principal_like);
        principal_like.setVisibility(View.VISIBLE);
        //Cargo los recyclerview
        try {
            cargarRecycleview();
        } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
            Toast.makeText(getApplicationContext(),
                    "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
        }
        //agregarSeriesRapidamente();
        //agregarPeliculasRapidamente();
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        //Pantalla de peliculas
        Peliculas=findViewById(R.id.Peliculas);
        //Pantalla de series
        Series=findViewById(R.id.Series);
        botoncomentario_chat=findViewById(R.id.botonmandarComentario_chat);

        cajaTexto_chat = findViewById(R.id.cajaTexto_chat);

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
        botoncomentario_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                user = mAuth.getCurrentUser();
                db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("chatgeneral").document("chat");
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        arraychat.clear();
                        Chat chat = documentSnapshot.toObject(Chat.class);
                        ChatGeneral chatGeneral = new ChatGeneral(user.getDisplayName(),user.getUid(),cajaTexto_chat.getText().toString());
                        arraychat.add(chatGeneral);
                        try {
                            for (int i = 0; i < chat.getChatgeneral().size(); i++) {
                                arraychat.add(chat.getChatgeneral().get(i));
                            }
                        }
                        catch (Exception e)
                        {

                        }

                        chat = new Chat(arraychat);
                        db.collection("chatgeneral").document("chat").set(chat);
                        AdaptadorChat adaptadorChat = new AdaptadorChat(contexto, arraychat);
                        RVCR.setAdapter(adaptadorChat);
                        RVCR.setHasFixedSize(true);
                        adaptadorChat.refrescar();
                    }
                });


            }
        });
    }

    private void actualizarTablaChat() {
        db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("chatgeneral").document("chat");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Chat chat = snapshot.toObject(Chat.class);
                    arraychat.clear();
                    try {
                        for (int i = 0; i < chat.getChatgeneral().size(); i++) {
                            arraychat.add(chat.getChatgeneral().get(i));
                        }
                    }
                    catch (Exception u)
                    {

                    }
                    AdaptadorChat adaptadorChat = new AdaptadorChat(contexto, arraychat);
                    RVCR.setAdapter(adaptadorChat);
                    RVCR.setHasFixedSize(true);
                    adaptadorChat.refrescar();
                } else {
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mispositivos = findViewById(R.id.mispositivos);
        misnegativos = findViewById(R.id.misnegativos);
        try {
            cargarRecycleview();
            cargarRecycerViewVotos();
            cargarVotosPositivosenListaHorizontal();
            cargarVotosNegativosenListaHorizontal();
        }
        catch (LikeSeriesExceptionClass likeSeriesExceptionClass)
        {
            Toast.makeText(getApplicationContext(),
                    "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
        }
    }

    public void cerrarActividad() throws LikeSeriesExceptionClass
    {
        finish();
    }
    private void cargarNombreUsuario() throws LikeSeriesExceptionClass
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
        array.add("Drama");
        array.add("Escolares");
        array.add("Shounen");
        array.add("Anime");

        ArrayList<Votos> votos = new ArrayList<>();
        Votos v = new Votos("F6Z6lvukr7c1wioZprva2SpCHnH3","0");
        votos.add(v);

        ArrayList<Comentario> comentarios = new ArrayList<>();
        Comentario c = new Comentario("Jesus","F6Z6lvukr7c1wioZprva2SpCHnH3","hola");
        comentarios.add(c);
        ArrayList<Votacion_media> votacion_media = new ArrayList<>();
        //Votacion_media vm = new Votacion_media("PR1n0WS7eGepWHObst5MXEbTZbB2",0,false);
        //votacion_media.add(vm);


        db= FirebaseFirestore.getInstance();
        Pelicula pelicula = new Pelicula("peliculas","KoenoKatachi","Koe no Katachi","La historia gira en torno a Shoko Nishimiya, una estudiante de primaria que es sorda de nacimiento y que al cambiarse de colegio comienza a recibir acoso escolar por parte de sus nuevos compañeros. Uno de los principales responsables es Ishida Shouya quien termina por forzar que Nishimiya se cambie de escuela. Como resultado de los actos contra Shoko las autoridades del colegio toman cartas en el asunto y el curso señala como único responsable a Ishida, quien comienza a sentir el acoso impuesto por sus propios compañeros, al mismo tiempo que termina aislándose de los que alguna vez fueron sus amigos. Años después, Ishida intenta corregir su mal actuar, buscando la redención frente a Nishimiya.","Selecta Vision","Naoko Yamada","16/03/2018","19WaToCSwyg","2",array,"gs://likeseries-c426a.appspot.com/imagenesPeliculas/koeno.jpg",0,0,0,0,votos,comentarios,votacion_media);
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
        Votos v = new Votos("F6Z6lvukr7c1wioZprva2SpCHnH3","0");
        votos.add(v);

        ArrayList<Comentario> comentarios = new ArrayList<>();
        Comentario c = new Comentario("Jesus","F6Z6lvukr7c1wioZprva2SpCHnH3","hola");
        comentarios.add(c);

        ArrayList<Votacion_media> votacion_media = new ArrayList<>();
        //Votacion_media vm = new Votacion_media("PR1n0WS7eGepWHObst5MXEbTZbB2",0,false);
        //votacion_media.add(vm);


        db= FirebaseFirestore.getInstance();
        Serie serie = new Serie("series","BokunoHero","Boku no Hero","Un día, tras conocer personalmente a All Might, este le ofrece heredar sus poderes al ver la gran determinación de Midoriya aunque no tenga poderes; desde entonces, Midoriya accede y empieza a estudiar en la U.A; donde hace nuevos amigos, conoce otros héroes profesionales, aprende a dominar sus poderes y hasta hacer frente a auténticos villanos.","Bones","Kenji Nagasaki","03/04/2016","wIb3nnOeves","25",array,"gs://likeseries-c426a.appspot.com/imagenesSeries/bokunohero.jpg",0,0,0,15,0,votos,comentarios,votacion_media);
        db.collection("series").document(serie.getID_Serie()).set(serie);
    }
    private void cargarRecycleview() throws LikeSeriesExceptionClass{
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

        RVCR = findViewById(R.id.RVCR);
        LinearLayoutManager llmRVCR = new LinearLayoutManager(this);
        RVCR.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space, true, true));
        RVCR.setHasFixedSize(true);
        RVCR.setLayoutManager(llmRVCR);
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

    public void cargarRecycerViewVotos() throws LikeSeriesExceptionClass
    {
        multipositiva = new ArrayList<>();
        multinegativa = new ArrayList<>();
        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
//Aqui falta algo
        try {
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
                                        }
                                        catch (Exception e)
                                        {

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
                                        try {
                                            if (multimedia.getVotosusuarios().get(i).getReglas().equals("1") && multimedia.getVotosusuarios().get(i).getUsuariovoto().equals(user.getUid())) {
                                                multipositiva.add(multimedia);
                                            } else if (multimedia.getVotosusuarios().get(i).getReglas().equals("2") && multimedia.getVotosusuarios().get(i).getUsuariovoto().equals(user.getUid())) {
                                                multinegativa.add(multimedia);
                                            }
                                        }
                                        catch (Exception e)
                                        {

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
        catch (Exception e) {
        }
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
            pantalla_linear.setVisibility(View.VISIBLE);
            cajaTexto_chat.setText("");
        }
        else if (id == R.id.pelicula) {

            Peliculas.setVisibility(View.VISIBLE);
            Series.setVisibility(View.GONE);
            principal_like.setVisibility(View.GONE);
            pantalla_linear.setVisibility(View.VISIBLE);
            spiner = findViewById(R.id.spinerPelicula);
            cajaTexto_chat.setText("");
            try {
                rellenarSpinner(spiner);
            } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                Toast.makeText(getApplicationContext(),
                        "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.series) {
            Peliculas.setVisibility(View.GONE);
            Series.setVisibility(View.VISIBLE);
            principal_like.setVisibility(View.GONE);
            pantalla_linear.setVisibility(View.VISIBLE);
            spiner = findViewById(R.id.spinerSerie);
            cajaTexto_chat.setText("");
            try {
                rellenarSpinner(spiner);
            } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                Toast.makeText(getApplicationContext(),
                        "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
            }
        }
        else if (id == R.id.mi_perfil) {
            Intent miperfil = new Intent(this, miperfil.class);
            startActivity(miperfil);
            pantalla_linear.setVisibility(View.VISIBLE);
            cajaTexto_chat.setText("");
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
    private ArrayList<String> categoriasPeliculasySeries() throws LikeSeriesExceptionClass
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







    private class SpaceItemDecoration extends RecyclerView.ItemDecoration{
        public SpaceItemDecoration(Like like, Object p1, boolean b, boolean b1) throws LikeSeriesExceptionClass{
        }
    }
    private void rellenarSpinner(Spinner spiner) throws LikeSeriesExceptionClass
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
    private void cargarVotosPositivosenListaHorizontal() throws LikeSeriesExceptionClass{
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("usuarios").document(user.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Usuario user = documentSnapshot.toObject(Usuario.class);
                    mispositivos.setText(getString(R.string.mispositivos,user.getVotosPositivos()));
            }
        });


    }
        @SuppressLint("StringFormatMatches")
        private void cargarVotosNegativosenListaHorizontal() throws LikeSeriesExceptionClass{
            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("usuarios").document(user.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Usuario user = documentSnapshot.toObject(Usuario.class);
                    misnegativos.setText(getString(R.string.misnegativos,user.getVotosNegativos()));
                }
            });
        }
}
