package com.loja.jesus.likeseries;

public class Votacion_media {
    private String uid;
    private int nota;
    public Votacion_media() {
    }

    public Votacion_media(String uid, int nota) {
        this.uid = uid;
        this.nota = nota;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

}
