package com.dsi.insibo.sice.Calificaciones;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Materia;
import com.dsi.insibo.sice.entity.NotaPeriodo;

@Service
public class NotaPeriodoService {

    @Autowired
    private NotaPeriodoRepository notaPeriodoRepository;

    public void guardarNotaPeriodo(NotaPeriodo notaPeriodo) {
        notaPeriodoRepository.save(notaPeriodo);
    }

    public List<NotaPeriodo> listaNotasPeriodo() {
        return notaPeriodoRepository.findAll();
    }

    public void eliminarNotaPeriodo(int idNotaPeriodo) {
        notaPeriodoRepository.deleteById(idNotaPeriodo);
    }

    public NotaPeriodo notaPeriodoId(int idNotaPeriodo) {

        return notaPeriodoRepository.findById(idNotaPeriodo).orElse(null);
    }

    public NotaPeriodo notaPeriodoAlumno(int idAlumno, String periodo, int IdMateria, int codigoBachillerato,
            String duiDocente) {
        return notaPeriodoRepository.notaPeriodoAlumno(idAlumno, periodo, IdMateria, codigoBachillerato, duiDocente)
                .orElse(null);
    }

    public List<NotaPeriodo> notaPorPeriodoAlumno(int idAlumno, int idMateria, int codigoBachillerato) {
        return notaPeriodoRepository.notaPeriodoAlumnos(idAlumno, idMateria, codigoBachillerato);
    }

    public Optional<NotaPeriodo> obtenerNotaPeriodo(Alumno alumno, int numeroPeriodo, Materia materia) {
        return notaPeriodoRepository.findByAlumnoAndPeriodoNumeroPeriodoAndAsignacionMateria(alumno, numeroPeriodo,
                materia);

    }
    public List<NotaPeriodo> listadoNotasPeriodoMateria(int idMateria, int codigoBachillerato, String duiDocente){
        return notaPeriodoRepository.notaPeriodoMateria(idMateria, codigoBachillerato, duiDocente);
    }

}
