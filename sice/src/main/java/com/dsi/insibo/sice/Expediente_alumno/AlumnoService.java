package com.dsi.insibo.sice.Expediente_alumno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.entity.Alumno;


@Service
public class AlumnoService {
    
    @Autowired
    private AlumnoRepositoty alumnoRepository;

    public Alumno guardarAlumno(int nie, String nombre) {
       
        Alumno alumno = new Alumno();
        alumno.setNie(nie);
        alumno.setNombreAlumno(nombre);
        return alumnoRepository.save(alumno);
    }
}
