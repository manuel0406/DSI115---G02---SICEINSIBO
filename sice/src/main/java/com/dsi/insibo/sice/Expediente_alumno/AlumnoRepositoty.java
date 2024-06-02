package com.dsi.insibo.sice.Expediente_alumno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dsi.insibo.sice.entity.Alumno;

@Repository
public interface  AlumnoRepositoty extends JpaRepository<Alumno, Integer> {
    
}
