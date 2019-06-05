package com.loja.jesus.likeseries;

public class ChatGeneral {
    private String nombre,uid,mensaje,nombredocumento;

    public ChatGeneral() {
    }

    public ChatGeneral(String nombre, String uid, String mensaje, String nombredocumento) {
        this.nombre = nombre;
        this.uid = uid;
        this.mensaje = mensaje;
        this.nombredocumento = nombredocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombredocumento() {
        return nombredocumento;
    }

    public void setNombredocumento(String nombredocumento) {
        this.nombredocumento = nombredocumento;
    }
}
