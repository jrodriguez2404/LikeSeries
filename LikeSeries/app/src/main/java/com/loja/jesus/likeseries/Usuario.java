package com.loja.jesus.likeseries;

import android.content.Intent;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;

public class Usuario {
    private String UID,Nombre,Email;
    private Boolean Recibir;
    private int votosPositivos,votosNegativos;
    public Usuario() {
    }

    public Usuario(String UID, String nombre, String email, Boolean recibir) {
        this.UID = UID;
        Nombre = nombre;
        Email = email;
        Recibir = recibir;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Boolean getRecibir() {
        return Recibir;
    }

    public void setRecibir(Boolean recibir) {
        Recibir = recibir;
    }

}
