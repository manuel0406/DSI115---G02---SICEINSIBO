package com.dsi.insibo.sice.Seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.Seguridad.ClasesDeSeguridad.PasswordGenerator;
import com.dsi.insibo.sice.entity.Usuario;

@Controller
public class recuperarContraController {

	@Autowired
    private UsuarioService usuarioService;
	@Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/recuperarContra")
	public String verRecuperarContra(Model model, @ModelAttribute("mensaje") String mensaje){
		
		 Usuario usuario= new Usuario();
        model.addAttribute("usuario", usuario);
		model.addAttribute("mensaje", mensaje);
		return "Seguridad/recuperarContra";
	}

    @PostMapping("/correoDeRecuperacion")
	public String validarCorreo(@ModelAttribute Usuario user, RedirectAttributes redirectAttributes) {

        // Obtenemos la informacion de los campos
        String correo =  user.getCorreoUsuario();

		//Buscamos en la base el correo y la contraseña
		Usuario usuario = usuarioService.buscarPorCorreo(correo);

		
		// Validamos la existencia del usuario
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("mensaje", "<b>¡Acceso inválido!</b> Sus crendenciales no existen.");
            return "redirect:/recuperarContra";
        } 

		//Validamos el estado del usuario
        if (usuario.isEnabled() != true || usuario.isAccountLocked() != true){
            redirectAttributes.addFlashAttribute("mensaje", "<b>¡Sin Acceso!</b> Su usuario esta bloqueado o desactivado");
            return "redirect:/recuperarContra";
        } 
        else {
			String nuevaContra = PasswordGenerator.generateRandomPassword(8); //Genero una nueva contraseña
			usuario.setContrasenaUsuario(passwordEncoder.encode(nuevaContra)); // Cambio la nueva contraseña
			usuario.setPrimerIngreso(true); // Restablezco primer inicio de sesion
			usuarioService.guardarUsuario(usuario); // Guardo el nuevo usuario.
			redirectAttributes.addFlashAttribute("Usuario", usuario);
			redirectAttributes.addFlashAttribute("nuevaContrasena", nuevaContra);
			return "redirect:/enviarCorreo";
        }
	}

	@Autowired
	EnvioCorreo envioCorreo;
	@GetMapping("/enviarCorreo")
	public String enviarCorreo(@ModelAttribute("Usuario") Usuario usuario, @ModelAttribute("nuevaContrasena") String nuevaContra){
		String destinatario = usuario.getCorreoUsuario();
		String encabezado = "Recuperación de contraseña";
		String texto= "Les saluda el Instituto Nacional Simón Bolivar [INSIBO] \n" +
					  "Ante su solicitud de recuperación de contraseña, por este medio se la hacemos llegar, " +
					  "aconsejamos guardarla de manera privada para evitar contratiempos en el desarrollo de sus actividades \n" +
					  "Tu contraseña es: " + nuevaContra + "\n" +
					  "Att. Administrador del Sistema Integral de Control y Progreso Educativo INSIBO [SICE - INSIBO] ";
		envioCorreo.sendEmail(destinatario, encabezado, texto);
		return "redirect:/login";
	}	

}
