package com.loja.jesus.likeseries;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContenedorMultimedia extends AppCompatActivity {
private TextView titulo_multi,descripcion_multi,genero0_multi,genero1_multi,genero2_multi,votos_multi;
private ImageView imagen_multi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_multimedia);
        declaraciones();
    }
    private void declaraciones()
    {
        titulo_multi = findViewById(R.id.titulo_Multimedia);
        descripcion_multi = findViewById(R.id.descripcion_Multimedia);
        genero0_multi = findViewById(R.id.genero1_multimedia);
        genero1_multi = findViewById(R.id.genero2_multimedia);
        genero2_multi = findViewById(R.id.genero3_multimedia);
        votos_multi = findViewById(R.id.votos_multimedia);
        imagen_multi = findViewById(R.id.imagen_Multimedia);
        cogerExtras();
    }
    private void cogerExtras()
    {
        Intent intent = getIntent();
        String titulo = intent.getStringExtra("titulo");
        titulo_multi.setText(titulo);
        String descripcion = intent.getStringExtra("descripcion");
        descripcion_multi.setText(descripcion);
        ArrayList<String> genero= intent.getStringArrayListExtra("genero");
        genero0_multi.setText(genero.get(0).toString());
        genero1_multi.setText(genero.get(1).toString());
        genero2_multi.setText(genero.get(2).toString());
        Integer votos = intent.getIntExtra("votos",0);
        votos_multi.setText(votos);
        byte[] imagen_bytes = intent.getByteArrayExtra("imagen");
        Bitmap imagen= BitmapFactory.decodeByteArray(imagen_bytes,0,imagen_bytes.length);
        imagen_multi.setImageBitmap(imagen);
    }
    private void actualizarVotos()
    {

    }
    private void sumarVotoUsuario()
    {

    }
    private void insertarComentarios()
    {

    }
}
