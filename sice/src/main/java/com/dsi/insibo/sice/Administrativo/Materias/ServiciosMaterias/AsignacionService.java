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

    public List<String> listarDocentesMaximo(){
        return asignacionRepository.findDocentesWithThreeDistinctMaterias();
    }

    public List<Asignacion> buscarDocenteMateria(int idMateria, String duiDocente){
        return asignacionRepository.findByDocenteAndMateria(duiDocente, idMateria);
    }

    public List<Asignacion> listarAsignaciones(int idMateria){
        return asignacionRepository.findByMateria(idMateria);
    }

}
