package com.dsi.insibo.sice.Expediente_docente.Docentes;

import java.time.LocalDate;
import java.time.Period;
import com.dsi.insibo.sice.entity.Docente;

public class DocenteDTO {
    private Docente docente;
    private int edad;

    public DocenteDTO(Docente docente) {
        this.docente = docente;
        // Verificar si la fecha de nacimiento es nula o incorrecta
        if (docente.getFechaNacimientoD() == null || docente.getFechaNacimientoD().isAfter(LocalDate.now())) {
            this.edad = 0;
        } else {
            // Calcular la edad normalmente si la fecha de nacimiento es válida
            this.edad = Period.between(docente.getFechaNacimientoD(), LocalDate.now()).getYears();
        }
    }

    public Docente getDocente() {
        return docente;
    }

    public int getEdad() {
        return edad;
    }
}
