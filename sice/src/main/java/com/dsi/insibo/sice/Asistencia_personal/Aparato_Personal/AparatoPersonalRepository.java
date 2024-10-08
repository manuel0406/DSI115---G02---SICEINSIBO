package com.dsi.insibo.sice.Asistencia_personal.Aparato_Personal;
import org.springframework.data.repository.CrudRepository;
import com.dsi.insibo.sice.entity.PersonalAparato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AparatoPersonalRepository extends CrudRepository<PersonalAparato, Integer>{
    PersonalAparato findByNumeroAparatoPersonal(int numeroAparatoPersonal);
    Page<PersonalAparato> findAll(Pageable pageable);

}
