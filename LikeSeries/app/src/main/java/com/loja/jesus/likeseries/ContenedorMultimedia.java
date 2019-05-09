package com.loja.jesus.likeseries;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

public class ContenedorMultimedia extends AppCompatActivity implements View.OnClickListener {
private TextView titulo_multi,descripcion_multi,genero0_multi,genero1_multi,genero2_multi,votopositivo,votonegativo;
private ImageView imagenvotopositivo,imagenvotonegativos,imagen_multi,like,nolike;
private String titulo;
private Boolean contenedorpositivo,contenedornegativo,vnegativo,vpositivo;
private Integer votospositivos,votosnegativos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_multimedia);
        declaraciones();
        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        like=findViewById(R.id.like);
        nolike=findViewById(R.id.nolike);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatosBD();
            finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        declaraciones();
        if(vnegativo)
        {
            imagenvotonegativos.setImageResource(R.drawable.nolike);
            vnegativo = false;
            actualizarVotos();
        }
        else
        {
            imagenvotonegativos.setImageResource(R.drawable.unnolike);
            vnegativo = true;
            actualizarVotos();
        }
        if(vpositivo)
        {
            imagenvotopositivo.setImageResource(R.drawable.like);
            vpositivo=false;
            actualizarVotos();
        }
        else
        {
            imagenvotopositivo.setImageResource(R.drawable.unlike);
            vpositivo=true;
            actualizarVotos();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        actualizarDatosBD();
    }
    private void declaraciones()
    {
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
        cogerExtras();
    }
    private void cogerExtras()
    {
        Intent intent = getIntent();
        titulo = intent.getStringExtra("titulo");
        titulo_multi.setText(titulo);
        String descripcion = intent.getStringExtra("descripcion");
        descripcion_multi.setText(descripcion);
        ArrayList<String> genero= intent.getStringArrayListExtra("genero");
        genero0_multi.setText(genero.get(0).toString());
        genero1_multi.setText(genero.get(1).toString());
        genero2_multi.setText(genero.get(2).toString());

         votospositivos = intent.getIntExtra("votosmas",0);
         votosnegativos = intent.getIntExtra("votosmenos",0);
        Integer mediacontador = intent.getIntExtra("mediavotos",0);

        final String comentarios =intent.getStringExtra("comentarios");
        String usuariocomentario =intent.getStringExtra("usuariocomentario");
        HashMap<String,Object> Comentario = new HashMap<>();
        Comentario.put("usuario",usuariocomentario);
        Comentario.put("comentario",comentarios);


        byte[] imagen_bytes = intent.getByteArrayExtra("imagen");
        Bitmap imagen= BitmapFactory.decodeByteArray(imagen_bytes,0,imagen_bytes.length);
        imagen_multi.setImageBitmap(imagen);



         vnegativo = intent.getBooleanExtra("votonegativo",false);
         vpositivo = intent.getBooleanExtra("votopositivo",false);
        imagenvotopositivo.setOnClickListener(this);
        imagenvotonegativos.setOnClickListener(this);
    }
    private void actualizarVotos()
    {
        votopositivo.setText(votospositivos.toString());
        votonegativo.setText(votosnegativos.toString());


    }
    private void insertarComentarios()
    {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.like:
                if(vpositivo) {
                    imagenvotopositivo.setImageResource(R.drawable.like);
                    vpositivo = false;
                    votospositivos++;

                    imagenvotonegativos.setImageResource(R.drawable.unnolike);
                    vnegativo = true;
                    if (votosnegativos > 0) {votosnegativos--;
                    }


                    actualizarVotos();
            }
                else if (!vpositivo )
            {
                imagenvotopositivo.setImageResource(R.drawable.unlike);
                vpositivo=true;
                votospositivos--;
                actualizarVotos();
            }
                break;

            case R.id.nolike:

                if(vnegativo)
                {
                    imagenvotonegativos.setImageResource(R.drawable.nolike);
                    votosnegativos++;
                    imagenvotopositivo.setImageResource(R.drawable.unlike);
                    vpositivo=true;
                    vnegativo = false;
                    if(votospositivos>0) {
                        votospositivos--;
                    }
                    actualizarVotos();
                }
                else if (!vnegativo)
            {
                imagenvotonegativos.setImageResource(R.drawable.unnolike);
                vnegativo = true;
                votosnegativos--;
                actualizarVotos();
            }
                break;
        }

    }

    /**
     * Metodo que llama a una función de firebase para subir los nuevos datos actualizados
     */
    private void actualizarDatosBD()
    {

    }
}
