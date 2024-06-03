package com.dsi.insibo.sice.Seguridad;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dsi.insibo.sice.entity.Usuario;

@Controller
public class iniciarSesionController {
    
	//Variables globales
	String correo, contra;

    @GetMapping("/iniciarSesion")
	public String verIniciarSesion(){
		return "Seguridad/iniciarSesion";
	}
}
