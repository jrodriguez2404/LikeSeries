package com.loja.jesus.likeseries;

import java.util.ArrayList;
import java.util.HashMap;

public class Multimedia {
    //Esta clase sirve para elegir los votos positivos y negativos de las series y peliculas
    private String collection_Pelicula,collection_Serie,ID_Pelicula,Titulo_Pelicula,Descripcion_Pelicula,Productora_Pelicula,Director_Pelicula,FechaEstreno_Pelicula,Trailer_Pelicula,Duración_Pelicula,ID_Serie,Titulo_Serie,Descripcion_Serie,Productora_Serie,Director_Serie,PrimeraEmision_Serie,Trailer_Serie,Duración_Serie;
    private ArrayList<String> Genero_Pelicula,Genero_Serie;
    private String Imagen_Pelicula,Imagen_Serie;
    private int votosPositivos_Pelicula,votosNegativos_Pelicula,votantes_Pelicula,votosPositivos_Serie,votosNegativos_Serie,votantes_Serie,NCapitulos;
    private double notamedia_Pelicula,notamedia_Serie;
    private ArrayList<Votos> votosusuarios;
    private ArrayList<Comentario> comentarios;
    private ArrayList<Votacion_media> votacion_media;
    private ArrayList<String> actores_peliculas,actores_series;

    public Multimedia() {
    }

    public Multimedia(String collection_Pelicula, String ID_Pelicula, String titulo_Pelicula, String descripcion_Pelicula, String productora_Pelicula, String director_Pelicula, String fechaEstreno_Pelicula, String trailer_Pelicula, String duración_Pelicula, ArrayList<String> genero_Pelicula, String imagen_Pelicula, int votosPositivos_Pelicula, int votosNegativos_Pelicula, int votantes_Pelicula, double notamedia_Pelicula, ArrayList<Votos> votosusuarios, ArrayList<Comentario> comentarios, ArrayList<Votacion_media> votacion_media, ArrayList<String> actores_peliculas) {
        this.collection_Pelicula = collection_Pelicula;
        this.ID_Pelicula = ID_Pelicula;
        Titulo_Pelicula = titulo_Pelicula;
        Descripcion_Pelicula = descripcion_Pelicula;
        Productora_Pelicula = productora_Pelicula;
        Director_Pelicula = director_Pelicula;
        FechaEstreno_Pelicula = fechaEstreno_Pelicula;
        Trailer_Pelicula = trailer_Pelicula;
        Duración_Pelicula = duración_Pelicula;
        Genero_Pelicula = genero_Pelicula;
        Imagen_Pelicula = imagen_Pelicula;
        this.votosPositivos_Pelicula = votosPositivos_Pelicula;
        this.votosNegativos_Pelicula = votosNegativos_Pelicula;
        this.votantes_Pelicula = votantes_Pelicula;
        this.notamedia_Pelicula = notamedia_Pelicula;
        this.votosusuarios = votosusuarios;
        this.comentarios = comentarios;
        this.votacion_media = votacion_media;
        this.actores_peliculas = actores_peliculas;
    }

    public Multimedia(String collection_Serie, String ID_Serie, String titulo_Serie, String descripcion_Serie, String productora_Serie, String director_Serie, String primeraEmision_Serie, String trailer_Serie, String duración_Serie, ArrayList<String> genero_Serie, String imagen_Serie, int votosPositivos_Serie, int votosNegativos_Serie, int votantes_Serie, int NCapitulos, double notamedia_Serie, ArrayList<Votos> votosusuarios, ArrayList<Comentario> comentarios, ArrayList<Votacion_media> votacion_media, ArrayList<String> actores_series) {
        this.collection_Serie = collection_Serie;
        this.ID_Serie = ID_Serie;
        Titulo_Serie = titulo_Serie;
        Descripcion_Serie = descripcion_Serie;
        Productora_Serie = productora_Serie;
        Director_Serie = director_Serie;
        PrimeraEmision_Serie = primeraEmision_Serie;
        Trailer_Serie = trailer_Serie;
        Duración_Serie = duración_Serie;
        Genero_Serie = genero_Serie;
        Imagen_Serie = imagen_Serie;
        this.votosPositivos_Serie = votosPositivos_Serie;
        this.votosNegativos_Serie = votosNegativos_Serie;
        this.votantes_Serie = votantes_Serie;
        this.NCapitulos = NCapitulos;
        this.notamedia_Serie = notamedia_Serie;
        this.votosusuarios = votosusuarios;
        this.comentarios = comentarios;
        this.votacion_media = votacion_media;
        this.actores_series = actores_series;
    }

