package com.dsi.insibo.sice.Seguridad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dsi.insibo.sice.entity.Usuario;

@Controller
public class iniciarSesionController {

    @GetMapping("/iniciarSesion")
    public String verIniciarSesion(Model model) {

        Usuario usuario= new Usuario();
        model.addAttribute("usuario", usuario);
        return "Seguridad/iniciarSesion";
    }
}