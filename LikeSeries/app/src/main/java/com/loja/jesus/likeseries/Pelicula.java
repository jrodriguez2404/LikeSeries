package com.loja.jesus.likeseries;


import java.util.Map;

public class Pelicula {
    private Integer votos;
    private Map comentarios;

    public Pelicula(Integer votos, Map comentarios) {
        this.votos = votos;
        this.comentarios = comentarios;
    }

    public Integer getVotos() {
        return votos;
    }

    public void setVotos(Integer votos) {
        this.votos = votos;
    }

    public Map getComentarios() {
        return comentarios;
    }

    public void setComentarios(Map comentarios) {
        this.comentarios = comentarios;
    }
}
