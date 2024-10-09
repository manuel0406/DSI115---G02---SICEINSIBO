package com.dsi.insibo.sice.Expediente_alumno_test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import com.dsi.insibo.sice.Administrativo.Bachilleratos.Servicios.BachilleratoService;
import com.dsi.insibo.sice.Expediente_alumno.AlumnoService;
import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Bachillerato;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({ AlumnoService.class, BachilleratoService.class })
public class AlumnosTest {

    @Autowired
    AlumnoService alumnoService;
    @Autowired
    BachilleratoService bachilleratoService;

    @Test
    @Rollback(false)
    public void guardarAlumnoTest() {
        String carrera = "Atención Primaria en Salud", grado = "1", seccion = "B";
        Bachillerato bachillerato = bachilleratoService.debolverBachilleratoMatricula(carrera, seccion, grado);

        Alumno alumno = new Alumno();
        alumno.setApellidoAlumno("Aleman Orellana");
        alumno.setNombreAlumno("Pedro Hugo");
        alumno.setNie(1234567);

        if (bachillerato != null) {
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("bachillerato no nulo");
            System.out.println("--------------------------------------------------------------------------");
            alumno.setBachillerato(bachillerato);
        }

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Guardado con exito");
        System.out.println("--------------------------------------------------------------------------");
        alumnoService.guardarAlumno(alumno);
        assertNotNull(alumno);

    }

    @Test
    @Rollback(false)
    public void actualizarAlumnoTest() {

        Alumno alumno = new Alumno();
        alumno.setIdAlumno(37);
        alumno.setApellidoAlumno("Aleman Orellana");
        alumno.setNombreAlumno("Pedro");
        alumno.setNie(1234567);
        Bachillerato bachillerato = bachilleratoService.debolverBachillerato("Atención Primaria en Salud", "A", "1");

        if (bachillerato != null) {
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("bachillerato no nulo");
            System.out.println("--------------------------------------------------------------------------");
            alumno.setBachillerato(bachillerato);
        }
        // Guarda el alumno con la información actualizada
        alumnoService.guardarAlumno(alumno);
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Registro Guardado con exito.");
        System.out.println("--------------------------------------------------------------------------");

    }

    @Test
    public void listarAlumnosTest() {

        String carrera = "Administrativo Contable", grado = null, seccion = null, genero = null;

        // Obtener la lista completa de alumnos filtrada por los parámetros
        List<Alumno> listaAlumnos = alumnoService.listarAlumnos(carrera, grado, seccion, genero);
        // Ordenar la lista por "apellidoAlumno"
        listaAlumnos.sort(Comparator.comparing(Alumno::getApellidoAlumno));

        // Obtener la lista de carreras (bachilleratos)
        List<Bachillerato> listaCarreras = bachilleratoService.listaCarrera(false);

        System.out.println("--------------------------------------------------------------------------");
        for (Alumno alumnos : listaAlumnos) {
            System.out.println("Alumnos: [ id: " + alumnos.getIdAlumno() + " -- " + alumnos.getNombreAlumno() + " -- "
                    + alumnos.getApellidoAlumno() + " ]");
        }
        System.out.println("--------------------------------------------------------------------------");
        for (Bachillerato bachillerato : listaCarreras) {
            System.out.println(bachillerato);
        }
        System.out.println("--------------------------------------------------------------------------");
    }

    @Test
    @Rollback(false)
    public void eliminarAlumnoTest() {
        int idAlumno = 10;
        Alumno alumno = null;
        if (idAlumno > 0) {
            // Busca al alumno por su número de identificación estudiantil (NIE)
            alumno = alumnoService.buscarPorIdAlumno(idAlumno);

            // Verifica que el alumno exista
            if (alumno == null) {
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Error: ¡El idAlumno ingresado no existe!");
                System.out.println("--------------------------------------------------------------------------");

            } else {
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Exito se elimino");
                System.out.println("--------------------------------------------------------------------------");
                alumnoService.eliminar(idAlumno);
            }
        } else {
            // Maneja el caso donde el NIE no es válido
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("Error: ¡El NIE ingresado no es válido!");
            System.out.println("--------------------------------------------------------------------------");
        }

    }
}
