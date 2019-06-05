package com.loja.jesus.likeseries;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Administrador extends AppCompatActivity implements View.OnClickListener {
private LinearLayout insertarpeli,insertarserie,agregartertulia;
private EditText trailer_peli,trailer_serie,titulo_peli,titulo_serie,descripcion_peli,descripcion_serie,genero1_peli,genero1_serie,genero2_peli,genero2_serie,genero3_peli,genero3_serie,genero4_peli,genero4_serie,imagen_peli,imagen_serie,director_peli,director_serie,estreno_peli,primeraemision_serie,duracion_peli,duracion_serie,productora_peli,productora_serie,numerocapitulos_serie,nombre_tertulia,inicio_tertulia,fin_tertulia,activar_tertulia,actores1_peli,actores1_serie,actores2_peli,actores2_serie,actores3_peli,actores3_serie;
private Button insertar_peli,insertar_serie,insertar_tertulia;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private Context contexto=this;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        declaraciones();

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        toolbar.setTitle(R.string.titulo_Administración);
        setSupportActionBar(toolbar);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText(R.string.insertar_pelicula_admin));
        tabs.addTab(tabs.newTab().setText(R.string.insertar_serie_admin));
        tabs.addTab(tabs.newTab().setText(R.string.administrartertulia));
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println(tab.getPosition());
                if(tab.getPosition()==0)
                {
                    insertarpeli.setVisibility(View.VISIBLE);
                    insertarserie.setVisibility(View.GONE);
                    agregartertulia.setVisibility(View.GONE);
                }
                else if(tab.getPosition()==1)
                {
                    insertarpeli.setVisibility(View.GONE);
                    insertarserie.setVisibility(View.VISIBLE);
                    agregartertulia.setVisibility(View.GONE);
                }
                else if(tab.getPosition()==2)
                {
                    insertarpeli.setVisibility(View.GONE);
                    insertarserie.setVisibility(View.GONE);
                    agregartertulia.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void declaraciones()
    {
                        actores1_peli = findViewById(R.id.textoactor1_peli);
                        actores1_serie=findViewById(R.id.textoactor1_serie);
        actores2_peli = findViewById(R.id.textoactor2_peli);
        actores2_serie=findViewById(R.id.textoactor2_serie);
        actores3_peli = findViewById(R.id.textoactor3_peli);
        actores3_serie=findViewById(R.id.textoactor3_serie);
                        insertar_tertulia=findViewById(R.id.insertar__);
                        insertar_tertulia.setOnClickListener(this);
                        nombre_tertulia=findViewById(R.id.textontertulia);
                        inicio_tertulia=findViewById(R.id.textoitertulia);
                        fin_tertulia=findViewById(R.id.textoftertulia);
                        activar_tertulia=findViewById(R.id.textoatertulia);
                        insertarpeli=findViewById(R.id.insertarpelicula_view);
                        insertarserie=findViewById(R.id.insertarserie_view);
                        agregartertulia=findViewById(R.id.agregartertulia_view);
                        insertar_peli=findViewById(R.id.insertar);
                        insertar_peli.setOnClickListener(this);
                        insertar_serie=findViewById(R.id.insertar_);
                        insertar_serie.setOnClickListener(this);
                        trailer_peli=findViewById(R.id.textotrailer_peli);
                        trailer_serie=findViewById(R.id.textotrailer_serie);
                        titulo_peli=findViewById(R.id.textotitulo_peli);
                        titulo_serie=findViewById(R.id.textotitulo_serie);
                        descripcion_peli=findViewById(R.id.textodescripcion_peli);
                        descripcion_serie=findViewById(R.id.textodescripcion_serie);
                        genero1_peli=findViewById(R.id.textogenero1_peli);
                        genero1_serie=findViewById(R.id.textogenero1_serie);
                        genero2_peli=findViewById(R.id.textogenero2_peli);
                        genero2_serie=findViewById(R.id.textogenero2_serie);
                        genero3_peli=findViewById(R.id.textogenero3_peli);
                        genero3_serie=findViewById(R.id.textogenero3_serie);
                        genero4_peli=findViewById(R.id.textogenero4_peli);
                        genero4_serie=findViewById(R.id.textogenero4_serie);
                        imagen_peli=findViewById(R.id.textoimagen_peli);
                        imagen_serie=findViewById(R.id.textoimagen_serie);
                        director_peli=findViewById(R.id.textodirector_peli);
                        director_serie=findViewById(R.id.textodirector_serie);
                        estreno_peli=findViewById(R.id.textoestreno_peli);
                        primeraemision_serie=findViewById(R.id.textoprimeraemision_serie);
                        duracion_peli=findViewById(R.id.textoduración_peli);
                        duracion_serie=findViewById(R.id.textoduración_serie);
                        productora_peli=findViewById(R.id.textoproductora_peli);
                        productora_serie=findViewById(R.id.textoproductora_serie);
                        numerocapitulos_serie=findViewById(R.id.textonumerocapitulos_serie);
    }
    private void insertarPeli()
    {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db= FirebaseFirestore.getInstance();
        ArrayList<String> array = new ArrayList<>();
        array.add(genero1_peli.getText().toString());
        array.add(genero2_peli.getText().toString());
        array.add(genero3_peli.getText().toString());
        array.add(genero4_peli.getText().toString());
        ArrayList<String>arrayactores = new ArrayList<>();
                arrayactores.add(actores1_peli.getText().toString());
        arrayactores.add(actores2_peli.getText().toString());
        arrayactores.add(actores3_peli.getText().toString());
        ArrayList<Votos> votos = new ArrayList<>();

        ArrayList<Comentario> comentarios = new ArrayList<>();

        ArrayList<Votacion_media> votacion_media = new ArrayList<>();


        db= FirebaseFirestore.getInstance();

        Pelicula pelicula = new Pelicula("peliculas",titulo_peli.getText().toString().replace(" ",""),titulo_peli.getText().toString(),descripcion_peli.getText().toString(),productora_peli.getText().toString(),director_peli.getText().toString(),estreno_peli.getText().toString(),trailer_peli.getText().toString(),duracion_peli.getText().toString(),array,imagen_peli.getText().toString(),0,0,0,0,votos,comentarios,votacion_media,arrayactores);
        db.collection("peliculas").document(pelicula.getID_Pelicula()).set(pelicula);
        actores1_peli.setText("");
        actores2_peli.setText("");
        actores3_peli.setText("");
        trailer_peli.setText("");
        titulo_peli.setText("");
        descripcion_peli.setText("");
        genero1_peli.setText("");
        genero2_peli.setText("");
        genero3_peli.setText("");
        genero4_peli.setText("");
        imagen_peli.setText("");
        director_peli.setText("");
        estreno_peli.setText("");
        duracion_peli.setText("");
        productora_peli.setText("");

        Toast.makeText(getApplicationContext(),
                "Película insertada con exito", Toast.LENGTH_LONG).show();
    }
    private void insertarSerie()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add(genero1_serie.getText().toString());
        array.add(genero2_serie.getText().toString());
        array.add(genero3_serie.getText().toString());
        array.add(genero4_serie.getText().toString());

        ArrayList<Votos> votos = new ArrayList<>();

        ArrayList<Comentario> comentarios = new ArrayList<>();

        ArrayList<Votacion_media> votacion_media = new ArrayList<>();

        ArrayList<String>arrayactores = new ArrayList<>();
        arrayactores.add(actores1_serie.getText().toString());
        arrayactores.add(actores2_serie.getText().toString());
        arrayactores.add(actores3_serie.getText().toString());
        db= FirebaseFirestore.getInstance();
        Serie serie = new Serie("series",titulo_serie.getText().toString().replace(" ",""),titulo_serie.getText().toString(),descripcion_serie.getText().toString(),productora_serie.getText().toString(),director_serie.getText().toString(),primeraemision_serie.getText().toString(),trailer_serie.getText().toString(),duracion_serie.getText().toString(),array,imagen_serie.getText().toString(),0,0,0, Integer.parseInt(numerocapitulos_serie.getText().toString()),0,votos,comentarios,votacion_media,arrayactores);
        db.collection("series").document(serie.getID_Serie()).set(serie);

        genero1_serie.setText("");
        genero2_serie.setText("");
        genero3_serie.setText("");
        genero4_serie.setText("");
        titulo_serie.setText("");
        descripcion_peli.setText("");
        trailer_serie.setText("");
        imagen_serie.setText("");
        director_serie.setText("");
        primeraemision_serie.setText("");
        duracion_serie.setText("");
        productora_serie.setText("");
        numerocapitulos_serie.setText("");
        actores1_serie.setText("");
        actores2_serie.setText("");
        actores3_serie.setText("");


        Toast.makeText(getApplicationContext(),
                "Serie insertada con exito", Toast.LENGTH_LONG).show();
    }
    private void agregarTertulia()
    {
        db= FirebaseFirestore.getInstance();
        ArrayList<Tertulia> arraytertulia = new ArrayList<>();
        ArrayList<Chat> arraychat = new ArrayList<>();
        Tertulia tertulia = new Tertulia(nombre_tertulia.getText().toString(),inicio_tertulia.getText().toString(),fin_tertulia.getText().toString(), Integer.parseInt(activar_tertulia.getText().toString()),arraychat);
        arraytertulia.add(tertulia);
        Tertulias tertulias = new Tertulias(arraytertulia);
        db.collection("tertulia").document(nombre_tertulia.getText().toString().replace(" ","")).set(tertulias);


        nombre_tertulia.setText("");
        inicio_tertulia.setText("");
        fin_tertulia.setText("");
        activar_tertulia.setText("");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.insertar:
                if(!titulo_peli.equals("")) {
                    insertarPeli();
                }
                break;

            case R.id.insertar_:
                if(!titulo_serie.equals("")) {
                    insertarSerie();
                }
                break;
            case R.id.insertar__:
                if(!nombre_tertulia.equals("")) {
                    agregarTertulia();
                }
                break;
        }
    }
}
