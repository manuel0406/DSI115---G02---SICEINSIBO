package com.dsi.insibo.sice.Horario.PDF;

public class HorarioEditarDTO {
    private String nomMateria, nombreDocente, apellidoDocente, nombreDia, horaFinalizacion, horaInicio;
    private int idHorarioBase, idAsignacion, idHora, idAsignacionHorario;

    public int getIdHorarioBase() {
        return idHorarioBase;
    }

    public void setIdHorarioBase(int idHorarioBase) {
        this.idHorarioBase = idHorarioBase;
    }

    public String getNomMateria() {
        return nomMateria;
    }

    public void setNomMateria(String nomMateria) {
        this.nomMateria = nomMateria;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getApellidoDocente() {
        return apellidoDocente;
    }

    public void setApellidoDocente(String apellidoDocente) {
        this.apellidoDocente = apellidoDocente;
    }

    public String getNombreDia() {
        return nombreDia;
    }

    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }

    public String getHoraFinalizacion() {
        return horaFinalizacion;
    }

    public void setHoraFinalizacion(String horaFinalizacion) {
        this.horaFinalizacion = horaFinalizacion;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public int getIdHora() {
        return idHora;
    }

    public void setIdHora(int idHora) {
        this.idHora = idHora;
    }

    public int getIdAsignacionHorario() {
        return idAsignacionHorario;
    }

    public void setIdAsignacionHorario(int idAsignacionHorario) {
        this.idAsignacionHorario = idAsignacionHorario;
    }

}
