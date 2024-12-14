package com.dsi.insibo.sice.Seguridad;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.dsi.insibo.sice.Seguridad.SeguridadService.SessionService;
import com.dsi.insibo.sice.Seguridad.SeguridadService.UsuarioService;
import com.dsi.insibo.sice.entity.Usuario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public String verActualizarContrasena(Model model) {
        return "Seguridad/cambiarContrasena";
    }

    @PostMapping("/update-password")
    public String actualizarContrasena(@RequestParam String newPassword,
                                       @RequestParam String confirmNewPassword,
                                       RedirectAttributes redirectAttributes, Model model) {
        // Validamos y actualizar la contraseña para el usuario autentificado
        try {
    
            // Validación de confirmación de contraseña
            if (!newPassword.equals(confirmNewPassword)) {
                redirectAttributes.addFlashAttribute("error", "Las nuevas contraseñas no coinciden");
                redirectAttributes.addFlashAttribute("newPassword", newPassword);
                return "redirect:/update-password";
            }
    
            // Validación de la nueva contraseña
            int validCriteriaCount = validatePasswordCriteria(newPassword);
            if (validCriteriaCount < 4) {
                redirectAttributes.addFlashAttribute("error", "La contraseña debe cumplir con al menos 4 de los criterios.");
                redirectAttributes.addFlashAttribute("newPassword", newPassword);
                return "redirect:/update-password";
            }
    
            // Actualización de contraseña 
            Usuario usuario = sessionService.usuarioSession();
            usuario.setContrasenaUsuario(passwordEncoder.encode(newPassword));
            usuario.setPrimerIngreso(false);
            usuarioService.guardarUsuario(usuario);
    
        // Capturador de excepciones
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";    
    }
    
    private int validatePasswordCriteria(String password) {
        int criteriaMet = 0;
    
        // Criterio 1: Longitud mínima de 8 caracteres
        if (password.length() >= 8) criteriaMet++;
    
        // Criterio 2: Al menos una letra mayúscula
        if (Pattern.compile("[A-Z]").matcher(password).find()) criteriaMet++;
    
        // Criterio 3: Al menos una letra minúscula
        if (Pattern.compile("[a-z]").matcher(password).find()) criteriaMet++;
    
        // Criterio 4: Al menos un número
        if (Pattern.compile("[0-9]").matcher(password).find()) criteriaMet++;
    
        // Criterio 5: Al menos un carácter especial
        if (Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) criteriaMet++;
    
        return criteriaMet;
    }
    
}