    public String getCollection_Pelicula() {
        return collection_Pelicula;
    }

    public void setCollection_Pelicula(String collection_Pelicula) {
        this.collection_Pelicula = collection_Pelicula;
    }

    public String getCollection_Serie() {
        return collection_Serie;
    }

    public void setCollection_Serie(String collection_Serie) {
        this.collection_Serie = collection_Serie;
    }

    public String getID_Pelicula() {
        return ID_Pelicula;
    }

    public void setID_Pelicula(String ID_Pelicula) {
        this.ID_Pelicula = ID_Pelicula;
    }

    public String getTitulo_Pelicula() {
        return Titulo_Pelicula;
    }

    public void setTitulo_Pelicula(String titulo_Pelicula) {
        Titulo_Pelicula = titulo_Pelicula;
    }

    public String getDescripcion_Pelicula() {
        return Descripcion_Pelicula;
    }

    public void setDescripcion_Pelicula(String descripcion_Pelicula) {
        Descripcion_Pelicula = descripcion_Pelicula;
    }

    public String getProductora_Pelicula() {
        return Productora_Pelicula;
    }

    public void setProductora_Pelicula(String productora_Pelicula) {
        Productora_Pelicula = productora_Pelicula;
    }

    public String getDirector_Pelicula() {
        return Director_Pelicula;
    }

    public void setDirector_Pelicula(String director_Pelicula) {
        Director_Pelicula = director_Pelicula;
    }

    public String getFechaEstreno_Pelicula() {
        return FechaEstreno_Pelicula;
    }

    public void setFechaEstreno_Pelicula(String fechaEstreno_Pelicula) {
        FechaEstreno_Pelicula = fechaEstreno_Pelicula;
    }

    public String getTrailer_Pelicula() {
        return Trailer_Pelicula;
    }

    public void setTrailer_Pelicula(String trailer_Pelicula) {
        Trailer_Pelicula = trailer_Pelicula;
    }

    public String getDuración_Pelicula() {
        return Duración_Pelicula;
    }

    public void setDuración_Pelicula(String duración_Pelicula) {
        Duración_Pelicula = duración_Pelicula;
    }

    public String getID_Serie() {
        return ID_Serie;
    }

    public void setID_Serie(String ID_Serie) {
        this.ID_Serie = ID_Serie;
    }

    public String getTitulo_Serie() {
        return Titulo_Serie;
    }

    public void setTitulo_Serie(String titulo_Serie) {
        Titulo_Serie = titulo_Serie;
    }

    public String getDescripcion_Serie() {
        return Descripcion_Serie;
    }

    public void setDescripcion_Serie(String descripcion_Serie) {
        Descripcion_Serie = descripcion_Serie;
    }

    public String getProductora_Serie() {
        return Productora_Serie;
    }

    public void setProductora_Serie(String productora_Serie) {
        Productora_Serie = productora_Serie;
    }

    public String getDirector_Serie() {
        return Director_Serie;
    }

    public void setDirector_Serie(String director_Serie) {
        Director_Serie = director_Serie;
    }

    public String getPrimeraEmision_Serie() {
        return PrimeraEmision_Serie;
    }

    public void setPrimeraEmision_Serie(String primeraEmision_Serie) {
        PrimeraEmision_Serie = primeraEmision_Serie;
    }

    public String getTrailer_Serie() {
        return Trailer_Serie;
    }

