package com.dsi.insibo.sice.Paquetes_escolar.Uniforme;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Uniforme;

@Repository
public interface UniformeRepository extends JpaRepository<Uniforme, Integer> {
    Uniforme findByAlumno(Alumno alumno);

    // @Query("SELECT DISTINCT u.fecha_entrega_uniforme FROM Uniforme u JOIN u.alumno a WHERE a.bachillerato.codigoBachillerato = :bachilleratoId")
    // List<String> obtenerFechasPorBachillerato(@Param("bachilleratoId") int bachilleratoId);

}
