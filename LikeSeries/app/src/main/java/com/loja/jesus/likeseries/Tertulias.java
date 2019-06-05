package com.loja.jesus.likeseries;

import java.util.ArrayList;

public class Tertulias {
    private ArrayList<Tertulia> tertulia = new ArrayList<>();
    public Tertulias() {
    }

    public Tertulias(ArrayList<Tertulia> tertulia) {
        this.tertulia = tertulia;
    }

    public ArrayList<Tertulia> getTertulia() {
        return tertulia;
    }

    public void setTertulia(ArrayList<Tertulia> tertulia) {
        this.tertulia = tertulia;
    }
}
