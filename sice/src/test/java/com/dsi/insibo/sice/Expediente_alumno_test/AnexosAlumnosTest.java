package com.dsi.insibo.sice.Expediente_alumno_test;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import com.dsi.insibo.sice.Expediente_alumno.AlumnoService;
import com.dsi.insibo.sice.Expediente_alumno.AnexoAlumnoRepository;
import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.AnexoAlumno;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(AlumnoService.class)
public class AnexosAlumnosTest {

    @Autowired
    private AnexoAlumnoRepository anexoAlumnoRepository;
    @Autowired
    private AlumnoService alumnoService;

    @Test
    @Rollback(false)
    public void guardarAnexoAlumnoTest() throws IOException {
        // Cargar el archivo pdf de pruebas
        byte[] datosArchivo = Files.readAllBytes(Paths.get("src/test/resource/prueba.pdf"));

        Alumno alumno = alumnoService.buscarPorIdAlumno(30);
        AnexoAlumno anexoAlumno = new AnexoAlumno();
        anexoAlumno.setAlumno(alumno);
        anexoAlumno.setDatosDui(datosArchivo);
        anexoAlumno.setNombreDui("prueba.pdf");

        anexoAlumnoRepository.save(anexoAlumno);

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Anexo guardado con éxito: " + anexoAlumno.getNombreDui());
        System.out.println("--------------------------------------------------------------------------");

        // Recuperar el Anexo por el idAlumno
        AnexoAlumno anexoRecuperado = anexoAlumnoRepository.findByAlumnoNie(30);

        assertThat(anexoRecuperado).isNotNull();
        assertThat(anexoRecuperado.getNombreDui()).isEqualTo("prueba.pdf");
        assertThat(anexoRecuperado.getDatosDui()).isEqualTo(datosArchivo);
    }

    @Test
    @Rollback(true)
    public void eliminarAnexoAlumnoTest(){

        int idAlumno=30;
        AnexoAlumno anexoAlumno= anexoAlumnoRepository.findByAlumnoNie(idAlumno);
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Anexo de: "+anexoAlumno.getAlumno().getNombreAlumno());
        System.out.println("--------------------------------------------------------------------------");

        anexoAlumnoRepository.deleteById(anexoAlumno.getIdAnexoAlumno());
        // Mensaje de confirmación de eliminación
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Anexo eliminado con éxito.");
        System.out.println("--------------------------------------------------------------------------");

    }
}
