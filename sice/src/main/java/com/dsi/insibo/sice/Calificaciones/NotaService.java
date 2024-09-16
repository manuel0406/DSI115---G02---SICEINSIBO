package com.dsi.insibo.sice.Calificaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dsi.insibo.sice.entity.Nota;
import jakarta.transaction.Transactional;

@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;
    
    @Transactional
    public void deleteNotasByAlumnoNie(int nie) {
        notaRepository.deleteByAlumnoNie(nie);
    }
    
    public void guardarNota(Nota nota){
        notaRepository.save(nota);
    }

    public List<Nota> notasPorBachilleratoActivdad(int idActividad){
        return notaRepository.notasActividad(idActividad);
    }
}
 