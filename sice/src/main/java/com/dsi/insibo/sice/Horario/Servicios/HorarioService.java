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

    // Asignaciones en general, cambiar despues a asignaciones por seccion
    public List<AsignacionHorario> obtenerHorasAsignadas() {
        return (List<AsignacionHorario>) horarioRepository.findAll();
    }

    // Guardar una asignacion
    public void guardarHoraAsignacion(AsignacionHorario asignacionHorario) {
        horarioRepository.save(asignacionHorario);
    }

    // Obtener una hora asignada por id
    public AsignacionHorario obtenerHoraAsignacionPorId(int idAsignacionHorario) {
        return horarioRepository.findById(idAsignacionHorario).orElse(null);
    }

    // Eliminar una hora asignada
    public void eliminarHoraAsignacion(AsignacionHorario asignacionHorario) {
        horarioRepository.delete(asignacionHorario);
    }

    // Método para obtener los idHorarioBase dado un codigoBachillerato y que activoAnio sea true
    public List<Integer> obtenerIdHorariosBasePorCodigoBachillerato(int codigoBachillerato) {
        return horarioRepository.findIdHorarioBaseByCodigoBachilleratoAndActivoAnioTrue(codigoBachillerato);
    }
}
