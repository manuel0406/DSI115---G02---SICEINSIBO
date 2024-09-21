package com.dsi.insibo.sice.Paquetes_escolar.Zapatos;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Bachillerato;
import com.dsi.insibo.sice.entity.Uniforme;
import com.dsi.insibo.sice.entity.UtilesEscolares;
import com.dsi.insibo.sice.entity.Zapatos;

@Service
public class ZapatosService {
    @Autowired
    private ZapatosRepository zapatosRepository;

    public void saveZapatos(Alumno alumno, int tallaZapato, boolean fueEntregado) {
        Zapatos zapatos = new Zapatos();
        zapatos.setAlumno(alumno);
        zapatos.setTallaZapato(tallaZapato);
        zapatos.setZapataloEntregado(fueEntregado);
        // Obtener la fecha actual
        Date fechaActual = new Date();
        java.sql.Date fechaEntrega = new java.sql.Date(fechaActual.getTime());

        zapatos.setFechaEntegaZapatos(fechaEntrega);
        zapatosRepository.save(zapatos);
    }


}
