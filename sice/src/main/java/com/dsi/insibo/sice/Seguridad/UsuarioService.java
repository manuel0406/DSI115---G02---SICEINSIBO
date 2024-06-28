package com.dsi.insibo.sice.Seguridad;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.dsi.insibo.sice.Seguridad.UsuarioRepository;
import com.dsi.insibo.sice.entity.Usuario;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    //USUARIOS ACTIVOS - CON CREDENCIALES

    public List<Usuario> listaUsuariosActivosIntervalos(int offset){
        Pageable pageable = PageRequest.of(offset, 7);
        return (List<Usuario>) usuarioRepository.findByAccountLocked(true, pageable);
    }

    public List<Usuario> listaUsuariosActivos(){
        return (List<Usuario>) usuarioRepository.findByAccountLocked(true);
    }

    // USUARIOS INACTIVOS - SIN CREDENCIALES

    public List<Usuario> listaUsuariosDesactivados(){
        return (List<Usuario>) usuarioRepository.findByAccountLocked(true);
    }

    public List<Usuario> listaUsuariosDesactivadosIntervalos(int offset){
        Pageable pageable = PageRequest.of(offset, 7);
        return (List<Usuario>) usuarioRepository.findByAccountLocked(true, pageable);
    }

    // USUARIOS RECHAZADOS - SIN PERMISOS

    public List<Usuario> listaUsuariosRechazados(){
        return (List<Usuario>) usuarioRepository.findByAccountLocked(true);
    }

    public List<Usuario> listaUsuariosRechazadosIntervalos(int offset){
        Pageable pageable = PageRequest.of(offset, 7);
        return (List<Usuario>) usuarioRepository.findByAccountLocked(true, pageable);
    }

    // USUARIOS BLOQUEADOS - DENEGADOS
    public List<Usuario> listaUsuariosBloqueados(){
        return (List<Usuario>) usuarioRepository.findByAccountLocked(true);
    }

    public List<Usuario> listaUsuariosBloqueadosIntervalos(int offset){
        Pageable pageable = PageRequest.of(offset, 7);
        return (List<Usuario>) usuarioRepository.findByAccountLocked(true, pageable);
    }



    public Usuario buscarPorCorreoYContrasena(String correoUsuario, String contrasenaUsuario) {
        return usuarioRepository.findByCorreoUsuarioAndContrasenaUsuario(correoUsuario, contrasenaUsuario).orElse(null);
    }

    public Usuario buscarPorCorreo(String correoUsuario) {
        return usuarioRepository.findByCorreoUsuario(correoUsuario).orElse(null);
    }

    public Usuario buscarPorIdUsuario(int idUsuario) {
        return usuarioRepository.findByIdUsuario(idUsuario);
    }

    public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void eliminarUsuarioPorDocenteId(String idDocente) {
        usuarioRepository.deleteByDocente_DuiDocente(idDocente);
    }
    
    @Transactional
    public void eliminarUsuarioPorPersonalId(String idPersonal) {
        usuarioRepository.deleteByPersonalAdministrativo_DuiPersonal(idPersonal);
    }

    public Usuario buscarPorIdDocente(String idUsuario) {
        return usuarioRepository.findByDocente_DuiDocente(idUsuario);
    }

}
