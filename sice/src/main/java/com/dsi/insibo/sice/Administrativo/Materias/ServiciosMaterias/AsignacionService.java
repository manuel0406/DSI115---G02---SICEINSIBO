package com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.Administrativo.Materias.RespositoriosMaterias.AsignacionRepository;
import com.dsi.insibo.sice.entity.Asignacion;

@Service
public class AsignacionService {
    
    @Autowired
    private AsignacionRepository asignacionRepository;

    public void saveAsignacion(Asignacion asignacion) {
        asignacionRepository.save(asignacion);
    }

    public void saveAsignaciones(List<Asignacion> asignaciones) {
        asignacionRepository.saveAll(asignaciones);
    }

    public List<Asignacion> obtenerAsignacionExistente(int idMateria){
        return asignacionRepository.obtenerAsignacionExistente(idMateria);
    }

    public List<String> listarDocentesMaximo(int idMateria){
        return asignacionRepository.findDocentesWithThreeDistinctMaterias(idMateria);
    }

    public List<Asignacion> buscarDocenteMateria(int idMateria, String duiDocente){
        return asignacionRepository.findByDocenteAndMateria(duiDocente, idMateria);
    }

    public List<Asignacion> listarAsignaciones(int idMateria){
        return asignacionRepository.findByMateria(idMateria);
    }

    public Asignacion buscarAsignacion(int idAsignacion){
        return asignacionRepository.findById(idAsignacion).orElse(null);
    }

    public void eliminarAsignacion(Asignacion asignacion){
        asignacionRepository.delete(asignacion);
    }

    public List<Asignacion> listarAsignacionesCodigoBachillerato(String codigoBachillerato){
        return asignacionRepository.findByCodigoBachillerato(codigoBachillerato);
    }
}
