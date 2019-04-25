package com.loja.jesus.likeseries;



import com.google.type.Date;

import java.util.ArrayList;
import java.util.Arrays;

public class Pelicula {
    private String Titulo_PEL_NA,Descripcion_PEL_NA,Productora_PEL_NA;
    private Date Duración_PEL_NA;
    private ArrayList<String> Género_PEL_NA;
    private String nombreimagen;
    private int Votos_PEL_NA;

    public Pelicula() {
    }

    public Pelicula(String titulo_PEL_NA, String descripcion_PEL_NA, String productora_PEL_NA, ArrayList<String> género_PEL_NA, String nombreimagen, Integer votos_PEL_NA) {
        Titulo_PEL_NA = titulo_PEL_NA;
        Descripcion_PEL_NA = descripcion_PEL_NA;
        Productora_PEL_NA = productora_PEL_NA;
        Género_PEL_NA = género_PEL_NA;
        this.nombreimagen = nombreimagen;
        Votos_PEL_NA = votos_PEL_NA;
    }

    public String getTitulo_PEL_NA() {
        return Titulo_PEL_NA;
    }

    public void setTitulo_PEL_NA(String titulo_PEL_NA) {
        Titulo_PEL_NA = titulo_PEL_NA;
    }

    public String getDescripcion_PEL_NA() {
        return Descripcion_PEL_NA;
    }

    public void setDescripcion_PEL_NA(String descripcion_PEL_NA) {
        Descripcion_PEL_NA = descripcion_PEL_NA;
    }

    public String getProductora_PEL_NA() {
        return Productora_PEL_NA;
    }

    public void setProductora_PEL_NA(String productora_PEL_NA) {
        Productora_PEL_NA = productora_PEL_NA;
    }

    public ArrayList<String> getGénero_PEL_NA() {
        return Género_PEL_NA;
    }

    public void setGénero_PEL_NA(ArrayList<String> género_PEL_NA) {
        Género_PEL_NA = género_PEL_NA;
    }

    public String getNombreimagen() {
        return nombreimagen;
    }

    public void setNombreimagen(String nombreimagen) {
        this.nombreimagen = nombreimagen;
    }

    public int getVotos_PEL_NA() {
        return Votos_PEL_NA;
    }

    public void setVotos_PEL_NA(int votos_PEL_NA) {
        Votos_PEL_NA = votos_PEL_NA;
    }
}
