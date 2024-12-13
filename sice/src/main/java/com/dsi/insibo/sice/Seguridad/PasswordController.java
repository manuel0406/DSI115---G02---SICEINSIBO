package com.dsi.insibo.sice.Seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.dsi.insibo.sice.Seguridad.SeguridadService.SessionService;
import com.dsi.insibo.sice.Seguridad.SeguridadService.UsuarioService;
import com.dsi.insibo.sice.entity.Usuario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PasswordController {
    
    // Consultador a BD tabla Usuario
    @Autowired
    private UsuarioService usuarioService;

    // Clase cifradora de contraseña
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Obtener usuario conectado
    @Autowired
    private SessionService sessionService;

    @GetMapping("/update-password")
    public String verActualizarContrasena() {
        return "Seguridad/cambiarContrasena";
    }

    @PostMapping("/update-password")
    public String actualizarContrasena(@RequestParam String newPassword,
                                       @RequestParam String confirmNewPassword,
                                       Model model) {
        
        // Validamos y actualizar la contraseña para el usuario autentificado
        try {

            // Validación de confirmación de contraseña
            if (!newPassword.equals(confirmNewPassword)) {
                model.addAttribute("error", "Las nuevas contraseñas no coinciden");
                return "redirect:/update-password";
            }

            // Actualización de contraseña 
            Usuario usuario = sessionService.usuarioSession();
            usuario.setContrasenaUsuario(passwordEncoder.encode(newPassword));
            usuarioService.guardarUsuario(usuario);

            // Confirmación de actualización con exito
            model.addAttribute("success", "Contraseña actualizada con exito");

        // Capturador de excepciones
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/login?actualizado=true";    
    }
    
}
