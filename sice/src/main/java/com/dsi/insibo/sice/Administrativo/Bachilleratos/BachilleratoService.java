package com.dsi.insibo.sice.Administrativo.Bachilleratos;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.entity.Bachillerato;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BachilleratoService {

    @Autowired
    private BachilleratoRepository bachilleratoRepository;

    public List<Bachillerato> listaBachilleratos() {
        return (List<Bachillerato>) bachilleratoRepository.findAll();
    }

    public List<Bachillerato> listaCarrera() {
        List<Bachillerato> bachilleratos = bachilleratoRepository.findAll(); // tu lógica para obtener los
                                                                             // bachilleratos;
        Set<String> nombresUnicos = new HashSet<>();

        return bachilleratos.stream()
                .filter(bachillerato -> nombresUnicos.add(bachillerato.getNombreCarrera()))
                .collect(Collectors.toList());
    }

    public void guardarBachillerato(Bachillerato bachillerato){
        bachilleratoRepository.save(bachillerato);
    }

    public Integer obtenerCodigoBachillerato(String carrera, int grado, String seccion) {
        return bachilleratoRepository.findByNombreCarreraAndGradoAndSeccion(carrera, grado, seccion)
                .map(Bachillerato::getCodigoBachillerato)
                .orElse(null);
    }
}
