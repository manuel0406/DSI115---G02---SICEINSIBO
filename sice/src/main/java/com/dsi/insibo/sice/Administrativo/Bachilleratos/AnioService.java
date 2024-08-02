package com.dsi.insibo.sice.Administrativo.Bachilleratos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.entity.AnioAcademico;

@Service
public class AnioService {

    @Autowired
    AnioRepository anioRepository;
    
    public List<AnioAcademico> listaAnio(){        
        return anioRepository.findAll();
    }

    public void guardarAnio(AnioAcademico anioAcademico){
        anioRepository.save(anioAcademico);
    }

    public AnioAcademico buscarPoridAnioAcademico(int id){
        return anioRepository.findById(id).orElse(null);
    }
}
