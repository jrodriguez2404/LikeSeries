package com.loja.jesus.likeseries;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pelicula {
    private String titulo_PEL,descripcion_PEL,productora_PEL;
    private ArrayList<String> Género_PEL;
    private String nombreimagen;
    private int votosmas_PEL,votosmenos_PEL,notamedia,contadormedia;
    private ArrayList<HashMap<String,Object>> votosusuarios;
    private ArrayList<HashMap<String,Object>> comentarios;
    public Pelicula() {
    }

    public Pelicula(String titulo_PEL, String descripcion_PEL, String productora_PEL, ArrayList<String> género_PEL, String nombreimagen, int votosmas_PEL, int votosmenos_PEL, int notamedia, int contadormedia, ArrayList<HashMap<String, Object>> votosusuarios, ArrayList<HashMap<String, Object>> comentarios) {
        this.titulo_PEL = titulo_PEL;
        this.descripcion_PEL = descripcion_PEL;
        this.productora_PEL = productora_PEL;
        Género_PEL = género_PEL;
        this.nombreimagen = nombreimagen;
        this.votosmas_PEL = votosmas_PEL;
        this.votosmenos_PEL = votosmenos_PEL;
        this.notamedia = notamedia;
        this.contadormedia = contadormedia;
        this.votosusuarios = votosusuarios;
        this.comentarios = comentarios;
    }

    public String getTitulo_PEL() {
        return titulo_PEL;
    }

    public void setTitulo_PEL(String titulo_PEL) {
        this.titulo_PEL = titulo_PEL;
    }

    public String getDescripcion_PEL() {
        return descripcion_PEL;
    }

    public void setDescripcion_PEL(String descripcion_PEL) {
        this.descripcion_PEL = descripcion_PEL;
    }

    public String getProductora_PEL() {
        return productora_PEL;
    }

    public void setProductora_PEL(String productora_PEL) {
        this.productora_PEL = productora_PEL;
    }

    public ArrayList<String> getGénero_PEL() {
        return Género_PEL;
    }

    public void setGénero_PEL(ArrayList<String> género_PEL) {
        Género_PEL = género_PEL;
    }

    public String getNombreimagen() {
        return nombreimagen;
    }

    public void setNombreimagen(String nombreimagen) {
        this.nombreimagen = nombreimagen;
    }

    public int getVotosmas_PEL() {
        return votosmas_PEL;
    }

    public void setVotosmas_PEL(int votosmas_PEL) {
        this.votosmas_PEL = votosmas_PEL;
    }

    public int getVotosmenos_PEL() {
        return votosmenos_PEL;
    }

    public void setVotosmenos_PEL(int votosmenos_PEL) {
        this.votosmenos_PEL = votosmenos_PEL;
    }

    public int getNotamedia() {
        return notamedia;
    }

    public void setNotamedia(int notamedia) {
        this.notamedia = notamedia;
    }

    public int getContadormedia() {
        return contadormedia;
    }

    public void setContadormedia(int contadormedia) {
        this.contadormedia = contadormedia;
    }

    public ArrayList<HashMap<String, Object>> getVotosusuarios() {
        return votosusuarios;
    }

    public void setVotosusuarios(ArrayList<HashMap<String, Object>> votosusuarios) {
        this.votosusuarios = votosusuarios;
    }

    public ArrayList<HashMap<String, Object>> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<HashMap<String, Object>> comentarios) {
        this.comentarios = comentarios;
    }
}
