package com.dsi.insibo.sice.Administrativo.Bachilleratos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dsi.insibo.sice.entity.AnioAcademico;

public class AnioService {

    @Autowired
    AnioRepository anioRepository;
    
    public List<AnioAcademico> listaAnio(){        
        return anioRepository.findAll();
    }

    public void guardarAnio(AnioAcademico anioAcademico){
        anioRepository.save(anioAcademico);
    }
}
