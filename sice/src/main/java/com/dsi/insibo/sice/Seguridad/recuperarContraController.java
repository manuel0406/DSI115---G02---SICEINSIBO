package com.dsi.insibo.sice.Seguridad;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class recuperarContraController {
    @GetMapping("/recuperarContra")
	public String verRecuperarContra(){
		return "Seguridad/recuperarContra";
	}
}
