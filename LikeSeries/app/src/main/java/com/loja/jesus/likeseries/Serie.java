package com.loja.jesus.likeseries;

import java.util.ArrayList;


public class Serie extends Multimedia{
    public Serie() {
    }

    public Serie(String collection_Serie, String ID_Serie, String titulo_Serie, String descripcion_Serie, String productora_Serie, String director_Serie, String primeraEmision_Serie, String trailer_Serie, String duración_Serie, ArrayList<String> genero_Serie, String imagen_Serie, int votosPositivos_Serie, int votosNegativos_Serie, int votantes_Serie, int NCapitulos, int notamedia_Serie, ArrayList<Votos> votosusuarios, ArrayList<Comentario> comentarios, ArrayList<Votacion_media> votacion_media) {
        super(collection_Serie, ID_Serie, titulo_Serie, descripcion_Serie, productora_Serie, director_Serie, primeraEmision_Serie, trailer_Serie, duración_Serie, genero_Serie, imagen_Serie, votosPositivos_Serie, votosNegativos_Serie, votantes_Serie, NCapitulos, notamedia_Serie, votosusuarios, comentarios, votacion_media);
    }
}
