package com.loja.jesus.likeseries;

import android.content.Intent;
import android.provider.ContactsContract;

public class Usuario {
    private String nombre,email,uid;
    private Boolean mensajes;
    private Integer num_votos;
    public Usuario() {
    }

    public Usuario(String nombre, String email, String uid, Boolean mensajes,Integer num_votos) {
        this.nombre = nombre;
        this.email = email;
        this.uid = uid;
        this.mensajes = mensajes;
        this.num_votos = num_votos;
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

    public Integer getNum_votos() {
        return num_votos;
    }

    public void setNum_votos(Integer num_votos) {
        this.num_votos = num_votos;
    }
}
