package com.dsi.insibo.sice.Seguridad;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.Seguridad.UsuarioRepository;
import com.dsi.insibo.sice.entity.Usuario;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listaUsuarios(){
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Usuario buscarPorId(int id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario buscarPorCorreo(String correoUsuario){
        return usuarioRepository.findByCorreoUsuario(correoUsuario);
    }

}
