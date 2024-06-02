package com.dsi.insibo.sice.Expediente_alumno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Usuario;

@Controller
public class AlumnoController {

	@Autowired
	private AlumnoService alumnoService;
 @GetMapping("/guardar")
    public String guardarAlumno(@RequestParam int nie, @RequestParam String nombre) {

        Alumno alumno = alumnoService.guardarAlumno(nie, nombre);
        return "Usuario guardado con ID: "+  alumno.getNie();
    }

    @GetMapping("/ExpedienteAlumno/Crear")
	public String holaMundo(){
		return "Expediente_alumno/registro";
	}


@GetMapping("/ExpedienteAlumno/editar")
	public String Editar(){
		return "Expediente_alumno/editar";
	}

@GetMapping("/ExpedienteAlumno/ver")
public String verAlumno(){
	return "Expediente_alumno/verAlumno";
}
}
