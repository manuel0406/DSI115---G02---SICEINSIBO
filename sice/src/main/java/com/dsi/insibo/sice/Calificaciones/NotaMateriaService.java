package com.dsi.insibo.sice.Calificaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.entity.NotaMateria;

@Service
public class NotaMateriaService {

    @Autowired
    private NotaMateriaRepository notaMateriaRepository;

    public void guardarNotaMateria(NotaMateria notaMateria) {
        notaMateriaRepository.save(notaMateria);
    }

    public List<NotaMateria> listadoNotaMateria(){
        return notaMateriaRepository.findAll();
    }

    public NotaMateria notaMateriaId(int idNotaMateria){
        return notaMateriaRepository.findById(idNotaMateria).orElse(null);
    }

    public void eliminarNotaMateria(int idNotaMateria){
        notaMateriaRepository.deleteById(idNotaMateria);
    }

    public NotaMateria existeNotaMateria(int idAlumno, int idMateria, String duiDocente, int codigoBachillerato){
        return notaMateriaRepository.notaMateriaAlumno(idAlumno, idMateria, duiDocente, codigoBachillerato).orElse(null);
    }

    
}
