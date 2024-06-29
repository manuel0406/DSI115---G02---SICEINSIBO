package com.dsi.insibo.sice.Seguridad;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.security.SecureRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.entity.Usuario;
import com.dsi.insibo.sice.entity.UsuarioRoleEnum;
import com.dsi.insibo.sice.entity.UsuarioRoles;

@Controller
public class gestionarRechazados {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/gestionarRechazados")
    public String cargarRechazados(Model model, @RequestParam(required = false, defaultValue = "1") int pagina) {
        pagina=(pagina-1);
        List<Usuario> listadoUsuarios = usuarioService.listaUsuariosRechazadosIntervalos(pagina);

        List<UsuarioConNombre> listadoCompleto =new ArrayList<>();
        //Obtenemos los nombres
        for (Usuario usuario : listadoUsuarios) {
            Set<UsuarioRoles> rol = usuario.getRolesUsuario();
            // Verificar si hay algún UsuarioRoles con role_name igual a "ADMINISTRADOR"
            boolean isAdmin = rol.stream()
                                 .anyMatch(usuarioRoles -> usuarioRoles.getRoleEnum().equals(UsuarioRoleEnum.ADMINISTRADOR));
            // Verificar si hay algún UsuarioRoles con role_name igual a "DOCENTE"
            boolean isDocente = rol.stream()
                                 .anyMatch(usuarioRoles -> usuarioRoles.getRoleEnum().equals(UsuarioRoleEnum.DOCENTE));
            // Verificar si hay algún UsuarioRoles con role_name igual a "PERSONAL_ADMINISTRATIVO"
            boolean isPersonal = rol.stream()
                                   .anyMatch(usuarioRoles -> usuarioRoles.getRoleEnum().equals(UsuarioRoleEnum.PERSONAL_ADMINISTRATIVO));
            // Verificar si hay algún UsuarioRoles con role_name igual a "DIRECTOR"
            boolean isDirector = rol.stream()
                                   .anyMatch(usuarioRoles -> usuarioRoles.getRoleEnum().equals(UsuarioRoleEnum.DIRECTOR));                      
            // Verificar si hay algún UsuarioRoles con role_name igual a "SECRETARIA"
            boolean isSecretaria = rol.stream()
                                   .anyMatch(usuarioRoles -> usuarioRoles.getRoleEnum().equals(UsuarioRoleEnum.SECRETARIA));
            // Verificar si hay algún UsuarioRoles con role_name igual a "SUBDIRECTORA"
            boolean isSubDirectora = rol.stream()
                                   .anyMatch(usuarioRoles -> usuarioRoles.getRoleEnum().equals(UsuarioRoleEnum.SUBDIRECTORA));
            String nombre="";

            if(isAdmin){
                nombre = "ADMINISTRADOR";
                if (usuario.getDocente() != null) {
                    nombre = usuario.getDocente().getNombreDocente() + " " + usuario.getDocente().getApellidoDocente();
                }
                if (usuario.getPersonalAdministrativo() != null) {
                    nombre = usuario.getPersonalAdministrativo().getNombrePersonal()+ " " + usuario.getDocente().getApellidoDocente();
                }
            }
            if(isDocente || isDirector || isSubDirectora){
                nombre = "DOCENTE";
                if (usuario.getDocente() != null) {
                    nombre = usuario.getDocente().getNombreDocente() + " " + usuario.getDocente().getApellidoDocente();
                }
                if (usuario.getPersonalAdministrativo() != null) {
                    nombre = usuario.getPersonalAdministrativo().getNombrePersonal()+ " " + usuario.getDocente().getApellidoDocente();
                }
            }
            if(isPersonal || isSecretaria){
                nombre = "PERSONAL";
                if (usuario.getDocente() != null) {
                    nombre = usuario.getDocente().getNombreDocente() + " " + usuario.getDocente().getApellidoDocente();
                }
                if (usuario.getPersonalAdministrativo() != null) {
                    nombre = usuario.getPersonalAdministrativo().getNombrePersonal()+ " " + usuario.getDocente().getApellidoDocente();
                }
            }

            listadoCompleto.add(new UsuarioConNombre(usuario, nombre));
        }

        model.addAttribute("Usuarios", listadoCompleto);

       // Obtenemos la cantidad de usuarios que tenemos
        List<Usuario> totalUsuario = usuarioService.listaUsuariosRechazados();
        int cantidad = (int) Math.ceil((double) totalUsuario.size() / 7);
        model.addAttribute("Cantidad", cantidad);
    
        return "Seguridad/gestionarRechazados";
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/aceptarUsuarioRechazado/{id}")
    public String aceptarUsuarioRechazado(@PathVariable("id") int idUsuario, RedirectAttributes attribute) {
        Usuario usuario = usuarioService.buscarPorIdUsuario(idUsuario);
        String contrasena = generateRandomPassword(8);
        usuario.setEnabled(true);
        usuario.setAccountLocked(true);
        //usuario.setContrasenaUsuario(passwordEncoder.encode(contrasena));
        usuario.setContrasenaUsuario("admin123");
        usuarioService.guardarUsuario(usuario);
        return "redirect:/gestionarRechazados";
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

    @GetMapping("/buscarUsuariosRechazados")
    public String buscarUsuario(@RequestParam("correoUsuario") String correoUsuario, RedirectAttributes redirectAttributes, Model model) {
        Usuario usuarioBuscado = usuarioService.buscarPorCorreo(correoUsuario);
        

        if (usuarioBuscado == null) {
            // Usuario no encontrado, añadir mensaje de error
            redirectAttributes.addFlashAttribute("Error", "<b>¡Usuario no encontrado!</b> Verificar si ha escrito correctamente el correo.");
            return "redirect:/gestionarRechazados"; // Redirigir a la página de gestión de credenciales
        }

        if (usuarioBuscado.isEnabled() != false || usuarioBuscado.isAccountLocked() != false) {
            // Usuario no encontrado, añadir mensaje de error
            redirectAttributes.addFlashAttribute("Error", "<b>¡Advertencia!</b> Su usuario no se encuentra rechazado.");
            return "redirect:/gestionarRechazados"; // Redirigir a la página de gestión de credenciales
        }
        
        Set<UsuarioRoles> rol = usuarioBuscado.getRolesUsuario();
        // Verificar si hay algún UsuarioRoles con role_name igual a "ADMINISTRADOR"
        boolean isAdmin = rol.stream()
                            .anyMatch(usuarioRoles -> usuarioRoles.getRoleEnum().equals(UsuarioRoleEnum.ADMINISTRADOR));
        // Verificar si hay algún UsuarioRoles con role_name igual a "DOCENTE"
        boolean isDocente = rol.stream()
                            .anyMatch(usuarioRoles -> usuarioRoles.getRoleEnum().equals(UsuarioRoleEnum.DOCENTE));
        // Verificar si hay algún UsuarioRoles con role_name igual a "PERSONAL_ADMINISTRATIVO"
        boolean isPersonal = rol.stream()
                            .anyMatch(usuarioRoles -> usuarioRoles.getRoleEnum().equals(UsuarioRoleEnum.PERSONAL_ADMINISTRATIVO));
        // Verificar si hay algún UsuarioRoles con role_name igual a "DIRECTOR"
        boolean isDirector = rol.stream()
                            .anyMatch(usuarioRoles -> usuarioRoles.getRoleEnum().equals(UsuarioRoleEnum.DIRECTOR));                      
        // Verificar si hay algún UsuarioRoles con role_name igual a "SECRETARIA"
        boolean isSecretaria = rol.stream()
                            .anyMatch(usuarioRoles -> usuarioRoles.getRoleEnum().equals(UsuarioRoleEnum.SECRETARIA));
        // Verificar si hay algún UsuarioRoles con role_name igual a "SUBDIRECTORA"
        boolean isSubDirectora = rol.stream()
                            .anyMatch(usuarioRoles -> usuarioRoles.getRoleEnum().equals(UsuarioRoleEnum.SUBDIRECTORA));
        String nombre="";

        if(isAdmin){
            nombre = "ADMINISTRADOR";
            if (usuarioBuscado.getDocente() != null) {
                nombre = usuarioBuscado.getDocente().getNombreDocente() + " " + usuarioBuscado.getDocente().getApellidoDocente();
            }
            if (usuarioBuscado.getPersonalAdministrativo() != null) {
                nombre = usuarioBuscado.getPersonalAdministrativo().getNombrePersonal()+ " " + usuarioBuscado.getDocente().getApellidoDocente();
            }
        }
        if(isDocente || isDirector || isSubDirectora){
            nombre = "DOCENTE";
            if (usuarioBuscado.getDocente() != null) {
                nombre = usuarioBuscado.getDocente().getNombreDocente() + " " + usuarioBuscado.getDocente().getApellidoDocente();
            }
            if (usuarioBuscado.getPersonalAdministrativo() != null) {
                nombre = usuarioBuscado.getPersonalAdministrativo().getNombrePersonal()+ " " + usuarioBuscado.getDocente().getApellidoDocente();
            }
        }
        if(isPersonal || isSecretaria){
            nombre = "PERSONAL";
            if (usuarioBuscado.getDocente() != null) {
                nombre = usuarioBuscado.getDocente().getNombreDocente() + " " + usuarioBuscado.getDocente().getApellidoDocente();
            }
            if (usuarioBuscado.getPersonalAdministrativo() != null) {
                nombre = usuarioBuscado.getPersonalAdministrativo().getNombrePersonal()+ " " + usuarioBuscado.getDocente().getApellidoDocente();
            }
        }

        UsuarioConNombre usuarioConNombre = new UsuarioConNombre(usuarioBuscado, nombre);
   
        model.addAttribute("Cantidad", 0);
        model.addAttribute("Usuarios", usuarioConNombre);
        return "Seguridad/gestionarRechazados";
    }
}
