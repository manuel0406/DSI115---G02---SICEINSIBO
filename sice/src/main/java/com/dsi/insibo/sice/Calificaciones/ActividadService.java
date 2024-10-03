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

    public List<Actividad> listaActividades(String dui, int idMateria, String periodo) {
        return (List<Actividad>) actividadRepository.actividadesPorEspecialidadPeriodo(dui, idMateria,
                periodo);
    }

    public List<Actividad> listaActividades(String dui, int idMateria) {
        return (List<Actividad>) actividadRepository.actividadesPorEspecialidad(dui, idMateria);
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