package com.dsi.insibo.sice.Expediente_alumno;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsi.insibo.sice.entity.Alumno;

public interface  AlumnoRepositoty extends JpaRepository<Alumno, Integer> {
    
}
