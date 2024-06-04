package com.dsi.insibo.sice.Expediente_alumno;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.entity.Alumno;


@Service
public class AlumnoService {
    
    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<Alumno> filtrarAlumnos(String carrera, Integer grado, String seccion) {
        return alumnoRepository.filtrarAlumnos(carrera, grado, seccion);
    }

    public List<Alumno> listarAlumnos(){
        return (List<Alumno>)alumnoRepository.findAll();
    }

    public void guardarAlumno(Alumno alumno) {      
        
        alumnoRepository.save(alumno);
    }

    public Alumno buscarPorIdAlumno(int id){

        return alumnoRepository.findById(id).orElse(null);

    }

    public void eliminar(int nie){
        alumnoRepository.deleteById(nie);
    }

}
