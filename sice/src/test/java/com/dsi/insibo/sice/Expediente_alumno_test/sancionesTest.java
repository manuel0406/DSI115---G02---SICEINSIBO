package com.dsi.insibo.sice.Expediente_alumno_test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import com.dsi.insibo.sice.Expediente_alumno.AlumnoService;
import com.dsi.insibo.sice.Expediente_alumno.SancionesService;
import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Sancion;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({ AlumnoService.class, SancionesService.class })
public class sancionesTest {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private SancionesService sancionesService;

    @Test
    @Rollback(false)
    public void crearSancion() {
        int idAlumno = 30;
        // Buscar el alumno por su idAlumno
        Alumno alumno = alumnoService.buscarPorIdAlumno(idAlumno);

        String descripcionSancion = "Demasiado revelde";
        String tipoSancion = " grave";
        Date fechaSancion = new Date();
        String accionCorrectiva = "Barrer la escuela";
        Sancion sancion = new Sancion(alumno, descripcionSancion, tipoSancion, fechaSancion, accionCorrectiva);

        // No le agrego id la sancion por que se genera automaticamente
        if (alumno != null) {
            sancionesService.guardarSancion(sancion);
        } else {
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("Error: alumno invalido");
            System.out.println("--------------------------------------------------------------------------");
        }
        assertNotNull(sancion);
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Guardado con exito");
        System.out.println("--------------------------------------------------------------------------");
        System.out.println(sancion);
        System.out.println("--------------------------------------------------------------------------");

    }

    @Test
    @Rollback(false)
    public void editarSancionTest() {
        int idAlumno = 30;
        // Buscar el alumno por su idAlumno
        Alumno alumno = alumnoService.buscarPorIdAlumno(idAlumno);

        String descripcionSancion = "Se porta mal";
        String tipoSancion = " grave";
        Date fechaSancion = new Date();
        String accionCorrectiva = "Dos horas al sol en la escuela";

        Sancion sancion = new Sancion(1, alumno, descripcionSancion, tipoSancion, fechaSancion, accionCorrectiva);

        // No le agrego id la sancion por que se genera automaticamente
        if (alumno != null) {
            sancionesService.guardarSancion(sancion);
        } else {
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("Actualizado con exito");
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("Error: alumno invalido");
            System.out.println("--------------------------------------------------------------------------");
        }
        assertNotNull(sancion);
        System.out.println("--------------------------------------------------------------------------");
        System.out.println(sancion);
        System.out.println("--------------------------------------------------------------------------");

    }

    @Test
    @Rollback(false)
    public void eliminarSancionTest() {
        int id = 1;

        sancionesService.EliminarSancion(id);
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Registro eliminado con exito");
        System.out.println("--------------------------------------------------------------------------");
    }

    @Test
    @Rollback(false)
    public void listarSancionesTest() {

        Alumno alumno = null;
        int idAlumno = 30;

        if (idAlumno > 0) {
            // Busca al alumno por su número de identificación estudiantil (NIE)
            alumno = alumnoService.buscarPorIdAlumno(idAlumno);

            // Verifica que el alumno exista
            if (alumno == null) {
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Error: ¡El NIE ingresado no existe!");
                System.out.println("--------------------------------------------------------------------------");
            }
        } else {
            // Maneja el caso donde el NIE no es válido
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("Error: ¡El NIE ingresado no es válido!");
            System.out.println("--------------------------------------------------------------------------");
        }

        List<Sancion> listaSanciones = sancionesService.buscarSancionAlumno(idAlumno);
        for (Sancion sancion : listaSanciones) {
            System.out.println("--------------------------------------------------------------------------");
            System.out.println(sancion);
            System.out.println("--------------------------------------------------------------------------");

        }

    }

}
