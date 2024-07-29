package com.dsi.insibo.sice.Administrativo.Orientadores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.entity.Orientador;

@Service
public class OrientadorService {
    @Autowired
    OrientadorRepository orientadorRepository;

    public void guardarOrientador(Orientador orientador) {

        orientadorRepository.save(orientador);
    }

    public List<Orientador> listaOrientador(){
        return orientadorRepository.findAll();
    }

    public void eliminarOrientador(int id){
        orientadorRepository.deleteById(id);
    }

}
