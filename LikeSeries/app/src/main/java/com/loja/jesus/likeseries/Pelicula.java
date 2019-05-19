package com.loja.jesus.likeseries;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pelicula extends Multimedia{
    public Pelicula() {
    }

    public Pelicula(String collection,String ID_Pelicula, String titulo_Pelicula, String descripcion_Pelicula, String productora_Pelicula, ArrayList<String> genero_Pelicula, String imagen_Pelicula, int votosPositivos_Pelicula, int votosNegativos_Pelicula, int votantes_Pelicula, long notamedia_Pelicula, ArrayList<Votos> votosusuarios, ArrayList<Comentario> comentarios) {
        super(collection,ID_Pelicula, titulo_Pelicula, descripcion_Pelicula, productora_Pelicula, genero_Pelicula, imagen_Pelicula, votosPositivos_Pelicula, votosNegativos_Pelicula, votantes_Pelicula, notamedia_Pelicula, votosusuarios, comentarios);
    }
}
