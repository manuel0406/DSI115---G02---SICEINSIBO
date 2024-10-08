package com.dsi.insibo.sice.Asistencia_personal.Aparato_Docente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.dsi.insibo.sice.entity.DocenteAparato;

public interface AparatoDocenteRepository extends CrudRepository<DocenteAparato, Integer> {
  DocenteAparato findByNumeroAparatoDocente(int numeroAparatoDocente);
  Page<DocenteAparato> findAll(Pageable pageable);
}
