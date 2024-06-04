package com.dsi.insibo.sice.Seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dsi.insibo.sice.entity.Usuario;

@Controller
public class recuperarContraController {
    @GetMapping("/recuperarContra")
	public String verRecuperarContra(Model model){
		
		 Usuario usuario= new Usuario();
        model.addAttribute("usuario", usuario);
		return "Seguridad/recuperarContra";
	}

	@Autowired
    private UsuarioService usuarioService;
    @PostMapping("/correoDeRecuperacion")
	public String validarCorreo(@ModelAttribute Usuario user) {

        // Obtenemos la informacion de los campos
        String correo =  user.getCorreoUsuario();

		 //Buscamos en la base el correo y la contraseña
		 Usuario usuario = usuarioService.buscarPorCorreo(correo);

		System.out.println(usuario.getContrasenaUsuario());

		return "redirect:/iniciarSesion";
	}
}
