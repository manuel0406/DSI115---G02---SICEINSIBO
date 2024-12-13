package com.dsi.insibo.sice.Seguridad.Configuraciones;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dsi.insibo.sice.Seguridad.SeguridadService.UsuarioService;
import com.dsi.insibo.sice.entity.Usuario;
import org.springframework.security.core.Authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class contrasenaFilter extends OncePerRequestFilter {
    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtén el nombre de usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            // Si no está autenticado, continúa con la cadena de filtros
            filterChain.doFilter(request, response);
            return;
        }
        String username = authentication.getName();

        // Verifica el estado de primerIngreso
        Usuario usuario = usuarioService.buscarPorCorreo(username);

        if (usuario.isPrimerIngreso() && !request.getRequestURI().equals("/update-password")) {
            response.sendRedirect("/update-password");
            return;
        }

        filterChain.doFilter(request, response);
    }
}