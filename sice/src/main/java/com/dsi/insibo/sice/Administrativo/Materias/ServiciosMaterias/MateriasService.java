package com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.Administrativo.Materias.RespositoriosMaterias.MateriasRepository;
import com.dsi.insibo.sice.entity.Materia;

@Service
public class MateriasService {
    
    @Autowired
    private MateriasRepository materiasRepository;
    
    // MATERIAS DISPONIBLES
    public List<Materia> obtenerMaterias(){
        return (List<Materia>) materiasRepository.obtenerMaterias();
    }

    // GUARDAR MATERIAS
    public void guardarMateria (Materia materia){
        materiasRepository.save(materia);
    }

    // OBTENER MATERIAS POR ID
    public Materia obtenerMateriaPorId(int idMateria) {
        return materiasRepository.findById(idMateria).orElse(null);
    }

    // ELIMINAR MATERIA
    public void eliminarMateria(Materia materia) {
        materiasRepository.delete(materia);
    }
}
