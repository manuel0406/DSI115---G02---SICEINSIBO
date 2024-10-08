package com.dsi.insibo.sice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.dsi.insibo.sice.Seguridad.SeguridadService.UsuarioRepository;
import com.dsi.insibo.sice.entity.Usuario;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional  // Asegura que las pruebas sean transaccionales y limpien después de ejecutarse
public class UsuarioTest {

    @Autowired 
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        // Inicializar el usuario antes de cada prueba
        usuario = new Usuario();
        usuario.setCorreoUsuario("test@ejemplo.com");
        usuario.setContrasenaUsuario("contrasena123");
        usuario.setPrimerIngreso(true);
        usuario.setEnabled(true);
        usuario.setAccountNoExpired(true);
        usuario.setAccountLocked(true);
        usuario.setCredentialNoExpired(true);
        // Puedes establecer más atributos según sea necesario
    }

    // LISTADO DE SEGURIDAD Y ENCRIPTAMIENTO
    @Test
    @Rollback(true) // Cambia a true para mantener el entorno limpio
    public void testListarUsuarios() {
        // Guardar el usuario para que haya al menos uno en la base de datos
        usuarioRepository.save(usuario);

        // Listar todos los usuarios
        List<Usuario> usuarios = usuarioRepository.findAll();

        // Imprimir lista
        imprimirUsuarios(usuarios);

        // Verificar que la lista de usuarios no esté vacía
        assertThat(usuarios).isNotEmpty();
        assertThat(usuarios.size()).isGreaterThan(0); // Asegúrate de que hay al menos un usuario
        System.out.println("----------- Listado de usuarios exitoso -----------");
    }

    public void imprimirUsuarios(List<Usuario> usuarios) {
        System.out.println("Listado de Usuarios:");
        for (Usuario usuario : usuarios) {
            System.out.println("ID: " + usuario.getIdUsuario() +
                               ", Correo: " + usuario.getCorreoUsuario() +
                               ", Contraseña: " + usuario.getContrasenaUsuario() +
                               ", Primer Ingreso: " + usuario.isPrimerIngreso() +
                               ", Activo: " + usuario.isEnabled() +
                               ", Cuenta No Expirada: " + usuario.isAccountNoExpired() +
                               ", Bloqueado: " + usuario.isAccountLocked() +
                               ", Credenciales No Expiradas: " + usuario.isCredentialNoExpired());
        }
        System.out.println("---------------------------------------------------");
    }
    
    // GESTIÓN DE CREDENCIALES
    @Test
    @Rollback(true) // Mantiene el entorno limpio
    public void cambioEstadoUsuario() {
        // Guardar el usuario inicial
        usuarioRepository.save(usuario);

        // Cambiar y mostrar los estados de los usuarios
        cambiarEstadoYListarUsuarios(true, true, "ACTIVOS");
        cambiarEstadoYListarUsuarios(true, false, "BLOQUEADOS");
        cambiarEstadoYListarUsuarios(false, true, "INACTIVOS");
        cambiarEstadoYListarUsuarios(false, false, "RECHAZADOS");
    }

    private void cambiarEstadoYListarUsuarios(boolean enabled, boolean accountLocked, String estado) {
        // Cambiar el estado del usuario
        usuario.setEnabled(enabled);
        usuario.setAccountLocked(accountLocked);
        usuarioRepository.save(usuario);

        // Listar los usuarios según el estado
        List<Usuario> usuarios = usuarioRepository.findByEnabledAndAccountLocked(enabled, accountLocked);
        System.out.println(" ------------------------------ " + estado + " ------------------------------");
        imprimirUsuarios(usuarios);
        System.out.println("----------- Cambio de estado exitoso para " + estado + " -----------");
    }

    // PRUEBA PARA CREAR UN NUEVO USUARIO
    @Test
    @Rollback(true) // Mantiene el entorno limpio
    public void testCrearUsuario() {
        // Guardar el usuario en el repositorio
        usuarioRepository.save(usuario);

        // Verificar que el usuario se haya guardado correctamente
        Optional<Usuario> usuarioGuardado = usuarioRepository.findByCorreoUsuario("test@ejemplo.com");
        assertThat(usuarioGuardado).isPresent();
        assertThat(usuarioGuardado.get().getCorreoUsuario()).isEqualTo("test@ejemplo.com");
        System.out.println("----------- Usuario creado exitosamente -----------");
    }

    // PRUEBA PARA ACTUALIZAR EL CORREO DE UN USUARIO
    @Test
    @Rollback(true) // Mantiene el entorno limpio
    public void testActualizarCorreoUsuario() {
        // Guardar el usuario inicialmente
        usuarioRepository.save(usuario);

        // Actualizar el correo
        String nuevoCorreo = "nuevo@ejemplo.com";
        usuario.setCorreoUsuario(nuevoCorreo);
        usuarioRepository.save(usuario);

        // Verificar que el correo se haya actualizado
        Optional<Usuario> usuarioActualizado = usuarioRepository.findByCorreoUsuario(nuevoCorreo);
        assertThat(usuarioActualizado).isPresent();
        assertThat(usuarioActualizado.get().getCorreoUsuario()).isEqualTo(nuevoCorreo);
        System.out.println("----------- Correo del usuario actualizado exitosamente -----------");
    }
}
