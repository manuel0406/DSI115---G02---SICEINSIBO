package com.dsi.insibo.sice.Asistencia_personal.Aparato_Docente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.dsi.insibo.sice.entity.DocenteAparato;

@Service
public class AparatoDocenteService {
    @Autowired
    private AparatoDocenteRepository aparatoDocenteRepository;
    //obtine el numero del aparato docente
    public DocenteAparato obtenerPorNumeroAparatoDocente(int numeroAparatoDocente) {
        return aparatoDocenteRepository.findByNumeroAparatoDocente(numeroAparatoDocente);
    }
    //Listar todos los numeros del aparato docente
    public List<DocenteAparato> aparatoTodos(){
        return (List <DocenteAparato>) aparatoDocenteRepository.findAll();
    };
    //Guarda el numero del aparato docente
    public void guardarDocenteAparato(DocenteAparato aparato){
        aparatoDocenteRepository.save(aparato);
    };
    //Busca el id del aparato docente
    public DocenteAparato buscarPorIdAparatoDocente(int idDocenteAparato){
        return aparatoDocenteRepository.findById(idDocenteAparato).orElse(null);
    };
    //elimina el docente aparato
    public void eliminarDocenteAparato(int idDocenteAparato){
        aparatoDocenteRepository.deleteById(idDocenteAparato);
    };
    
}
