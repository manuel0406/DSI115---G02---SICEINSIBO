package com.dsi.insibo.sice.Expediente_alumno;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsi.insibo.sice.entity.AnexoAlumno;

public interface AnexoRepository extends JpaRepository<AnexoAlumno, Integer> {
    
}
