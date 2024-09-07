package com.dsi.insibo.sice.Asistencia_personal.Aparato_Docente;
import org.springframework.data.repository.CrudRepository;
import com.dsi.insibo.sice.entity.DocenteAparato;

public interface AparatoDocenteRepository extends CrudRepository<DocenteAparato, Integer> {
  DocenteAparato findByNumeroAparatoDocente(int numeroAparatoDocente);

}