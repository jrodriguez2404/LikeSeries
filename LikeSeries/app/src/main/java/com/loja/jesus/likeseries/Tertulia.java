package com.loja.jesus.likeseries;

import java.util.ArrayList;

public class Tertulia {
private String nombretertulia,horainicio,horafin;
private int activado;
private ArrayList<Chat>chattertulia=new ArrayList<>();

    public Tertulia() {
    }

    public Tertulia(String nombretertulia, String horainicio, String horafin, int activado, ArrayList<Chat> chattertulia) {
        this.nombretertulia = nombretertulia;
        this.horainicio = horainicio;
        this.horafin = horafin;
        this.activado = activado;
        this.chattertulia = chattertulia;
    }

    public String getNombretertulia() {
        return nombretertulia;
    }

    public void setNombretertulia(String nombretertulia) {
        this.nombretertulia = nombretertulia;
    }

    public String getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(String horainicio) {
        this.horainicio = horainicio;
    }

    public String getHorafin() {
        return horafin;
    }

    public void setHorafin(String horafin) {
        this.horafin = horafin;
    }

    public int getActivado() {
        return activado;
    }

    public void setActivado(int activado) {
        this.activado = activado;
    }

    public ArrayList<Chat> getChattertulia() {
        return chattertulia;
    }

    public void setChattertulia(ArrayList<Chat> chattertulia) {
        this.chattertulia = chattertulia;
    }
}
