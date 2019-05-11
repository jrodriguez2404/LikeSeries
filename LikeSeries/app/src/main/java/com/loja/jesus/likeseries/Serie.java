package com.loja.jesus.likeseries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Serie {
    private String titulo_SER,descripcion_SER,productora_SER,comentarios,usuariocomentario;
    private ArrayList<String> Género_SER;
    private String nombreimagen;
    private int votosmas_SER,votosmenos_SER,notamedia,contadormedia;
    private ArrayList<HashMap<String,Object>> votosusuarios;
    public Serie() {
    }

    public Serie(String titulo_SER, String descripcion_SER, String productora_SER, String comentarios, String usuariocomentario, ArrayList<String> género_SER, String nombreimagen, int votosmas_SER, int votosmenos_SER, int notamedia, int contadormedia, ArrayList<HashMap<String, Object>> votosusuarios) {
        this.titulo_SER = titulo_SER;
        this.descripcion_SER = descripcion_SER;
        this.productora_SER = productora_SER;
        this.comentarios = comentarios;
        this.usuariocomentario = usuariocomentario;
        Género_SER = género_SER;
        this.nombreimagen = nombreimagen;
        this.votosmas_SER = votosmas_SER;
        this.votosmenos_SER = votosmenos_SER;
        this.notamedia = notamedia;
        this.contadormedia = contadormedia;
        this.votosusuarios = votosusuarios;
    }

    public String getTitulo_SER() {
        return titulo_SER;
    }

    public void setTitulo_SER(String titulo_SER) {
        this.titulo_SER = titulo_SER;
    }

    public String getDescripcion_SER() {
        return descripcion_SER;
    }

    public void setDescripcion_SER(String descripcion_SER) {
        this.descripcion_SER = descripcion_SER;
    }

    public String getProductora_SER() {
        return productora_SER;
    }

    public void setProductora_SER(String productora_SER) {
        this.productora_SER = productora_SER;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getUsuariocomentario() {
        return usuariocomentario;
    }

    public void setUsuariocomentario(String usuariocomentario) {
        this.usuariocomentario = usuariocomentario;
    }

    public ArrayList<String> getGénero_SER() {
        return Género_SER;
    }

    public void setGénero_SER(ArrayList<String> género_SER) {
        Género_SER = género_SER;
    }

    public String getNombreimagen() {
        return nombreimagen;
    }

    public void setNombreimagen(String nombreimagen) {
        this.nombreimagen = nombreimagen;
    }

    public int getVotosmas_SER() {
        return votosmas_SER;
    }

    public void setVotosmas_SER(int votosmas_SER) {
        this.votosmas_SER = votosmas_SER;
    }

    public int getVotosmenos_SER() {
        return votosmenos_SER;
    }

    public void setVotosmenos_SER(int votosmenos_SER) {
        this.votosmenos_SER = votosmenos_SER;
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
}
