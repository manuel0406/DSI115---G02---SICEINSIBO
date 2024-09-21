package com.dsi.insibo.sice.Paquetes_escolar.Zapatos;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Zapatos;

@Repository
public interface ZapatosRepository extends JpaRepository<Zapatos, Integer> {
    public List<Zapatos> findByAlumno(Alumno alumno);

    // @Query("SELECT DISTINCT z.fechaEntegaZapatos FROM Zapatos z JOIN z.alumno a WHERE a.bachillerato.codigoBachillerato = :bachilleratoId")
    // List<Date> findDistinctFechasByBachillerato(@Param("bachilleratoId") int bachilleratoId);

}
