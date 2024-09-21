package com.dsi.insibo.sice.Paquetes_escolar.Uniforme;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Uniforme;

@Service
public class UniformeService {

    @Autowired
    private UniformeRepository uniformeRepository;

    public void saveUniforme(Alumno alumno, String tallaUniforme, boolean fueEntregado) {
        Uniforme uniforme = new Uniforme();
        uniforme.setAlumno(alumno);
        uniforme.setTallaUniforme(tallaUniforme);
        uniforme.setUniformeEntegado(fueEntregado);

        // Obtener la fecha actual de tipo 
        Date fechaActual = new Date();

        java.sql.Date fechaEntrega = new java.sql.Date(fechaActual.getTime());

        uniforme.setFechaEntregaUniforme(fechaEntrega);
        uniformeRepository.save(uniforme);

    }



}
