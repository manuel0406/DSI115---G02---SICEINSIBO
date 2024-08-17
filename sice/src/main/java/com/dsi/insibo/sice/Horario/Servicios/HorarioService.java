package com.dsi.insibo.sice.Horario.Servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.Horario.Repositorios.HorarioRepository;
import com.dsi.insibo.sice.entity.AsignacionHorario;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    // Guardar una asignacion
    public void guardarHoraAsignacion(AsignacionHorario asignacionHorario) {
        horarioRepository.save(asignacionHorario);
    }

    // Método para obtener los idHorarioBase dado un codigoBachillerato y año activo
    public List<Integer> obtenerIdHorariosBasePorCodigoBachillerato(int codigoBachillerato) {
        return horarioRepository.findIdHorarioBaseByCodigoBachilleratoAndActivoAnioTrue(codigoBachillerato);
    }

    // Horas asignadas dado un codigoBachillerato y año activo
    public List<AsignacionHorario> obtenerHorasAsignadas(int codigoBachillerato) {
        return (List<AsignacionHorario>) horarioRepository
                .findHorarioByCodigoBachilleratoAndActivoAnioTrue(codigoBachillerato);
    }

    
    // Obtener una hora asignada por id
    public AsignacionHorario obtenerHoraAsignacionPorId(int idAsignacionHorario) {
        return horarioRepository.findById(idAsignacionHorario).orElse(null);
    }

    // Eliminar una hora asignada
    public void eliminarHoraAsignacion(int idAsignacionHorario) {
        horarioRepository.deleteById(idAsignacionHorario);
    }
}
