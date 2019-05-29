package com.loja.jesus.likeseries;

import android.content.Intent;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;

public class Usuario {
    private String UID,Nombre,Email;
    private Boolean Administrador;
    private int votosPositivos,votosNegativos;
    public Usuario() {
    }

    public Usuario(String UID, String nombre, String email, Boolean Administrador,int votosPositivos,int votosNegativos) {
        this.UID = UID;
        Nombre = nombre;
        Email = email;
        this.Administrador = Administrador;
        this.votosPositivos=votosPositivos;
        this.votosNegativos=votosNegativos;
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

    public Boolean getAdministrador() {
        return Administrador;
    }

    public void setAdministrador(Boolean Administrador) {
        Administrador = Administrador;
    }

    public int getVotosPositivos() {
        return votosPositivos;
    }

    public void setVotosPositivos(int votosPositivos) {
        this.votosPositivos = votosPositivos;
    }

    public int getVotosNegativos() {
        return votosNegativos;
    }

    public void setVotosNegativos(int votosNegativos) {
        this.votosNegativos = votosNegativos;
    }
}
