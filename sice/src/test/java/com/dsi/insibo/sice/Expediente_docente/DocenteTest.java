package com.dsi.insibo.sice.Expediente_docente;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import com.dsi.insibo.sice.Expediente_docente.Docentes.DocenteDTO;
import com.dsi.insibo.sice.Expediente_docente.Docentes.DocenteService;
import com.dsi.insibo.sice.entity.Docente;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({ DocenteService.class })
@TestMethodOrder(OrderAnnotation.class)
public class DocenteTest {

    @Autowired
    private DocenteService docenteService;

    private final String ID_VALIDO = "01389762-0";
    private final String ID_INVALIDO = "AAAA678-9"; // Esto formato de DUI no existe

    // Método auxiliar para crear un docente de ejemplo
    private Docente getSampleDocente(String id) {
        return new Docente(
                id,
                "1234-567890-123-4",
                "1234567890",
                "1234567890",
                "2222-3333",
                "Juan",
                "Pérez",
                Date.valueOf("1980-01-01"),
                "Calle Falsa 123",
                "San Salvador",
                "San Salvador",
                "Distrito 1",
                "juan.perez@example.com",
                "7777-8888",
                "Ingeniero",
                Date.valueOf("2020-01-01"),
                "Urbana",
                "Licenciado",
                "Maestría en Educación",
                true,
                true,
                Date.valueOf("2023-01-01"));
    }

    // Prueba para guardar un nuevo docente.
    @Test
    @Order(1)
    @Rollback(false)
    @DisplayName("Guardar un docente")
    public void r5_GuardarDocente() {
        // Crear un objeto de prueba
        Docente docente = getSampleDocente(ID_VALIDO);

        // Guardar el docente y verificar si es nuevo
        boolean isNuevo = docenteService.guardarDocente(docente);
        System.out.println("El docente fue creado como nuevo: " + isNuevo);

        // Verificar que el registro fue guardado y no es null
        Docente guardado = docenteService.buscarPorIdDocente(ID_VALIDO);
        assertThat(guardado).isNotNull();
        System.out.println(
                "docente guardado: " + guardado.getNombreDocente() + " " + guardado.getApellidoDocente());
    }

    // Prueba para buscar un docente por ID.
    @Test
    @Order(2)
    @DisplayName("Buscar un docente dado un ID válido")
    public void r6_BuscarPorIddocente() {
        // Buscar docente por ID válido
        Docente encontrado = docenteService.buscarPorIdDocente(ID_VALIDO);

        // Verificar que el docente fue encontrado y validar el nombre
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getNombreDocente()).isEqualTo("Juan");
        System.out.println("Docente encontrado: " + encontrado.getNombreDocente() + " " + encontrado.getApellidoDocente());
    }

    // Prueba para buscar un docente con un ID no existente.
    @Test
    @Order(3)
    @DisplayName("Buscar un docente dado un ID no existente")
    public void r6_BuscarPorIddocenteNoExistente() {
        // Intentar buscar docente con ID inválido
        Docente encontrado = docenteService.buscarPorIdDocente(ID_INVALIDO);

        // Verificar que no se encuentra ningún docente
        assertThat(encontrado).isNull();
        System.out.println("Resultado esperado, no se encontró docente con ID: " + ID_INVALIDO);
    }

    // Prueba para listar todos los docentes.
    @Test
    @Order(4)
    @DisplayName("Listar todos los docentes")
    public void r6_ListarLosDocentes() {
        // Listar todos los docentes
        List<DocenteDTO> docentes = docenteService.listarDocentes();

        // Verificar que la lista no esté vacía
        assertThat(docentes).isNotEmpty();
        System.out.println("Número de docentes encontrados: " + docentes.size());
    }

    // Prueba para actualizar un docente existente.
    @Test
    @Order(5)
    @Rollback(false)
    @DisplayName("Actualizar un docente existente")
    public void r7_ActualizarDocente() {
        // Buscar un docente existente
        Docente personal = docenteService.buscarPorIdDocente(ID_VALIDO);
        assertThat(personal).isNotNull();

        // Actualizar el nombre del docente
        personal.setNombreDocente("Carlos");
        boolean isNuevo = docenteService.guardarDocente(personal);
        System.out.println("El docente fue creado como nuevo: " + isNuevo);


        // Verificar que no es un nuevo registro, sino una actualización
        assertThat(isNuevo).isFalse();
        System.out.println("El docente ha sido actualizado: " + personal.getNombreDocente());

        // Verificar que el nombre fue actualizado correctamente
        Docente actualizado = docenteService.buscarPorIdDocente(ID_VALIDO);
        assertThat(actualizado.getNombreDocente()).isEqualTo("Carlos");
        System.out.println("Nombre actualizado a: " + actualizado.getNombreDocente());
    }

    // Prueba para eliminar un docente existente.
    @Test
    @Order(6)
    @Rollback(false)
    @DisplayName("Eliminar un docente existente")
    public void r8_EliminarDocente() {
        // Buscar docente antes de eliminar
        Docente encontrado = docenteService.buscarPorIdDocente(ID_VALIDO);
        assertThat(encontrado).isNotNull();

        // Eliminar docente
        docenteService.eliminar(ID_VALIDO);
        System.out.println("El docente con ID: " + ID_VALIDO + " ha sido eliminado.");

        // Verificar que el docente ya no existe
        encontrado = docenteService.buscarPorIdDocente(ID_VALIDO);
        assertThat(encontrado).isNull();
        System.out.println("El docente ya no está presente en la base de datos.");
    }

    // Prueba para intentar eliminar un docente que no existe.
    @Test
    @Order(7)
    @DisplayName("Eliminar un docente no existente")
    @Rollback(false)
    public void r8_EliminarDocenteNoExistente() {
        // Intentar eliminar un docente con un ID no existente
        assertThatCode(() -> docenteService.eliminar(ID_INVALIDO))
                .doesNotThrowAnyException();
        System.out.println("Intento de eliminar docente con ID inexistente no generó excepciones.");
    }
}
