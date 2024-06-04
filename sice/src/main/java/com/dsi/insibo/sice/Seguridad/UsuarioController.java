package com.dsi.insibo.sice.Seguridad;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UsuarioService usuarioService;
    @PostMapping("/validarCorreo")
	public String validarUsuario(@ModelAttribute Usuario user) {

        // Obtenemos la informacion de los campos
        String correo =  user.getCorreoUsuario();
        String contra = user.getContrasenaUsuario();

        //Buscamos en la base el correo y la contraseña
        Usuario usuario = usuarioService.buscarPorCorreoYContrasena(correo, contra);

        // Validamosla existencia del usuario
        if(usuario==null){
            return "redirect:/iniciarSesion";
        }
        else{
            System.out.println("El correo es: "+ usuario.getIdUsuario());
            return "redirect:/recuperarContra";
        }
	}
}