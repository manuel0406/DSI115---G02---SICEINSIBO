package com.dsi.insibo.sice.Expediente_alumno;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlumnoController {

    @GetMapping("/home")
	public String holaMundo(){
		return "Expediente_alumno/registro";
	}


@GetMapping("/home/editar")
	public String Editar(){
		return "Expediente_alumno/editar";
	}



}
