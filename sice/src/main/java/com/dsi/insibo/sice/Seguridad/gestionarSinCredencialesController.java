package com.dsi.insibo.sice.Seguridad;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.entity.Usuario;

@Controller
public class gestionarSinCredencialesController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/gestionarSinCredenciales")
    public String cargarSinCredenciales(Model model, @RequestParam(required = false, defaultValue = "1") int pagina) {
        pagina=(pagina-1);
        List<Usuario> listadoUsuarios = usuarioService.listaUsuariosDesactivadosIntervalos(pagina);

        List<UsuarioConNombre> listadoCompleto =new ArrayList<>();
        //Obtenemos los nombres
        for (Usuario usuario : listadoUsuarios) {
            String rol = usuario.getRolUsuario();
            String nombre="";

            if(rol.equals("Administrador")){
                nombre = "Administrador";
            }
            if(rol.equals("Docente")){

                nombre = usuario.getDocente().getNombreDocente() + " " + usuario.getDocente().getApellidoDocente() ;
            }
            if(rol.equals("Personal Administrativo")){
                nombre = usuario.getPersonalAdministrativo().getNombrePersonal()+ " " + usuario.getDocente().getApellidoDocente();
            }

            listadoCompleto.add(new UsuarioConNombre(usuario, nombre));
        }

        model.addAttribute("Usuarios", listadoCompleto);

       // Obtenemos la cantidad de usuarios que tenemos
        List<Usuario> totalUsuario = usuarioService.listaUsuariosDesactivados();
        int cantidad = (int) Math.ceil((double) totalUsuario.size() / 7);
        model.addAttribute("Cantidad", cantidad);
    
        return "Seguridad/gestionarSinCredenciales";
    }

    @GetMapping("/rechazarUsuario/{id}")
    public String bloquearUsuario(@PathVariable("id") int idUsuario, RedirectAttributes attribute) {
        
        Usuario usuario = usuarioService.buscarPorIdUsuario(idUsuario);
        usuario.setEstadoUsuario("Rechazado");
        usuarioService.guardarUsuario(usuario);
        return "redirect:/gestionarSinCredenciales";
    }

    @GetMapping("/aceptarUsuario/{id}")
    public String aceptarUsuario(@PathVariable("id") int idUsuario, RedirectAttributes attribute) {
        
        Usuario usuario = usuarioService.buscarPorIdUsuario(idUsuario);
        String contrasena = generateRandomPassword(8);
        usuario.setEstadoUsuario("Activo");
        usuario.setContrasenaUsuario(contrasena);
        usuarioService.guardarUsuario(usuario);
        return "redirect:/gestionarSinCredenciales";
    }


    //----------------------------------------------------------------
    //         ALGORITMO GENERADOR DE CONTRASEÑA ALEATORIA
    //----------------------------------------------------------------
        // Definimos los caracteres que queremos que se incluyan en la contraseña
        private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
        private static final String NUMBERS = "0123456789";

        public static String generateRandomPassword(int length) {
        // Concatenamos todos los caracteres posibles
        String allCharacters = UPPERCASE_LETTERS + LOWERCASE_LETTERS + NUMBERS;
        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder(length);

        // Aseguramos que la contraseña contenga al menos un carácter de cada tipo
        password.append(UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length())));
        password.append(LOWERCASE_LETTERS.charAt(random.nextInt(LOWERCASE_LETTERS.length())));
        password.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));

        // Rellenamos el resto de la contraseña con caracteres aleatorios
        for (int i = 4; i < length; i++) {
            password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
        }

        // Mezclamos los caracteres para que no sigan un patrón fijo
        char[] passwordArray = password.toString().toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int randomIndex = random.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temp;
        }

        return new String(passwordArray);
    }

    //----------------------------------------------------------------

    @GetMapping("/buscarUsuario")
    public String buscarUsuario(@RequestParam("correoUsuario") String correoUsuario, RedirectAttributes redirectAttributes, Model model) {
        Usuario usuarioBuscado = usuarioService.buscarPorCorreo(correoUsuario);
        

        if (usuarioBuscado == null) {
            // Usuario no encontrado, añadir mensaje de error
            redirectAttributes.addFlashAttribute("Error", "<b>¡Usuario no encontrado!</b> Verificar si ha escrito correctamente el correo.");
            return "redirect:/gestionarSinCredenciales"; // Redirigir a la página de gestión de credenciales
        }

        if (!usuarioBuscado.getEstadoUsuario().equals("Desactivado")) {
            // Usuario no encontrado, añadir mensaje de error
            redirectAttributes.addFlashAttribute("Error", "<b>¡Advertencia!</b> Su usuario no se encuentra desactivado.");
            return "redirect:/gestionarSinCredenciales"; // Redirigir a la página de gestión de credenciales
        }
        
        String nombre="";
        if(usuarioBuscado.getRolUsuario().equals("Administrador")){
            nombre = "Administrador";
        }
        if(usuarioBuscado.getRolUsuario().equals("Docente")){

            nombre = usuarioBuscado.getDocente().getNombreDocente() + " " + usuarioBuscado.getDocente().getApellidoDocente() ;
        }
        if(usuarioBuscado.getRolUsuario().equals("Personal Administrativo")){
            nombre = usuarioBuscado.getPersonalAdministrativo().getNombrePersonal()+ " " + usuarioBuscado.getDocente().getApellidoDocente();
        }

        //OCULTAMIENTO DE CONTRASEÑA
        Integer tamanyoContra = usuarioBuscado.getContrasenaUsuario().length();
        String codificacion="";

        for(int i = 0; i < tamanyoContra ;i++){
            codificacion = codificacion + "*";
        }
        usuarioBuscado.setContrasenaUsuario(codificacion);

        UsuarioConNombre usuarioConNombre = new UsuarioConNombre(usuarioBuscado, nombre);
   
        model.addAttribute("Cantidad", 0);
        model.addAttribute("Usuarios", usuarioConNombre);
        return "Seguridad/gestionarSinCredenciales";
    }


}
