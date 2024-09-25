package com.dsi.insibo.sice.Calificaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.entity.Actividad;

import jakarta.transaction.Transactional;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private NotaRepository notaRepository;

    public void guardarActividad(Actividad actividad) {
        actividadRepository.save(actividad);
    }

    public List<Actividad> listaActividades(String dui, int codigoBachillerato, String periodo) {
        return (List<Actividad>) actividadRepository.actividadesPorEspecialidadPeriodo(dui, codigoBachillerato,
                periodo);
    }

    public List<Actividad> listaActividades(String dui, int codigoBachillerato) {
        return (List<Actividad>) actividadRepository.actividadesPorEspecialidad(dui, codigoBachillerato);
    }

    @Transactional
    public void eliminarActividad(int idActividad) {

        notaRepository.deleteByActividad(idActividad);// se elimina las notas relacionadas a esa actividad
        actividadRepository.deleteById(idActividad);
    }

    public Actividad buscarActividadPorId(int idActividad) {
        return actividadRepository.findById(idActividad).orElse(null);
    }
}