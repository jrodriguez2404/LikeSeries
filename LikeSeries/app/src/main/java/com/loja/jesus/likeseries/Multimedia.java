package com.loja.jesus.likeseries;

import java.util.ArrayList;
import java.util.HashMap;

public class Multimedia {
    //Esta clase sirve para elegir los votos positivos y negativos de las series y peliculas
    private String collection_Pelicula,collection_Serie,ID_Pelicula,Titulo_Pelicula,Descripcion_Pelicula,Productora_Pelicula,ID_Serie,Titulo_Serie,Descripcion_Serie,Productora_Serie;
    private ArrayList<String> Genero_Pelicula,Genero_Serie;
    private String Imagen_Pelicula,Imagen_Serie;
    private int votosPositivos_Pelicula,votosNegativos_Pelicula,votantes_Pelicula,votosPositivos_Serie,votosNegativos_Serie,votantes_Serie,NCapitulos;
    private long notamedia_Pelicula,notamedia_Serie;
    private ArrayList<Votos> votosusuarios;
    private ArrayList<Comentario> comentarios;

    public Multimedia() {
    }

    public Multimedia(String collection_Pelicula,String ID_Pelicula, String titulo_Pelicula, String descripcion_Pelicula, String productora_Pelicula, ArrayList<String> genero_Pelicula, String imagen_Pelicula, int votosPositivos_Pelicula, int votosNegativos_Pelicula, int votantes_Pelicula, long notamedia_Pelicula, ArrayList<Votos> votosusuarios, ArrayList<Comentario> comentarios) {
        this.collection_Pelicula=collection_Pelicula;
        this.ID_Pelicula = ID_Pelicula;
        Titulo_Pelicula = titulo_Pelicula;
        Descripcion_Pelicula = descripcion_Pelicula;
        Productora_Pelicula = productora_Pelicula;
        Genero_Pelicula = genero_Pelicula;
        Imagen_Pelicula = imagen_Pelicula;
        this.votosPositivos_Pelicula = votosPositivos_Pelicula;
        this.votosNegativos_Pelicula = votosNegativos_Pelicula;
        this.votantes_Pelicula = votantes_Pelicula;
        this.notamedia_Pelicula = notamedia_Pelicula;
        this.votosusuarios = votosusuarios;
        this.comentarios = comentarios;
    }

    public Multimedia(String collection_Serie,String ID_Serie, String titulo_Serie, String descripcion_Serie, String productora_Serie, ArrayList<String> genero_Serie, String imagen_Serie, int votosPositivos_Serie, int votosNegativos_Serie, int votantes_Serie, int NCapitulos, long notamedia_Serie, ArrayList<Votos> votosusuarios, ArrayList<Comentario> comentarios) {
        this.collection_Serie=collection_Serie;
        this.ID_Serie = ID_Serie;
        Titulo_Serie = titulo_Serie;
        Descripcion_Serie = descripcion_Serie;
        Productora_Serie = productora_Serie;
        Genero_Serie = genero_Serie;
        Imagen_Serie = imagen_Serie;
        this.votosPositivos_Serie = votosPositivos_Serie;
        this.votosNegativos_Serie = votosNegativos_Serie;
        this.votantes_Serie = votantes_Serie;
        this.NCapitulos = NCapitulos;
        this.notamedia_Serie = notamedia_Serie;
        this.votosusuarios = votosusuarios;
        this.comentarios = comentarios;
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

    public long getNotamedia_Pelicula() {
        return notamedia_Pelicula;
    }

    public void setNotamedia_Pelicula(long notamedia_Pelicula) {
        this.notamedia_Pelicula = notamedia_Pelicula;
    }

    public long getNotamedia_Serie() {
        return notamedia_Serie;
    }

    public void setNotamedia_Serie(long notamedia_Serie) {
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
}
