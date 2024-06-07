package com.dsi.insibo.sice.Calificaciones;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class actividadesController {
    @GetMapping("/Actividades")
	public String verActividades(){
		return "Calificaciones/vistaActividades";
	}
}
