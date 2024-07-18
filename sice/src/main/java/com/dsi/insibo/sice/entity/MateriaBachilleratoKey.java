package com.dsi.insibo.sice.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;



@Embeddable
public class MateriaBachilleratoKey implements Serializable {

    private String codigoBachillerato;
    private int codMateria;

    public String getCodigoBachillerato() {
        return codigoBachillerato;
    }
    public void setCodigoBachillerato(String codigoBachillerato) {
        this.codigoBachillerato = codigoBachillerato;
    }
    public int getCodMateria() {
        return codMateria;
    }
    public void setCodMateria(int codMateria) {
        this.codMateria = codMateria;
    }

    // Getters, Setters, hashCode y equals
}

