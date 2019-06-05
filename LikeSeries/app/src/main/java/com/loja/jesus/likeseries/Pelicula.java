package com.loja.jesus.likeseries;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pelicula extends Multimedia{

    public Pelicula() {
    }

    public Pelicula(String collection_Pelicula, String ID_Pelicula, String titulo_Pelicula, String descripcion_Pelicula, String productora_Pelicula, String director_Pelicula, String fechaEstreno_Pelicula, String trailer_Pelicula, String duración_Pelicula, ArrayList<String> genero_Pelicula, String imagen_Pelicula, int votosPositivos_Pelicula, int votosNegativos_Pelicula, int votantes_Pelicula, double notamedia_Pelicula, ArrayList<Votos> votosusuarios, ArrayList<Comentario> comentarios, ArrayList<Votacion_media> votacion_media, ArrayList<String> actores_peliculas) {
        super(collection_Pelicula, ID_Pelicula, titulo_Pelicula, descripcion_Pelicula, productora_Pelicula, director_Pelicula, fechaEstreno_Pelicula, trailer_Pelicula, duración_Pelicula, genero_Pelicula, imagen_Pelicula, votosPositivos_Pelicula, votosNegativos_Pelicula, votantes_Pelicula, notamedia_Pelicula, votosusuarios, comentarios, votacion_media, actores_peliculas);
    }
}
