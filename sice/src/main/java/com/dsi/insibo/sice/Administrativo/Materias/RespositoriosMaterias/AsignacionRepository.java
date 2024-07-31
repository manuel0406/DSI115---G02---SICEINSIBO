package com.dsi.insibo.sice.Administrativo.Materias.RespositoriosMaterias;

import com.dsi.insibo.sice.entity.Asignacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AsignacionRepository extends JpaRepository<Asignacion, Integer>{
    
    @Query("SELECT a FROM Asignacion a WHERE a.materia.idMateria = :idMateria")
    List<Asignacion> obtenerAsignacionExistente(int idMateria);

    @Query("SELECT a.docente.duiDocente FROM Asignacion a " 
        + "WHERE a.materia.idMateria != :idMateria "
        + "GROUP BY a.docente.duiDocente " 
        + "HAVING COUNT(DISTINCT a.materia.idMateria) = 3")
    List<String> findDocentesWithThreeDistinctMaterias(int idMateria);

    @Query("SELECT a FROM Asignacion a WHERE a.docente.duiDocente = :duiDocente AND a.materia.idMateria = :idMateria")
    List<Asignacion> findByDocenteAndMateria(String duiDocente, int idMateria);

    @Query("SELECT a FROM Asignacion a WHERE a.materia.idMateria = :idMateria")
    List<Asignacion> findByMateria(int idMateria);
}
