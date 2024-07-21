package com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.Administrativo.Materias.RespositoriosMaterias.BachilleratosRespository;
import com.dsi.insibo.sice.entity.Bachillerato;

@Service
public class BachilleratosService {
    
    @Autowired
    private BachilleratosRespository bachilleratosRepository;

    //OBTENER GRADOS
    public Set<Integer> obtenerGrados(){
        return (Set<Integer>) bachilleratosRepository.findGrados();
    }

    //OBTENER TECNICOS
    public Set<String> obtenerTecnicos(){
        return (Set<String>) bachilleratosRepository.findTecnicos();
    }    

    //OBTENER SECCIONES
    public Set<String> obtenerSecciones(String tecnico, Integer grado){
        return (Set<String>) bachilleratosRepository.findSecciones(tecnico,grado);
    }    

    //OBTENER PRIMEROS AÑOS
    public List<Bachillerato> obtenerPrimeros(){
        return (List<Bachillerato>) bachilleratosRepository.findPorSecciones(1);
    } 

    //OBTENER SEGUNDOS AÑOS
    public List<Bachillerato> obtenerSegundos(){
        return (List<Bachillerato>) bachilleratosRepository.findPorSecciones(2);
    }  

    //OBTENER TERCEROS AÑOS
    public List<Bachillerato> obtenerTerceros(){
        return (List<Bachillerato>) bachilleratosRepository.findPorSecciones(3);
    }
    
    // OBTENER POR ID
    public Bachillerato obtenerBachilleratoPorId(String codigoBachillerato){
        return bachilleratosRepository.findById(codigoBachillerato).orElse(null);
    }
}
