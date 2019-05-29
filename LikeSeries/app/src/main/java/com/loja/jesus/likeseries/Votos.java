package com.loja.jesus.likeseries;

public class Votos {
    private String reglas,usuariovoto;

    public Votos() {
    }

    public Votos( String usuariovoto,String reglas) {
        this.reglas = reglas;
        this.usuariovoto = usuariovoto;
    }

    public String getReglas() {
        return reglas;
    }

    public void setReglas(String reglas) {
        this.reglas = reglas;
    }

    public String getUsuariovoto() {
        return usuariovoto;
    }

    public void setUsuariovoto(String usuariovoto) {
        this.usuariovoto = usuariovoto;
    }
}
