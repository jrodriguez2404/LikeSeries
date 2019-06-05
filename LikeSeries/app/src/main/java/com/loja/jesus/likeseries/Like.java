package com.loja.jesus.likeseries;

import android.annotation.SuppressLint;
import android.content.ClipData;
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
private TextView bienvenida,hola, mispositivos,misnegativos,botoncomentario_chat,cajaTexto_chat,cerrar_chat,tipousuariodrawer,tipousuario,vaciarchat;
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
        vaciarchat = findViewById(R.id.eliminartodo_administrador);
        PersistenciaFirebase p = new PersistenciaFirebase();
        p.persistenciaFirebase();
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
                public void onClick(final View view) {

                    mAuth = FirebaseAuth.getInstance();
                    user = mAuth.getCurrentUser();
                    db = FirebaseFirestore.getInstance();


                    DocumentReference admin = db.collection("usuarios").document(user.getUid());
                    admin.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Usuario user = documentSnapshot.toObject(Usuario.class);

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

                            if(user.getAdministrador()==1)
                            {
                                vaciarchat.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                vaciarchat.setVisibility(View.GONE);
                            }
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
                tipousuariodrawer=drawer.findViewById(R.id.tipousuariodrawer);
                mAuth = FirebaseAuth.getInstance();
                user = mAuth.getCurrentUser();
                db= FirebaseFirestore.getInstance();



                    DocumentReference docRef = db.collection("usuarios").document(user.getUid());
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @SuppressLint("ResourceType")
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Usuario user = documentSnapshot.toObject(Usuario.class);
                            usuario=user.getNombre();
                            try {
                            bienvenida.setText(getResources().getString(R.string.bienvenida,usuario));

                                if (user.getAdministrador() == 1) {

                                    bienvenida.setTextColor(R.color.administrador_color);
                                    tipousuariodrawer.setBackgroundResource(R.drawable.administrador);
                                    int ancho = 50;
                                    int alto = 50;
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ancho, alto);
                                    tipousuariodrawer.setLayoutParams(params);
                                } else {
                                    tipousuariodrawer.setBackgroundResource(R.drawable.usuario);
                                    int ancho = 50;
                                    int alto = 50;

                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ancho, alto);
                                    tipousuariodrawer.setLayoutParams(params);
                                }
                            }
                            catch (Exception e) {
                            }

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
        vaciarchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                arraychat.clear();
                DocumentReference docRef = db.collection("chatgeneral").document("chat");
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Chat chat= new Chat(arraychat);
                        db.collection("chatgeneral").document("chat").set(chat);
                    }
                });
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
                        if(!cajaTexto_chat.getText().toString().equals("")) {
                            ChatGeneral chatGeneral = new ChatGeneral(user.getDisplayName(), user.getUid(), cajaTexto_chat.getText().toString(), null);
                            arraychat.add(chatGeneral);
                        }
                        else if(cajaTexto_chat.getText().toString().equals(""))
                        {
                            Toast.makeText(getApplicationContext(),
                                    "Porfavor, escribe algo", Toast.LENGTH_LONG).show();
                        }
                        try {
                            for (int i = 0; i < chat.getChatgeneral().size(); i++) {

                                    if(arraychat.get(i).getNombre().equals(chat.getChatgeneral().get(i).getNombre())&&arraychat.get(i).getMensaje().equals(chat.getChatgeneral().get(i).getMensaje()))
                                    {
                                        Toast.makeText(getApplicationContext(),
                                                "Porfavor, no escribas lo mismo", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        arraychat.add(chat.getChatgeneral().get(i));
                                    }

                            }
                                chat = new Chat(arraychat);
                                db.collection("chatgeneral").document("chat").set(chat);
                                AdaptadorChat adaptadorChat = new AdaptadorChat(contexto, arraychat,"chat");
                                RVCR.setAdapter(adaptadorChat);
                                RVCR.setHasFixedSize(true);
                                adaptadorChat.refrescar();


                        }
                        catch (Exception e)
                        {

                        }


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
                    AdaptadorChat adaptadorChat = new AdaptadorChat(contexto, arraychat,"chat");
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
        tipousuario=findViewById(R.id.tipousuario);

        try {
            cargarRecycleview();
            cargarRecycerViewVotos();
            cargarVotosPositivosenListaHorizontal();
            cargarVotosNegativosenListaHorizontal();
            cargarNombreUsuario();
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
            @SuppressLint({"StringFormatMatches", "ResourceType"})
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario user = documentSnapshot.toObject(Usuario.class);
                usuario=user.getNombre();
                hola.setText(getResources().getString(R.string.bienvenida,usuario));
                try {
                    if (user.getAdministrador() == 1) {
                        tipousuario.setBackgroundResource(R.drawable.administrador);
                        int ancho = 50;
                        int alto = 50;

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ancho, alto);
                        tipousuario.setLayoutParams(params);
                        hola.setTextColor(R.color.administrador_color);
                    } else {
                        tipousuario.setBackgroundResource(R.drawable.usuario);
                        int ancho = 50;
                        int alto = 50;

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ancho, alto);
                        tipousuario.setLayoutParams(params);
                    }
                }
                catch (Exception e)
                {

                }
            }
        });
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
                                    Multimedia multimedia = document.toObject(Multimedia.class);
                                    for (int i = 0; i < multimedia.getVotosusuarios().size(); i++) {
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
                                    Multimedia multimedia = document.toObject(Multimedia.class);
                                    for (int i = 0; i < multimedia.getVotosusuarios().size(); i++) {

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
    public boolean onNavigationItemSelected(final MenuItem item) {
        // Handle navigation view item clicks here.


        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        user=mAuth.getCurrentUser();
        DocumentReference docRef = db.collection("usuarios").document(user.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario user = documentSnapshot.toObject(Usuario.class);
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
                    Intent miperfil = new Intent(contexto, miperfil.class);
                    startActivity(miperfil);
                    pantalla_linear.setVisibility(View.VISIBLE);
                    cajaTexto_chat.setText("");
                }
                else if (id == R.id.cerrar_sesion) {
                    mAuth = FirebaseAuth.getInstance();
                    db= FirebaseFirestore.getInstance();

                    DocumentReference docRef = db.collection("usuarios").document(user.getUID());
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
                else if(id==R.id.creador)
                {
                    Intent intent = new Intent(contexto,Info.class);
                    startActivity(intent);
                }
                else if(id==R.id.tertulia)
                {
                    Intent intent = new Intent(contexto,TertuliaLike.class);
                    startActivity(intent);
                }
                else if (id == R.id.administrador) {
                    if(user.getAdministrador()==1)
                    {
                        Intent intent = new Intent(contexto,Administrador.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                "Usted no es administrador", Toast.LENGTH_LONG).show();
                    }
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

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
    array.add("Los 5 más votados");
    array.add("Los 5 mejor valorados");
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
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                final ArrayList<Pelicula>peliculas = new ArrayList<>();
                peliculas.clear();
                final ArrayList<Serie>series = new ArrayList<>();
                series.clear();
                db = FirebaseFirestore.getInstance();

                if(!parent.getItemAtPosition(position).toString().equals("Los 5 más votados") && !parent.getItemAtPosition(position).toString().equals("Sin filtro")&&!parent.getItemAtPosition(position).toString().equals("Los 5 mejor valorados")) {

                    AdaptadorPeliculas adaptadorPeliculas = new AdaptadorPeliculas(contexto, peliculas,"vacio");
                    RVP.setAdapter(adaptadorPeliculas);
                    adaptadorPeliculas.refrescar();

                    db.collection("peliculas")
                            .whereArrayContains("genero_Pelicula", parent.getItemAtPosition(position).toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        peliculas.clear();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                Pelicula pelicula = document.toObject(Pelicula.class);
                                                peliculas.add(pelicula);
                                                AdaptadorPeliculas adaptadorPeliculas = new AdaptadorPeliculas(contexto, peliculas, "");
                                                RVP.setAdapter(adaptadorPeliculas);
                                                adaptadorPeliculas.refrescar();
                                        }

                                    }

                                }
                            });


                    AdaptadorSeries adaptadorSeries = new AdaptadorSeries(contexto, series, "vacio");
                    RVS.setAdapter(adaptadorSeries);
                    adaptadorSeries.refrescar();
                    db.collection("series")
                            .whereArrayContains("genero_Serie", parent.getItemAtPosition(position).toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        series.clear();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                Serie serie = document.toObject(Serie.class);
                                                    series.add(serie);
                                                    AdaptadorSeries adaptadorSeries = new AdaptadorSeries(contexto, series, "");
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
                            .orderBy("titulo_Pelicula", Query.Direction.ASCENDING)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(task.getResult().size()>0) {
                                                Pelicula pelicula = document.toObject(Pelicula.class);
                                                peliculas.add(pelicula);
                                                AdaptadorPeliculas adaptadorPeliculas = new AdaptadorPeliculas(contexto, peliculas, "");
                                                RVP.setAdapter(adaptadorPeliculas);
                                                adaptadorPeliculas.refrescar();
                                            }
                                            else
                                            {
                                                AdaptadorPeliculas adaptadorPeliculas = new AdaptadorPeliculas(contexto, peliculas, "vacio");
                                                RVP.setAdapter(adaptadorPeliculas);
                                                adaptadorPeliculas.refrescar();
                                            }

                                        }
                                    }
                                }
                            });
                    db.collection("series")
                            .orderBy("titulo_Serie", Query.Direction.ASCENDING)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(task.getResult().size()>0) {
                                                Serie serie = document.toObject(Serie.class);
                                                series.add(serie);
                                                AdaptadorSeries adaptadorSeries = new AdaptadorSeries(contexto, series, "");
                                                RVS.setAdapter(adaptadorSeries);
                                                adaptadorSeries.refrescar();
                                            }
                                            else
                                            {
                                                AdaptadorSeries adaptadorSeries = new AdaptadorSeries(contexto, series, "vacio");
                                                RVS.setAdapter(adaptadorSeries);
                                                adaptadorSeries.refrescar();
                                            }
                                        }
                                    }
                                }
                            });
                }
                else if(parent.getItemAtPosition(position).toString().equals("Los 5 más votados"))
                {

                    db.collection("peliculas")
                            .orderBy("votosPositivos_Pelicula", Query.Direction.DESCENDING)
                            .limit(5)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(task.getResult().size()>0) {
                                                Pelicula pelicula = document.toObject(Pelicula.class);
                                                peliculas.add(pelicula);
                                                AdaptadorPeliculas adaptadorPeliculas = new AdaptadorPeliculas(contexto, peliculas, "");
                                                RVP.setAdapter(adaptadorPeliculas);
                                                adaptadorPeliculas.refrescar();
                                            }
                                            else
                                            {
                                                AdaptadorPeliculas adaptadorPeliculas = new AdaptadorPeliculas(contexto, peliculas, "vacio");
                                                RVP.setAdapter(adaptadorPeliculas);
                                                adaptadorPeliculas.refrescar();
                                            }
                                        }
                                    }
                                }
                            });
                    db.collection("series")
                            .orderBy("votosPositivos_Series", Query.Direction.DESCENDING)
                            .limit(5)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(task.getResult().size()>0) {
                                                Serie serie = document.toObject(Serie.class);
                                                series.add(serie);
                                                AdaptadorSeries adaptadorSeries = new AdaptadorSeries(contexto, series, "");
                                                RVS.setAdapter(adaptadorSeries);
                                                adaptadorSeries.refrescar();
                                            }
                                            else
                                            {
                                                AdaptadorSeries adaptadorSeries = new AdaptadorSeries(contexto, series, "vacio");
                                                RVS.setAdapter(adaptadorSeries);
                                                adaptadorSeries.refrescar();
                                            }
                                        }
                                    }
                                }
                            });
                }
                else if(parent.getItemAtPosition(position).toString().equals("Los 5 mejor valorados"))
                {

                    db.collection("peliculas")
                            .orderBy("notamedia_Pelicula", Query.Direction.DESCENDING)
                            .limit(5)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(task.getResult().size()>0) {


                                                Pelicula pelicula = document.toObject(Pelicula.class);
                                                peliculas.add(pelicula);
                                                AdaptadorPeliculas adaptadorPeliculas = new AdaptadorPeliculas(contexto, peliculas, "");
                                                RVP.setAdapter(adaptadorPeliculas);
                                                adaptadorPeliculas.refrescar();
                                            }
                                            else
                                            {
                                                AdaptadorPeliculas adaptadorPeliculas = new AdaptadorPeliculas(contexto, peliculas, "vacio");
                                                RVP.setAdapter(adaptadorPeliculas);
                                                adaptadorPeliculas.refrescar();
                                            }
                                        }

                                    }
                                }
                            });
                    db.collection("series")
                            .orderBy("notamedia_Series", Query.Direction.DESCENDING)
                            .limit(5)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(task.getResult().size()>0) {
                                                Serie serie = document.toObject(Serie.class);
                                                series.add(serie);
                                                AdaptadorSeries adaptadorSeries = new AdaptadorSeries(contexto, series, "");
                                                RVS.setAdapter(adaptadorSeries);
                                                adaptadorSeries.refrescar();
                                            }
                                            else
                                            {
                                                AdaptadorSeries adaptadorSeries = new AdaptadorSeries(contexto, series, "vacio");
                                                RVS.setAdapter(adaptadorSeries);
                                                adaptadorSeries.refrescar();
                                            }
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
