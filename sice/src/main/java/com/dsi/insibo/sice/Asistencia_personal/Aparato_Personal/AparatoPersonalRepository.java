package com.dsi.insibo.sice.Asistencia_personal.Aparato_Personal;
import org.springframework.data.repository.CrudRepository;

import com.dsi.insibo.sice.entity.PersonalAparato;

public interface AparatoPersonalRepository extends CrudRepository<PersonalAparato, Integer>{
    PersonalAparato findByNumeroAparatoPersonal(int numeroAparatoPersonal);
}
