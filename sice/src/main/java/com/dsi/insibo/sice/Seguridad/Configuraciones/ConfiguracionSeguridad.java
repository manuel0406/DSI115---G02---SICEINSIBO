package com.dsi.insibo.sice.Seguridad.Configuraciones;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.context.annotation.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfiguracionSeguridad {
    
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    JwtUtils jwtUtils;

    // CONFIGURACION DE FILTROS BASICOS
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        
        //JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        //jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        //jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        // He dejado activo el .csrf(csrf -> csrf.disable())
        return httpSecurity
                // ALWAYS = Siempre crea una nueva sesión HTTP, incluso si ya existe una sesión.
                // STATELESS =  No crea una sesión, pero utilizará una sesión existente si ya está presente.
                // NEVER = La sesión solo se crea si es requerida.
                // IF_REQUIERED = No crea ni utiliza sesiones HTTP en absoluto.
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); // Politicas de sesiones 
                    session.maximumSessions(1).sessionRegistry(sessionRegistry()); // Número maximo de sesiones
                    session.sessionFixation().migrateSession(); // Previene la fijación de sesión migrando a una nueva sesión
                    // session.invalidSessionUrl("null")
                    // session.expiredUrl("/login") Tiempo de expiración
                })
                .authorizeHttpRequests(http -> {
                    http.requestMatchers("/", "/css/**", "/js/**", "/Imagenes/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/recuperarContra/**", "/enviarCorreo/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/correoDeRecuperacion/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/expedientedocente/plantadocente/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/validarCorreo/**").permitAll();

                    http.anyRequest().authenticated(); // AUTENTIFICACIÓN A TODOS
               })
                .formLogin(form -> form
                    .loginPage("/login")
                    .permitAll()
                )
                .formLogin(form -> form
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutUrl("/logout")  // URL para el logout
                    .logoutSuccessUrl("/login")  // URL a la que redirigir después del logout
                    .invalidateHttpSession(true)  // Invalidar la sesión
                    .deleteCookies("JSESSIONID")  // Borrar las cookies
                )
                //.addFilter(jwtAuthenticationFilter)
                .build();
    }

    // AUTENTIFICADOR DE USUARIOS
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsServiceImpl) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());         // Contraseña
        //provider.setUserDetailsService(userDetailsService());   // Información del Usuario
        provider.setUserDetailsService(userDetailsServiceImpl);
        return provider;
    }

    // LEYES DE ENCRIPTAMIENTO DE CONTRASEÑAS
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // SOLO PARA PRUEBAS
        //return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("carranza")
                               .password(passwordEncoder().encode("admin123"))
                               .roles("DOCENTE")
                               .build());
        return manager;
    }

    /*
     * // AUTENTIFICACION DE USUARIOS
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception{
        
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                            .userDetailsService(userDetailsService())
                            //.userDetailsService(userDetailsServiceImpl)
                            .passwordEncoder(passwordEncoder)
                            .and()
                            .build();
    }
     */

    //Obtener datos de la sesion
    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

}
