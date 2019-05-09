package com.loja.jesus.likeseries;

import android.content.Intent;
import android.provider.ContactsContract;

public class Usuario {
    private String nombre,email,uid;
    private Boolean mensajes;
    private Integer num_votospositivos,num_votosnegativos;
    public Usuario() {
    }

    public Usuario(String nombre, String email, String uid, Boolean mensajes,Integer num_votospositivos,Integer num_votosnegativos) {
        this.nombre = nombre;
        this.email = email;
        this.uid = uid;
        this.mensajes = mensajes;
        this.num_votospositivos = num_votospositivos;
        this.num_votosnegativos = num_votosnegativos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return uid;
    }

    public void setToken(String token) {
        this.uid = uid;
    }

    public Boolean getMensajes() {
        return mensajes;
    }

    public void setMensajes(Boolean mensajes) {
        this.mensajes = mensajes;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getNum_votospositivos() {
        return num_votospositivos;
    }

    public void setNum_votospositivos(Integer num_votospositivos) {
        this.num_votospositivos = num_votospositivos;
    }

    public Integer getNum_votosnegativos() {
        return num_votosnegativos;
    }

    public void setNum_votosnegativos(Integer num_votosnegativos) {
        this.num_votosnegativos = num_votosnegativos;
    }
}
