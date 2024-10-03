package com.dsi.insibo.sice.Calificaciones;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dsi.insibo.sice.entity.Actividad;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Integer> {   

    @Query("SELECT a FROM Actividad a WHERE a.asignacion.docente.duiDocente=:dui AND a.asignacion.materia.idMateria=:idMateria AND a.periodo.idPeriodo=:periodo ORDER BY a.periodo.numeroPeriodo ASC")
    List<Actividad> actividadesPorEspecialidadPeriodo(String dui, int idMateria, String periodo);

    @Query("SELECT a FROM Actividad a WHERE a.asignacion.docente.duiDocente=:dui AND a.asignacion.materia.idMateria=:idMateria  ORDER BY a.periodo.numeroPeriodo ASC")
    List<Actividad> actividadesPorEspecialidad(String dui, int idMateria);
}



