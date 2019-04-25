package com.loja.jesus.likeseries;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Like extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RadioGroup.OnCheckedChangeListener {
private TextView nombre;
private RecyclerView RVPR,RVPA,s_RVPA,s_RVPR;
private FirebaseAuth mAuth;
private FirebaseUser user;
private FirebaseFirestore db;
private RadioGroup peli_serie,anime_real;
private LinearLayout Peliculas,Series,AnimeP,RealistaP,AnimeS,RealistaS;
Context contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        cargarRecycleview();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Pantalla de peliculas
        Peliculas=findViewById(R.id.Peliculas);
        Series=findViewById(R.id.Series);

        contexto = this;

        AnimeS = findViewById(R.id.s_anime);
        AnimeP = findViewById(R.id.s_realista);

        peli_serie = findViewById(R.id.peli_serie);
        anime_real = findViewById(R.id.anime_real);

        peli_serie.clearCheck();

        peli_serie.setOnCheckedChangeListener(this);
        anime_real.setOnCheckedChangeListener(this);
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
            public void onDrawerOpened(@NonNull View view) {
                mAuth = FirebaseAuth.getInstance();
                user = mAuth.getCurrentUser();
                nombre = drawer.findViewById(R.id.nombre);
                db= FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("usuarios").document(user.getUid());
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Usuario usuario = documentSnapshot.toObject(Usuario.class);
                        nombre.setText(usuario.getNombre());
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
    private void agregarPeliculasRapidamente()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add("Acción");
        array.add("Aventura");
        array.add("Romance");
        db= FirebaseFirestore.getInstance();
        Pelicula pelicula = new Pelicula("Alita: Ángel de combate","Alita, una joven en el cuerpo de un cyborg, que es rescatada por un científico tras ser encontrada entre los escombros de un desguace. Alita es reconstruida poco a poco, pero se da cuenta que ha perdido la memoria y no recuerda nada de lo que ha sido de ella antes de aterrizar en el desguace. La joven verá en el científico a su salvador, una figura paterna que la convertirá en un ser letal y peligroso para conseguir así un trabajo como cazarrecompensas.","Twentieth Century Fox España",array,"gs://likeseries-c426a.appspot.com/imagenesPeliculasNA/alita.png",0);
        db.collection("peliculas").document("1").set(pelicula);
    }
    private void cargarRecycleview(){
        RVPR = findViewById(R.id.RVPR);
        RVPA = findViewById(R.id.RVPA);
        s_RVPA = findViewById(R.id.s_RVPA);
        s_RVPR = findViewById(R.id.s_RVPR);

        LinearLayoutManager llmRVPR = new LinearLayoutManager(this);
        RVPR.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space, true, true));
        RVPR.setHasFixedSize(true);
        RVPR.setLayoutManager(llmRVPR);
        LinearLayoutManager llmRVPA = new LinearLayoutManager(this);
        RVPA.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space, true, true));
        RVPA.setHasFixedSize(true);
        RVPA.setLayoutManager(llmRVPA);
        LinearLayoutManager llms_RVPA = new LinearLayoutManager(this);
        s_RVPA.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space, true, true));
        s_RVPA.setHasFixedSize(true);
        s_RVPA.setLayoutManager(llms_RVPA);
        LinearLayoutManager llms_RVPR = new LinearLayoutManager(this);
        s_RVPR.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space, true, true));
        s_RVPR.setHasFixedSize(true);
        s_RVPR.setLayoutManager(llms_RVPR);


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

        if (id == R.id.peliculas) {

        } else if (id == R.id.series) {

        }
        else if (id == R.id.mi_perfil) {
            Intent miperfil = new Intent(this, miperfil.class);
            startActivity(miperfil);
        }
        else if (id == R.id.cerrar_sesion) {

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent login = new Intent(getApplication(), MainLogin.class);
                            startActivity(login);
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
    private void cargarDocumentosBD(String nombreColeccion, final RecyclerView recView) {
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(group.getId())
        {
            case R.id.peli_serie:
                switch(checkedId)
                {
                    case R.id.rPelicula:
                        Peliculas.setVisibility(View.VISIBLE);
                        Series.setVisibility(View.GONE);

                        AnimeP = findViewById(R.id.ventana_anime_P);
                        RealistaP = findViewById(R.id.ventana_realista_P);
                        break;
                    case R.id.rSerie:
                        Peliculas.setVisibility(View.GONE);
                        Series.setVisibility(View.VISIBLE);

                        AnimeP = findViewById(R.id.ventana_anime_P);
                        RealistaP = findViewById(R.id.ventana_realista_P);
                        break;
                }
                break;
            case R.id.anime_real:
                switch (checkedId)
                {
                    case R.id.rAnime:
                        AnimeP.setVisibility(View.VISIBLE);
                        RealistaP.setVisibility(View.GONE);
                        cargarDocumentosBD("peliculasA",RVPA);
                        break;
                    case R.id.rRealista:
                        AnimeP.setVisibility(View.GONE);
                        RealistaP.setVisibility(View.VISIBLE);
                        cargarDocumentosBD("peliculas",RVPR);
                        break;
                }
        }
    }

    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        public SpaceItemDecoration(Like like, Object p1, boolean b, boolean b1) {

        }
    }
}
