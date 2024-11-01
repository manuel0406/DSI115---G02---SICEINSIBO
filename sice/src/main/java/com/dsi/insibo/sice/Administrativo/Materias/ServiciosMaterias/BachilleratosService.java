package com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.Administrativo.Materias.RespositoriosMaterias.BachilleratosRespository;
import com.dsi.insibo.sice.entity.Bachillerato;

@Service
public class BachilleratosService {
    
    @Autowired
    private BachilleratosRespository bachilleratosRepository;

    //OBTENER PRIMEROS AÑOS
    // 1. Usado en nuevas asignaciones.
    public List<Bachillerato> obtenerPrimeros(){
        return (List<Bachillerato>) bachilleratosRepository.findPorSecciones(1);
    } 

    //OBTENER SEGUNDOS AÑOS
    // 1. Usado en nuevas asignaciones.
    public List<Bachillerato> obtenerSegundos(){
        return (List<Bachillerato>) bachilleratosRepository.findPorSecciones(2);
    }  

    //OBTENER TERCEROS AÑOS
    // 1. Usado en nuevas asignaciones.
    public List<Bachillerato> obtenerTerceros(){
        return (List<Bachillerato>) bachilleratosRepository.findPorSecciones(3);
    }
    
    // OBTENER POR ID
    public Bachillerato obtenerBachilleratoPorId(Integer codigoBachillerato){
        return bachilleratosRepository.findById(codigoBachillerato).orElse(null);
    }
}
