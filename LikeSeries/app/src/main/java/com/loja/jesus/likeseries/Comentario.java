package com.loja.jesus.likeseries;

import java.util.ArrayList;
import java.util.HashMap;

public class Comentario {
    private String nombre,usuario,comentario;

    public Comentario() {
    }

    public Comentario(String nombre, String usuario, String comentario) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.comentario = comentario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
