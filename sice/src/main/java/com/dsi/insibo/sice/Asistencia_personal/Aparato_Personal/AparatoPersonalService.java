package com.dsi.insibo.sice.Asistencia_personal.Aparato_Personal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dsi.insibo.sice.entity.PersonalAparato;

@Service
public class AparatoPersonalService {
    @Autowired
    private AparatoPersonalRepository aparatoPersonalRepository;
     //Obtine el numero del aparato del personal administrativo
    public PersonalAparato obtenerPorNumeroAparatoPersonal(int numeroAparatoPersonal) {
        return aparatoPersonalRepository.findByNumeroAparatoPersonal(numeroAparatoPersonal);
    }
    //Lista todos los numeros asignados en aparato
    public List<PersonalAparato> aparatoTodosPersonal(){
        return (List <PersonalAparato>) aparatoPersonalRepository.findAll();
    }
    //Guarda el numero de aparato asignado
    public void guardarPersonalAparato(PersonalAparato aparato){
        aparatoPersonalRepository.save(aparato);
    }
    //Busca el id del aparato
    public PersonalAparato buscarPorIdAparatoPersonal(int idPersonalAparato){
        return aparatoPersonalRepository.findById(idPersonalAparato).orElse(null);
    }
    //Metodo de eliminar aparato
    public void eliminarPersonalAparato(int idPersonalAparato){
        aparatoPersonalRepository.deleteById(idPersonalAparato);
    }
}
