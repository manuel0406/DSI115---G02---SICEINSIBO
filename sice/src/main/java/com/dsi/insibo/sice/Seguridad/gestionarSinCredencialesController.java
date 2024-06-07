package com.dsi.insibo.sice.Seguridad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dsi.insibo.sice.entity.Usuario;

@Controller
public class gestionarSinCredencialesController {
    
    @GetMapping("/gestionarSinCredenciales")
    public String verIniciarSesion() {
        return "/seguridad/gestionarSinCredenciales";
    }
}
