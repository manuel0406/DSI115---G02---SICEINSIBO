package com.dsi.insibo.sice.entity;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PersonalAdministrativo {
    
    @Id
    private String duiPersonal;

    @ManyToOne
    
    private Usuario usuario;

    private String nombrePersonal;
    private String apellidoPersonal;
    private String telefonoPersonal;
    private String profesionPersonal;
    private LocalDate fechaNacimientoP;
    private String gradoAcademicoP;
    private String correoPersonal;
    private String departamentoPersonal;
    private String municipioPersonal;
    private String distritoPersonal;
    private String direccionPersonal;
    private String zonaPersonal;
    private String nitPersonal;
    private String especialidadEnEstudioP;
    private boolean curriculumPersonal;
    private boolean atestadosPersonal;
    private Date fechaEntregaPersonal;
    private Date fechaIngresoPersonal;
    
    public String getDuiPersonal() {
        return duiPersonal;
    }
    public void setDuiPersonal(String duiPersonal) {
        this.duiPersonal = duiPersonal;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public String getNombrePersonal() {
        return nombrePersonal;
    }
    public void setNombrePersonal(String nombrePersonal) {
        this.nombrePersonal = nombrePersonal;
    }
    public String getApellidoPersonal() {
        return apellidoPersonal;
    }
    public void setApellidoPersonal(String apellidoPersonal) {
        this.apellidoPersonal = apellidoPersonal;
    }
    public String getTelefonoPersonal() {
        return telefonoPersonal;
    }
    public void setTelefonoPersonal(String telefonoPersonal) {
        this.telefonoPersonal = telefonoPersonal;
    }
    public String getProfesionPersonal() {
        return profesionPersonal;
    }
    public void setProfesionPersonal(String profesionPersonal) {
        this.profesionPersonal = profesionPersonal;
    }
    public LocalDate getFechaNacimientoP() {
        return fechaNacimientoP;
    }
    public void setFechaNacimientoP(LocalDate fechaNacimientoP) {
        this.fechaNacimientoP = fechaNacimientoP;
    }
    public String getGradoAcademicoP() {
        return gradoAcademicoP;
    }
    public void setGradoAcademicoP(String gradoAcademicoP) {
        this.gradoAcademicoP = gradoAcademicoP;
    }
    public String getCorreoPersonal() {
        return correoPersonal;
    }
    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }
    public String getDepartamentoPersonal() {
        return departamentoPersonal;
    }
    public void setDepartamentoPersonal(String departamentoPersonal) {
        this.departamentoPersonal = departamentoPersonal;
    }
    public String getMunicipioPersonal() {
        return municipioPersonal;
    }
    public void setMunicipioPersonal(String municipioPersonal) {
        this.municipioPersonal = municipioPersonal;
    }
    public String getDistritoPersonal() {
        return distritoPersonal;
    }
    public void setDistritoPersonal(String distritoPersonal) {
        this.distritoPersonal = distritoPersonal;
    }
    public String getDireccionPersonal() {
        return direccionPersonal;
    }
    public void setDireccionPersonal(String direccionPersonal) {
        this.direccionPersonal = direccionPersonal;
    }
    public String getZonaPersonal() {
        return zonaPersonal;
    }
    public void setZonaPersonal(String zonaPersonal) {
        this.zonaPersonal = zonaPersonal;
    }
    public String getNitPersonal() {
        return nitPersonal;
    }
    public void setNitPersonal(String nitPersonal) {
        this.nitPersonal = nitPersonal;
    }
    public String getEspecialidadEnEstudioP() {
        return especialidadEnEstudioP;
    }
    public void setEspecialidadEnEstudioP(String especialidadEnEstudioP) {
        this.especialidadEnEstudioP = especialidadEnEstudioP;
    }
    public boolean isCurriculumPersonal() {
        return curriculumPersonal;
    }
    public void setCurriculumPersonal(boolean curriculumPersonal) {
        this.curriculumPersonal = curriculumPersonal;
    }
    public boolean isAtestadosPersonal() {
        return atestadosPersonal;
    }
    public void setAtestadosPersonal(boolean atestadosPersonal) {
        this.atestadosPersonal = atestadosPersonal;
    }
    public Date getFechaEntregaPersonal() {
        return fechaEntregaPersonal;
    }
    public void setFechaEntregaPersonal(Date fechaEntregaPersonal) {
        this.fechaEntregaPersonal = fechaEntregaPersonal;
    }
    public Date getFechaIngresoPersonal() {
        return fechaIngresoPersonal;
    }
    public void setFechaIngresoPersonal(Date fechaIngresoPersonal) {
        this.fechaIngresoPersonal = fechaIngresoPersonal;
    }

    
    
}
