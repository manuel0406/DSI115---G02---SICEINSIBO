package com.dsi.insibo.sice.Administrativo.Bachilleratos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ch.qos.logback.core.model.Model;

@Controller
public class ControllerBachilleratos {
    
    @GetMapping("/administracion/prueba")
    public String prueba(Model model){
        return "Administrativo/GestionBachilleratos/prueba.html";
    }
}
