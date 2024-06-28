package com.dsi.insibo.sice;

import java.security.Permission;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dsi.insibo.sice.Seguridad.UsuarioRepository;
import com.dsi.insibo.sice.entity.Usuario;
import com.dsi.insibo.sice.entity.UsuarioPermiso;
import com.dsi.insibo.sice.entity.UsuarioRoleEnum;
import com.dsi.insibo.sice.entity.UsuarioRoles;

import jakarta.mail.Session;



@Controller
@SpringBootApplication
public class SiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiceApplication.class, args);
	}

	@GetMapping("/")
	public String holamundo( Model model) {
		model.addAttribute("titulo", "Inicio");
		return "home";
	}

	// CARGAR DATOS A BASE
	/*CommandLineRunner init (UsuarioRepository usuarioRepository){
		return arg ->{
			UsuarioPermiso crearUsuarioPermiso = UsuarioPermiso.builder().tipo("CREATE").build();
			UsuarioPermiso deleteUsuarioPermiso = UsuarioPermiso.builder().tipo("DELETE").build();
			UsuarioPermiso updateUsuarioPermiso = UsuarioPermiso.builder().tipo("UPDATE").build();
			UsuarioPermiso readUsuarioPermiso = UsuarioPermiso.builder().tipo("READ").build();
			
			UsuarioRoles roleAdmin = UsuarioRoles.builder().roleEnum(UsuarioRoleEnum.ADMINISTRADOR).usuarioPermiso(Set.of(crearUsuarioPermiso,deleteUsuarioPermiso)).build();
			UsuarioRoles roleDocente = UsuarioRoles.builder().roleEnum(UsuarioRoleEnum.DOCENTE).usuarioPermiso(Set.of(crearUsuarioPermiso,deleteUsuarioPermiso)).build();
			UsuarioRoles roleDirector = UsuarioRoles.builder().roleEnum(UsuarioRoleEnum.DIRECTOR).usuarioPermiso(Set.of(crearUsuarioPermiso,deleteUsuarioPermiso)).build();
			UsuarioRoles roleSubDirector = UsuarioRoles.builder().roleEnum(UsuarioRoleEnum.SUBDIRECTORA).usuarioPermiso(Set.of(crearUsuarioPermiso,deleteUsuarioPermiso)).build();
			UsuarioRoles rolePersonal = UsuarioRoles.builder().roleEnum(UsuarioRoleEnum.PERSONAL_ADMINISTRATIVO).usuarioPermiso(Set.of(crearUsuarioPermiso,deleteUsuarioPermiso)).build();
			UsuarioRoles roleSecretaria = UsuarioRoles.builder().roleEnum(UsuarioRoleEnum.SECRETARIA).usuarioPermiso(Set.of(crearUsuarioPermiso,deleteUsuarioPermiso)).build();

			 // Crear un usuario y asignar roles usando el builder
			 Usuario usuario = Usuario.builder()
			 .correoUsuario("cs21024@ues.edu.sv")
			 .contrasenaUsuario("1234")
			 .primerIngreso(true)
			 .estadoUsuario("Activo")
			 .accountNoExpired(true)
			 .accountLocked(false)
			 .credentialNoExpired(true)
			 .rolesUsuario(Set.of(roleAdmin, roleDocente))
			 .build();

			 Usuario usuario2 = Usuario.builder()
			 .correoUsuario("ivuan007@gmail.com")
			 .contrasenaUsuario("admin")
			 .primerIngreso(true)
			 .estadoUsuario("Activo")
			 .accountNoExpired(true)
			 .accountLocked(false)
			 .credentialNoExpired(true)
			 .rolesUsuario(Set.of(roleAdmin, roleDocente))
			 .build();
			
			usuarioRepository.saveAll(List.of(usuario,usuario2));
		};
	}*/



	@Autowired
	private SessionRegistry sessionRegistry;
	@GetMapping("/session")
	public ResponseEntity<?> getDetailResponseEntity() {
		String sessionId="";
		User userObject = null;
		List<Object>sessions = sessionRegistry.getAllPrincipals();

		for(Object session :sessions){
			if (session instanceof User) {
				userObject = (User) session;
			}
			// Usuario a recuperar y no incluimos las sesiones expiradas
			List <SessionInformation> sessionInformations = sessionRegistry.getAllSessions(session, false);
			for(SessionInformation sessionInformation : sessionInformations){
				sessionId = sessionInformation.getSessionId();
			}

		}

		Map<String, Object> response= new HashMap<>();	
		response.put("response", "Hello World");
		response.put("Session ID", sessionId);
		response.put("sessionUser", userObject);

		return ResponseEntity.ok(response);
	}
}
