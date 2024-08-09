package com.dsi.insibo.sice.Administrativo.Materias.RespositoriosMaterias;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dsi.insibo.sice.entity.Materia;

public interface MateriasRepository extends JpaRepository<Materia, Integer> {
    // Obtener todas las materias.
    @Query("SELECT m FROM Materia m ORDER BY m.codMateria ASC")
    List<Materia> obtenerMaterias();

    @Query("SELECT m FROM Materia m WHERE m.tipoMateria = :tipo ORDER BY m.codMateria ASC")
    List<Materia> findMateriasByType(String tipo);
}
