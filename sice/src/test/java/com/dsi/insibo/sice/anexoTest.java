package com.dsi.insibo.sice;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.dsi.insibo.sice.Expediente_docente.Docentes.Anexos.AnexoDocenteRepository;
import com.dsi.insibo.sice.entity.AnexoDocente;
import com.dsi.insibo.sice.entity.Docente;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Usa la base de datos real configurada en application.properties
public class anexoTest {

    @Autowired
    private AnexoDocenteRepository anexoDocenteRepository;

    private Docente docente;
    private AnexoDocente anexoDocente;

    @BeforeEach
    public void setUp() {
        // Inicializar un Docente antes de cada prueba
        docente = new Docente();
        docente.setDuiDocente("12345678-9");
        // Aquí puedes establecer otros atributos necesarios del Docente
        // Por ejemplo: docente.setNombre("Juan"); y otros

        // Guardar el Docente para que esté disponible para el AnexoDocente
        // Usar un repositorio para guardar el docente, si lo tienes
        // docenteRepository.save(docente);
    }

    @Test
    @Rollback(true) // Mantiene el entorno limpio después de la prueba
    public void testGuardarYRecuperarAnexoDocente() throws IOException {
        // Cargar el archivo PDF desde la misma carpeta donde está este archivo de prueba
        byte[] datosArchivo = Files.readAllBytes(Paths.get("src/test/resources/CV.pdf"));
    
        // Crear y guardar el AnexoDocente
        anexoDocente = new AnexoDocente();
        anexoDocente.setDocente(docente);
        anexoDocente.setNombreCurriculumDocente("CV.pdf");
        anexoDocente.setDatosCurriculumDocente(datosArchivo);
        anexoDocente.setFechaCurriculumDocente(new Date());
    
        anexoDocenteRepository.save(anexoDocente);
    
        // Mensaje de confirmación
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Anexo guardado con éxito: " + anexoDocente.getNombreCurriculumDocente());
        System.out.println("--------------------------------------------------------------------------");

        // Recuperar el AnexoDocente por el DUI del Docente
        AnexoDocente foundAnexo = anexoDocenteRepository.findByDocenteDui("12345678-9");
    
        // Verificar que se encontró el AnexoDocente y que los datos son correctos
        assertThat(foundAnexo).isNotNull();
        assertThat(foundAnexo.getNombreCurriculumDocente()).isEqualTo("CV.pdf");
        assertThat(foundAnexo.getDatosCurriculumDocente()).isEqualTo(datosArchivo); // Verifica que el contenido sea igual
    }
        

    @Test
    @Rollback(true) // Mantiene el entorno limpio después de la prueba
    public void testEliminarAnexoDocente() {
        // Guardar un AnexoDocente previamente para poder eliminarlo
        anexoDocente = new AnexoDocente();
        anexoDocente.setDocente(docente);
        anexoDocente.setNombreCurriculumDocente("CV.pdf");
        anexoDocente.setDatosCurriculumDocente(new byte[0]); // Simular datos
        anexoDocente.setFechaCurriculumDocente(new Date());

        anexoDocenteRepository.save(anexoDocente);

        // Mensaje de confirmación de guardado
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Anexo guardado con éxito para su posterior eliminación.");
        System.out.println("--------------------------------------------------------------------------");

        // Ahora eliminar el AnexoDocente por el DUI del Docente
        anexoDocenteRepository.deleteByDocenteDui("12345678-9");

        // Verificar que ya no existe
        AnexoDocente foundAnexo = anexoDocenteRepository.findByDocenteDui("12345678-9");
        assertThat(foundAnexo).isNull();

        // Mensaje de confirmación de eliminación
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Anexo eliminado con éxito.");
        System.out.println("--------------------------------------------------------------------------");

    }

}

