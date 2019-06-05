package com.loja.jesus.likeseries;

public class Votacion_media {
    private String uid;
    private int nota,votado;
    public Votacion_media() {
    }

    public Votacion_media(String uid, int nota, int votado) {
        this.uid = uid;
        this.nota = nota;
        this.votado = votado;
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

    public int getVotado() {
        return votado;
    }

    public void setVotado(int votado) {
        this.votado = votado;
    }
}
