package com.dsi.insibo.sice.Calificaciones;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class calificacionesController {
    @GetMapping("/calificaciones")
	public String verCalificaciones(){
		return "Calificaciones/vistaCalificaciones";
	}
}
