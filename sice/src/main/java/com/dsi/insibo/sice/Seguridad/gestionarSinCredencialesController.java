package com.dsi.insibo.sice.Seguridad;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dsi.insibo.sice.entity.Usuario;

@Controller
public class gestionarSinCredencialesController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/gestionarSinCredenciales")
    public String verIniciarSesion(Model model) {
        //List<Usuario> listadoUsuarios = usuarioService.listaUsuariosDesactivado();
        //model.addAttribute("Usuarios", listadoUsuarios);
        return "/seguridad/gestionarSinCredenciales";
    }
}
