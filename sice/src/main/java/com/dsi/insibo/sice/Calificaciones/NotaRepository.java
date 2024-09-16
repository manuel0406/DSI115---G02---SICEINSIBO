package com.dsi.insibo.sice.Calificaciones;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dsi.insibo.sice.entity.Nota;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {

    @Query("SELECT n FROM Nota n WHERE  n.actividad.idActividad=:idActividad ORDER BY n.alumno.apellidoAlumno ASC")
    List<Nota> notasActividad(@Param("idActividad") int idActividad);
    
    @Modifying
    @Query("DELETE FROM Nota n WHERE n.alumno.nie = :nie ")
    void deleteByAlumnoNie(int nie);
}
