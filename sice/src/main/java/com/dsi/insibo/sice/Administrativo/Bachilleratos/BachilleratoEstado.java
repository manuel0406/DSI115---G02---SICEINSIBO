package com.dsi.insibo.sice.Administrativo.Bachilleratos;

public class BachilleratoEstado {
    
    private String nombreCarrera;
    private String seccion;
    private int grado;
    private boolean seleccionado;
    
    public BachilleratoEstado() {
    }
    public BachilleratoEstado(String nombreCarrera, String seccion, int grado) {
        this.nombreCarrera = nombreCarrera;
        this.seccion = seccion;
        this.grado = grado;
    }
    public String getNombreCarrera() {
        return nombreCarrera; 
    }
    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }
    public String getSeccion() {
        return seccion;
    }
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
    public int getGrado() {
        return grado;
    }
    public void setGrado(int grado) {
        this.grado = grado;
    }
    public boolean isSeleccionado() {
        return seleccionado;
    }
    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    
    
}
