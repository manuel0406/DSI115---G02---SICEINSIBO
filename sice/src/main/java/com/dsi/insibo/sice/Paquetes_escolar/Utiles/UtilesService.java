package com.dsi.insibo.sice.Paquetes_escolar.Utiles;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.UtilesEscolares;

@Service
public class UtilesService {
    @Autowired
    private UtilesRepository utilesEscolaresRepository;

    public void saveUtilesEscolares(Alumno alumno, boolean fueEntregado) {
        UtilesEscolares utiles = new UtilesEscolares();
        utiles.setAlumno(alumno);
        utiles.setEntregado(fueEntregado);

        Date fechaActual = new Date();
        java.sql.Date fechaEntrega = new java.sql.Date(fechaActual.getTime());
        utiles.setFechaEntregaUtiles(fechaEntrega);

        utilesEscolaresRepository.save(utiles);
    }

}
