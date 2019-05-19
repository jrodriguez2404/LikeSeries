package com.loja.jesus.likeseries;

import java.util.ArrayList;


public class Serie extends Multimedia{
    public Serie() {
    }

    public Serie(String collection,String ID_Serie, String titulo_Serie, String descripcion_Serie, String productora_Serie, ArrayList<String> genero_Serie, String imagen_Serie, int votosPositivos_Serie, int votosNegativos_Serie, int votantes_Serie, int NCapitulos, long notamedia_Serie, ArrayList<Votos> votosusuarios, ArrayList<Comentario> comentarios) {
        super(collection,ID_Serie, titulo_Serie, descripcion_Serie, productora_Serie, genero_Serie, imagen_Serie, votosPositivos_Serie, votosNegativos_Serie, votantes_Serie, NCapitulos, notamedia_Serie, votosusuarios, comentarios);
    }
}
