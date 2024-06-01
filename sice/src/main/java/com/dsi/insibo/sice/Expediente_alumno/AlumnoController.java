package com.dsi.insibo.sice.Expediente_alumno;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlumnoController {

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
