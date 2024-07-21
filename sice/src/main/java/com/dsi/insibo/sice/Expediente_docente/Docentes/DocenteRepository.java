package com.dsi.insibo.sice.Expediente_docente.Docentes;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dsi.insibo.sice.entity.Docente;
import com.dsi.insibo.sice.entity.UsuarioRoleEnum;

public interface DocenteRepository extends CrudRepository<Docente, String> {
   
    //@Query("SELECT b FROM Bachillerato b WHERE b.grado = :grado ORDER BY b.nombreCarrera ASC, b.seccion ASC")
    @Query("SELECT d FROM Docente d "
         + "JOIN Usuario u ON d.duiDocente = u.docente.duiDocente " 
         + "JOIN u.rolesUsuario r " +
           "WHERE r.roleEnum = :roleEnum")
    List<Docente> obtenerDocentesPorRol(@Param("roleEnum") UsuarioRoleEnum roleEnum);



}
