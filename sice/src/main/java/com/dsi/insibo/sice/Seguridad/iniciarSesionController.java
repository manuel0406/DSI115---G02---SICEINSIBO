package com.dsi.insibo.sice.Seguridad;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class iniciarSesionController {
    
    @GetMapping("/iniciarSesion")
	public String verIniciarSesion(){
		return "Seguridad/iniciarSesion";
	}
}
