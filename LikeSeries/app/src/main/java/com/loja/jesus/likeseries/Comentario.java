package com.loja.jesus.likeseries;

import java.util.ArrayList;
import java.util.HashMap;

public class Comentario {
    private String nombreusuario,uidusuario,comentario;

    public Comentario() {
    }

    public Comentario(String nombreusuario,String uidusuario, String comentario) {
        this.nombreusuario = nombreusuario;
        this.uidusuario = uidusuario;
        this.comentario = comentario;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getUidusuario() {
        return uidusuario;
    }

    public void setUidusuario(String uidusuario) {
        this.uidusuario = uidusuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
