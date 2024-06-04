package com.dsi.insibo.sice.Seguridad;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.dsi.insibo.sice.entity.Usuario;
import org.springframework.ui.Model;


@Controller
public class UsuarioController {

    @GetMapping("/validarCorreo")
    public String obtenerNombreUsuarioPorCorreo(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("correoUsuario", usuario);
        System.out.println("El correo es: ");

        
        // Realiza aquí la lógica para validar el correo y obtener el nombre de usuario
        return "/recuperarContra"; // Cambia esto según lo que necesites hacer después de validar el correo
    }
}