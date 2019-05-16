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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class Like extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private TextView bienvenida,hola;
private String usuario;
private RecyclerView RVP,RVS,RVVP,RVVN;
private FirebaseAuth mAuth;
private FirebaseUser user;
private FirebaseFirestore db;
private LinearLayout Peliculas,Series,principal_like;
private Spinner spiner;
Context contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        cargarNombreUsuario();
        principal_like = findViewById(R.id.principal_like);
        principal_like.setVisibility(View.VISIBLE);
        //Cargo los recyclerview
        cargarRecycleview();
        //agregarSeriesRapidamente();
        agregarPeliculasRapidamente();
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        //Pantalla de peliculas
        Peliculas=findViewById(R.id.Peliculas);
        //Pantalla de series
        Series=findViewById(R.id.Series);
        contexto = this;
        cargarVotosPositivosenListaHorizontal();
        cargarVotosNegativosenListaHorizontal();
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
        array.add("Drama");
        array.add("Escolares");
        array.add("Shounen");
        array.add("Anime");
        ArrayList<HashMap<String,Object>> arraymapa = new ArrayList<>();
        HashMap<String,Object> map = new HashMap<>();

        map.put("usuario",user.getUid());
        map.put("votopositivo",false);
        map.put("votonegativo",false);
        arraymapa.add(map);

        ArrayList<HashMap<String,Object>> arraymapa2 = new ArrayList<>();
        HashMap<String,Object> map2 = new HashMap<>();

        map2.put("usuario",user.getUid());
        map2.put("nombre",user.getDisplayName());
        map2.put("comentario","Hola , muy buena peli");
        arraymapa2.add(map2);
        HashMap<String,Object> map3 = new HashMap<>();
        map3.put("usuario","6KhLKtDocIfOgySomzeXhygPUmF3");
        map3.put("nombre","J.R");
        map3.put("comentario","Hola ,soy nuevo , me la recomendais , espero que me guste");
        arraymapa2.add(map3);



        db= FirebaseFirestore.getInstance();
        Pelicula pelicula = new Pelicula("Koe no Katachi","La historia gira en torno a Shoko Nishimiya, una estudiante de primaria que es sorda de nacimiento y que al cambiarse de colegio comienza a recibir acoso escolar por parte de sus nuevos compañeros. Uno de los principales responsables es Ishida Shouya quien termina por forzar que Nishimiya se cambie de escuela. Como resultado de los actos contra Shoko las autoridades del colegio toman cartas en el asunto y el curso señala como único responsable a Ishida, quien comienza a sentir el acoso impuesto por sus propios compañeros, al mismo tiempo que termina aislándose de los que alguna vez fueron sus amigos. Años después, Ishida intenta corregir su mal actuar, buscando la redención frente a Nishimiya.","Selecta Vision",array,"gs://likeseries-c426a.appspot.com/imagenesPeliculas/koeno.jpg",0,0,0,0,arraymapa,arraymapa2);
        db.collection("peliculas").document("Koeno").set(pelicula);
    }
    private void agregarSeriesRapidamente()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add("Acción");
        array.add("Aventura");
        array.add("SuperHeroes");
        array.add("Anime");
        ArrayList<HashMap<String,Object>> arraymapa = new ArrayList<>();
        HashMap<String,Object> map = new HashMap<>();

        map.put("usuario",user.getUid());
        map.put("votopositivo",false);
        map.put("votonegativo",false);
        arraymapa.add(map);

        ArrayList<HashMap<String,Object>> arraymapa2 = new ArrayList<>();
        HashMap<String,Object> map2 = new HashMap<>();

        map2.put("usuario",user.getUid());
        map2.put("nombre",user.getDisplayName());
        map2.put("comentario","Buenas,soy un mensaje de ejemplo");
        arraymapa2.add(map2);

        db= FirebaseFirestore.getInstance();
        Serie serie = new Serie("Boku no Hero","Un día, tras conocer personalmente a All Might, este le ofrece heredar sus poderes al ver la gran determinación de Midoriya aunque no tenga poderes; desde entonces, Midoriya accede y empieza a estudiar en la U.A; donde hace nuevos amigos, conoce otros héroes profesionales, aprende a dominar sus poderes y hasta hacer frente a auténticos villanos.","Bones",null,null,array,"gs://likeseries-c426a.appspot.com/imagenesSeries/bokunohero.jpg",0,0,0,0,null);
        db.collection("series").document("Boku").set(serie);
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
     * Metodo que carga todas las tablas de la coleccion que le llega por parametro
     * @param nombreColeccion
     * @param recView
     */
    private void cargarDocumentosPeliculasBD(String nombreColeccion, final RecyclerView recView) {
    db = FirebaseFirestore.getInstance();
    final ArrayList<Pelicula>peliculas = new ArrayList<>();
    db.collection(nombreColeccion)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    String TAG="";
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Pelicula pelicula = document.toObject(Pelicula.class);
                            peliculas.add(pelicula);
                            AdaptadorPeliculas adaptadorPeliculas = new AdaptadorPeliculas(contexto,peliculas);
                            recView.setAdapter(adaptadorPeliculas);
                            adaptadorPeliculas.refrescar();

                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
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
    array.add("Realista");
    array.add("Anime");
    array.add("Aventura");
    array.add("Acción");
    array.add("SuperHeroes");
    array.add("Terror");
    array.add("Más Votados");
    //Para coger un elemento concreto del array y filtrar por ese elemento ;where("género_PEL","array-contains",)
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
                            .whereArrayContains("género_PEL", parent.getItemAtPosition(position).toString())
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
                            .whereArrayContains("género_SER", parent.getItemAtPosition(position).toString())
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

                    /**
                    //Agregamos las peliculas o series mas votadas
                    db.collection("peliculas")
                            .whereArrayContains("género_PEL", parent.getItemAtPosition(position).toString())
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
                            .whereGreaterThan("género_SER", parent.getItemAtPosition(position).toString())
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
                     */
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void cargarVotosPositivosenListaHorizontal() {
        //RVVP.
    }
        private void cargarVotosNegativosenListaHorizontal() {
            //RVVN
        }
}