    public void setTrailer_Serie(String trailer_Serie) {
        Trailer_Serie = trailer_Serie;
    }

    public String getDuración_Serie() {
        return Duración_Serie;
    }

    public void setDuración_Serie(String duración_Serie) {
        Duración_Serie = duración_Serie;
    }

    public ArrayList<String> getGenero_Pelicula() {
        return Genero_Pelicula;
    }

    public void setGenero_Pelicula(ArrayList<String> genero_Pelicula) {
        Genero_Pelicula = genero_Pelicula;
    }

    public ArrayList<String> getGenero_Serie() {
        return Genero_Serie;
    }

    public void setGenero_Serie(ArrayList<String> genero_Serie) {
        Genero_Serie = genero_Serie;
    }

    public String getImagen_Pelicula() {
        return Imagen_Pelicula;
    }

    public void setImagen_Pelicula(String imagen_Pelicula) {
        Imagen_Pelicula = imagen_Pelicula;
    }

    public String getImagen_Serie() {
        return Imagen_Serie;
    }

    public void setImagen_Serie(String imagen_Serie) {
        Imagen_Serie = imagen_Serie;
    }

    public int getVotosPositivos_Pelicula() {
        return votosPositivos_Pelicula;
    }

    public void setVotosPositivos_Pelicula(int votosPositivos_Pelicula) {
        this.votosPositivos_Pelicula = votosPositivos_Pelicula;
    }

    public int getVotosNegativos_Pelicula() {
        return votosNegativos_Pelicula;
    }

    public void setVotosNegativos_Pelicula(int votosNegativos_Pelicula) {
        this.votosNegativos_Pelicula = votosNegativos_Pelicula;
    }

    public int getVotantes_Pelicula() {
        return votantes_Pelicula;
    }

    public void setVotantes_Pelicula(int votantes_Pelicula) {
        this.votantes_Pelicula = votantes_Pelicula;
    }

    public int getVotosPositivos_Serie() {
        return votosPositivos_Serie;
    }

    public void setVotosPositivos_Serie(int votosPositivos_Serie) {
        this.votosPositivos_Serie = votosPositivos_Serie;
    }

    public int getVotosNegativos_Serie() {
        return votosNegativos_Serie;
    }

    public void setVotosNegativos_Serie(int votosNegativos_Serie) {
        this.votosNegativos_Serie = votosNegativos_Serie;
    }

    public int getVotantes_Serie() {
        return votantes_Serie;
    }

    public void setVotantes_Serie(int votantes_Serie) {
        this.votantes_Serie = votantes_Serie;
    }

    public int getNCapitulos() {
        return NCapitulos;
    }

    public void setNCapitulos(int NCapitulos) {
        this.NCapitulos = NCapitulos;
    }

    public double getNotamedia_Pelicula() {
        return notamedia_Pelicula;
    }

    public void setNotamedia_Pelicula(double notamedia_Pelicula) {
        this.notamedia_Pelicula = notamedia_Pelicula;
    }

    public double getNotamedia_Serie() {
        return notamedia_Serie;
    }

    public void setNotamedia_Serie(double notamedia_Serie) {
        this.notamedia_Serie = notamedia_Serie;
    }

    public ArrayList<Votos> getVotosusuarios() {
        return votosusuarios;
    }

    public void setVotosusuarios(ArrayList<Votos> votosusuarios) {
        this.votosusuarios = votosusuarios;
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public ArrayList<Votacion_media> getVotacion_media() {
        return votacion_media;
    }

    public void setVotacion_media(ArrayList<Votacion_media> votacion_media) {
        this.votacion_media = votacion_media;
    }

    public ArrayList<String> getActores_peliculas() {
        return actores_peliculas;
    }

    public void setActores_peliculas(ArrayList<String> actores_peliculas) {
        this.actores_peliculas = actores_peliculas;
    }

    public ArrayList<String> getActores_series() {
        return actores_series;
    }

    public void setActores_series(ArrayList<String> actores_series) {
        this.actores_series = actores_series;
    }
}
