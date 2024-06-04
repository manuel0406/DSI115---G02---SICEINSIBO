package com.dsi.insibo.sice.Seguridad;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dsi.insibo.sice.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>  {
    Usuario findByCorreoUsuario(String correoUsuario);
}
