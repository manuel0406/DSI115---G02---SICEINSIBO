package com.dsi.insibo.sice.Seguridad;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.dsi.insibo.sice.entity.Usuario;

@Controller
public class UsuarioController  {

    @GetMapping("/iniciarSesion")
    public String verIniciarSesion(Model model) {

        Usuario usuario= new Usuario();
        model.addAttribute("usuario", usuario);
        return "/seguridad/iniciarSesion";
    }

    @PostMapping("/validarCorreo")
	public String validarUsuario(@ModelAttribute Usuario user) {

		System.out.println("El correo es: "+ user.getCorreoUsuario());
		return "redirect:/recuperarContra";
	}
}