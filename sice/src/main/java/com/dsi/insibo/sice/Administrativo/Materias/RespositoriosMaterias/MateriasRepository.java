package com.dsi.insibo.sice.Administrativo.Materias.RespositoriosMaterias;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dsi.insibo.sice.entity.Materia;

public interface MateriasRepository extends JpaRepository<Materia, String> {
    //Obtener todas las materias.
    List<Materia> findAll();
}
