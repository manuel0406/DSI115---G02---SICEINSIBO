package com.dsi.insibo.sice.Seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.dsi.insibo.sice.Seguridad.ClasesDeSeguridad.PasswordValidationResponse;
import com.dsi.insibo.sice.Seguridad.SeguridadService.SessionService;
import com.dsi.insibo.sice.Seguridad.SeguridadService.UsuarioService;
import com.dsi.insibo.sice.entity.Usuario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PasswordController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        try {
            PasswordValidationResponse validationResponse = new PasswordValidationResponse(newPassword);
            if (!newPassword.equals(confirmNewPassword)) {
                redirectAttributes.addFlashAttribute("error", "Las nuevas contraseñas no coinciden");
                redirectAttributes.addFlashAttribute("newPassword", newPassword);
                redirectAttributes.addFlashAttribute("criterios", validationResponse);
                return "redirect:/update-password";
            }

            // Validación de la nueva contraseña
            if (validationResponse.getCriteriaMetCount() < 4) {
                redirectAttributes.addFlashAttribute("error", "La contraseña debe cumplir con al menos 4 de los criterios.");
                redirectAttributes.addFlashAttribute("newPassword", newPassword);
                redirectAttributes.addFlashAttribute("criterios", validationResponse);
                return "redirect:/update-password";
            }

            // Actualización de contraseña 
            Usuario usuario = sessionService.usuarioSession();
            usuario.setContrasenaUsuario(passwordEncoder.encode(newPassword));
            usuario.setPrimerIngreso(false);
            usuarioService.guardarUsuario(usuario);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";    
    }

}