package com.dsi.insibo.sice.entity;

import java.util.Date;

import jakarta.persistence.*;


@Entity
public class AnexoAlumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAnexoAlumno;
    private String nombreArchivo;
    @Lob
    private byte[] datos;
    private Date uploDate;
    
    public AnexoAlumno() {
    }

    public AnexoAlumno(int idAnexoAlumno, String nombreArchivo, byte[] datos, Date uploDate) {
        this.idAnexoAlumno = idAnexoAlumno;
        this.nombreArchivo = nombreArchivo;
        this.datos = datos;
        this.uploDate = uploDate;
    }

    public int getIdAnexoAlumno() {
        return idAnexoAlumno;
    }

    public void setIdAnexoAlumno(int idAnexoAlumno) {
        this.idAnexoAlumno = idAnexoAlumno;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public byte[] getDatos() {
        return datos;
    }

    public void setDatos(byte[] datos) {
        this.datos = datos;
    }

    public Date getUploDate() {
        return uploDate;
    }

    public void setUploDate(Date uploDate) {
        this.uploDate = uploDate;
    }

    

    
}
