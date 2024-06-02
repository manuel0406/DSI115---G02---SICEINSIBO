package com.dsi.insibo.sice.entity;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
public class Alumno implements Serializable{
    
    @Id
    private int nie;
    
    private String nombreAlumno;
    private String apellidoAlumno;
    private char sexoAlumno;
    private Date fechaNacimientoAlumno;
    private String duiAlumno;
    private int telefonoAlumno;
    private String direccionAlumno;
    private String viveCon;
    private String medicamento;
    private String nombreEncargadoAlumno;
    private String formaMedicacion;
    private String telefonoEncargado;
    private String padecimientos;
    private String parentescoEncargado;
    private String lugarDeTrabajo;
    private String correoElectronico;
    private String apellidoEncargado;

    @ManyToOne
    private Bachillerato bachillerato;

    public void setBachillerato(Bachillerato bachillerato) {
        this.bachillerato = bachillerato;
    }
    public Bachillerato getBachillerato() {
        return bachillerato;
    }
    public int getNie() {
        return nie;
    }
    public void setNie(int nie) {
        this.nie = nie;
    }
   
    public String getNombreAlumno() {
        return nombreAlumno;
    }
    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }
    public String getApellidoAlumno() {
        return apellidoAlumno;
    }
    public void setApellidoAlumno(String apellidoAlumno) {
        this.apellidoAlumno = apellidoAlumno;
    }
    public char getSexoAlumno() {
        return sexoAlumno;
    }
    public void setSexoAlumno(char sexoAlumno) {
        this.sexoAlumno = sexoAlumno;
    }
   
    public String getDuiAlumno() {
        return duiAlumno;
    }
    public void setDuiAlumno(String duiAlumno) {
        this.duiAlumno = duiAlumno;
    }
    public int getTelefonoAlumno() {
        return telefonoAlumno;
    }
    public void setTelefonoAlumno(int telefonoAlumno) {
        this.telefonoAlumno = telefonoAlumno;
    }
    public String getDireccionAlumno() {
        return direccionAlumno;
    }
    public void setDireccionAlumno(String direccionAlumno) {
        this.direccionAlumno = direccionAlumno;
    }
    public String getViveCon() {
        return viveCon;
    }
    public void setViveCon(String viveCon) {
        this.viveCon = viveCon;
    }
    public String getMedicamento() {
        return medicamento;
    }
    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }
    public String getNombreEncargadoAlumno() {
        return nombreEncargadoAlumno;
    }
    public void setNombreEncargadoAlumno(String nombreEncargadoAlumno) {
        this.nombreEncargadoAlumno = nombreEncargadoAlumno;
    }
    public String getFormaMedicacion() {
        return formaMedicacion;
    }
    public void setFormaMedicacion(String formaMedicacion) {
        this.formaMedicacion = formaMedicacion;
    }
    public String getTelefonoEncargado() {
        return telefonoEncargado;
    }
    public void setTelefonoEncargado(String telefonoEncargado) {
        this.telefonoEncargado = telefonoEncargado;
    }
    public String getPadecimientos() {
        return padecimientos;
    }
    public void setPadecimientos(String padecimientos) {
        this.padecimientos = padecimientos;
    }
    public String getParentescoEncargado() {
        return parentescoEncargado;
    }
    public void setParentescoEncargado(String parentescoEncargado) {
        this.parentescoEncargado = parentescoEncargado;
    }
    public String getLugarDeTrabajo() {
        return lugarDeTrabajo;
    }
    public void setLugarDeTrabajo(String lugarDeTrabajo) {
        this.lugarDeTrabajo = lugarDeTrabajo;
    }
    public String getCorreoElectronico() {
        return correoElectronico;
    }
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    public String getApellidoEncargado() {
        return apellidoEncargado;
    }
    public void setApellidoEncargado(String apellidoEncargado) {
        this.apellidoEncargado = apellidoEncargado;
    }
    public Date getFechaNacimientoAlumno() {
        return fechaNacimientoAlumno;
    }
    public void setFechaNacimientoAlumno(Date fechaNacimientoAlumno) {
        this.fechaNacimientoAlumno = fechaNacimientoAlumno;
    }


   
   
   

    

}
