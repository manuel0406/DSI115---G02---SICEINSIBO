package com.dsi.insibo.sice.entity;

import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PersonalAdministrativo {
    
    @Id
    private String duiPersonal; 
    private String nombrePersonal;
    private String apellidoPersonal;
    private String telefonoPersonal;
    private String profesionPersonal;
    private Date fechaNacimientoP;
    private String gradoAcademicoP;
    private String correoPersonal;
    private String departamentoPersonal;
    private String municipioPersonal;
    private String distritoPersonal;
    private String direccionPersonal;
    private String zonaPersonal;
    private String nitPersonal;
    private String nupPersonal;
    private String telefonoFijoPersonal;
    private String especialidadEnEstudioP;
    private boolean curriculumPersonal;
    private boolean atestadosPersonal;
    private Date fechaEntregaPersonal;
    private Date fechaIngresoPersonal;
    
    public PersonalAdministrativo() {
    }

    @Override
    public String toString() {
        return "PersonalAdministrativo [duiPersonal=" + duiPersonal + ", nombrePersonal=" + nombrePersonal
                + ", apellidoPersonal=" + apellidoPersonal + ", telefonoPersonal=" + telefonoPersonal
                + ", profesionPersonal=" + profesionPersonal + ", fechaNacimientoP=" + fechaNacimientoP
                + ", gradoAcademicoP=" + gradoAcademicoP + ", correoPersonal=" + correoPersonal
                + ", departamentoPersonal=" + departamentoPersonal + ", municipioPersonal=" + municipioPersonal
                + ", distritoPersonal=" + distritoPersonal + ", direccionPersonal=" + direccionPersonal
                + ", zonaPersonal=" + zonaPersonal + ", nitPersonal=" + nitPersonal + ", nupPersonal=" + nupPersonal
                + ", telefonoFijoPersonal=" + telefonoFijoPersonal + ", especialidadEnEstudioP="
                + especialidadEnEstudioP + ", curriculumPersonal=" + curriculumPersonal + ", atestadosPersonal="
                + atestadosPersonal + ", fechaEntregaPersonal=" + fechaEntregaPersonal + ", fechaIngresoPersonal="
                + fechaIngresoPersonal + "]";
    }

    public PersonalAdministrativo(String duiPersonal, String nombrePersonal, String apellidoPersonal,
            String telefonoPersonal, String profesionPersonal, Date fechaNacimientoP, String gradoAcademicoP,
            String correoPersonal, String departamentoPersonal, String municipioPersonal, String distritoPersonal,
            String direccionPersonal, String zonaPersonal, String nitPersonal, String nupPersonal,
            String telefonoFijoPersonal, String especialidadEnEstudioP, boolean curriculumPersonal,
            boolean atestadosPersonal, Date fechaEntregaPersonal, Date fechaIngresoPersonal) {
        this.duiPersonal = duiPersonal;
        this.nombrePersonal = nombrePersonal;
        this.apellidoPersonal = apellidoPersonal;
        this.telefonoPersonal = telefonoPersonal;
        this.profesionPersonal = profesionPersonal;
        this.fechaNacimientoP = fechaNacimientoP;
        this.gradoAcademicoP = gradoAcademicoP;
        this.correoPersonal = correoPersonal;
        this.departamentoPersonal = departamentoPersonal;
        this.municipioPersonal = municipioPersonal;
        this.distritoPersonal = distritoPersonal;
        this.direccionPersonal = direccionPersonal;
        this.zonaPersonal = zonaPersonal;
        this.nitPersonal = nitPersonal;
        this.nupPersonal = nupPersonal;
        this.telefonoFijoPersonal = telefonoFijoPersonal;
        this.especialidadEnEstudioP = especialidadEnEstudioP;
        this.curriculumPersonal = curriculumPersonal;
        this.atestadosPersonal = atestadosPersonal;
        this.fechaEntregaPersonal = fechaEntregaPersonal;
        this.fechaIngresoPersonal = fechaIngresoPersonal;
    }

    public String getNupPersonal() {
        return nupPersonal;
    }
    public void setNupPersonal(String nupPersonal) {
        this.nupPersonal = nupPersonal;
    }
    public String getTelefonoFijoPersonal() {
        return telefonoFijoPersonal;
    }
    public void setTelefonoFijoPersonal(String telefonoFijoPersonal) {
        this.telefonoFijoPersonal = telefonoFijoPersonal;
    }
    public String getDuiPersonal() {
        return duiPersonal;
    }
    public void setDuiPersonal(String duiPersonal) {
        this.duiPersonal = duiPersonal;
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
    public Date getFechaNacimientoP() {
        return fechaNacimientoP;
    }
    public void setFechaNacimientoP(Date fechaNacimientoP) {
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
