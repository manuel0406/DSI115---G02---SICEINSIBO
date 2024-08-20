package com.dsi.insibo.sice.Horario.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.dsi.insibo.sice.entity.AsignacionHorario;

public interface HorarioRepository extends CrudRepository<AsignacionHorario, Integer> {
    @Query("SELECT ah.horarioBase.idHorarioBase FROM AsignacionHorario ah WHERE ah.asignacion.bachillerato.codigoBachillerato = :codigoBachillerato AND ah.asignacion.bachillerato.anioAcademico.activoAnio = true")
    List<Integer> findIdHorarioBaseByCodigoBachilleratoAndActivoAnioTrue(int codigoBachillerato);
}