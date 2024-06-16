package com.dsi.insibo.sice.Expediente_alumno;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.Calificaciones.NotaRepository;
import com.dsi.insibo.sice.entity.Actividad;
import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Nota;


@Service
public class AlumnoService {
    
    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private NotaRepository notaRepository;


    public List<Alumno> listarAlumnos(String carrera, String grado, String seccion){

        if(carrera !=null && grado!=null && seccion!=null){
            return (List<Alumno>)alumnoRepository.findAll(carrera, grado, seccion);

        }else if (carrera !=null &&  grado==null && seccion==null) {
            return (List<Alumno>)alumnoRepository.findAllPorCarrera(carrera);

        }else if (carrera ==null &&  grado !=null && seccion==null) {
             return (List<Alumno>)alumnoRepository.findAllPorGrado(grado);
             
        }else if (carrera ==null &&  grado==null && seccion != null) {
             return (List<Alumno>)alumnoRepository.findAllPorSeccion(seccion);

        }else if (carrera !=null &&  grado !=null && seccion==null) {
            return (List<Alumno>)alumnoRepository.findAllCarreraGrado(carrera, grado);
            
        }else if (carrera !=null &&  grado ==null && seccion !=null) {
            return (List<Alumno>)alumnoRepository.findAllCarreraSeccion(carrera, seccion);

        }else if (carrera ==null &&  grado !=null && seccion !=null) {
             return (List<Alumno>)alumnoRepository.findAllGradoSeccion(grado, seccion);
        }

        return alumnoRepository.findAll();
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

    public List<Alumno> findAlumnosByBachilleratoCodigoBachillerato(String codigoBachillerato) {
        return alumnoRepository.findByBachilleratoCodigoBachillerato(codigoBachillerato);
    }

}
