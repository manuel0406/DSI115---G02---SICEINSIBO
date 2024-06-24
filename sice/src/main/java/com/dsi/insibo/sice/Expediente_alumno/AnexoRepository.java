package com.dsi.insibo.sice.Expediente_alumno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.AnexoAlumno;

@Repository
public interface AnexoRepository extends JpaRepository<AnexoAlumno, Integer> {

    @Query("SELECT a FROM AnexoAlumno a WHERE a.alumno.nie = :nie")
    AnexoAlumno findByAlumnoNie(int nie);

    // AnexoAlumno findByIdAnexoAlumno(int idAnexoAlumno);

    AnexoAlumno findFirstByAlumno(Alumno alumno);

}
