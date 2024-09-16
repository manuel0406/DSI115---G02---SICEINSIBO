package com.dsi.insibo.sice.Calificaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.entity.Actividad;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    public void guardarActividad(Actividad actividad) {
        actividadRepository.save(actividad);
    }

    public List<Actividad> listaActividades(String dui, int codigoBachillerato) {
        return (List<Actividad>) actividadRepository.actividadesPorEspecialidad(dui, codigoBachillerato);
    }

    public void eliminarActividad(int idActividad){
         actividadRepository.deleteById(idActividad);
    }
    public Actividad buscarActividadPorId(int idActividad){
        return actividadRepository.findById(idActividad).orElse(null);
    }
}