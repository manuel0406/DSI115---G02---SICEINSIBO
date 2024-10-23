package com.dsi.insibo.sice.Expediente_docente;

import static org.assertj.core.api.Assertions.*;
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
import com.dsi.insibo.sice.Expediente_docente.Administrativos.AdministrativoDTO;
import com.dsi.insibo.sice.Expediente_docente.Administrativos.AdministrativoService;
import com.dsi.insibo.sice.entity.PersonalAdministrativo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({ AdministrativoService.class })
@TestMethodOrder(OrderAnnotation.class)
public class AdministrativoTest {

    @Autowired
    private AdministrativoService administrativoService;

    private final String DUI_VALIDO = "32145678-9";
    private final String DUI_INVALIDO = "AAAA678-9";

    // Método auxiliar para crear un empleado administrativo de ejemplo
    private PersonalAdministrativo getSampleAdministrativo(String id) {
        return new PersonalAdministrativo(
            id, 
            "Juan", 
            "Pérez", 
            "555-1234", 
            "Ingeniero", 
            Date.valueOf("1990-01-01"),
            "Maestría", 
            "juan.perez@example.com", 
            "San Salvador", 
            "San Salvador", 
            "Distrito 1", 
            "Calle Falsa 123", 
            "Urbana", 
            "0614-290190-102-3", 
            "123456789", 
            "2222-3333", 
            "Sistemas", 
            true, true, 
            Date.valueOf("2023-01-01"), 
            Date.valueOf("2016-01-01"));
    }

    // Prueba para guardar un nuevo administrativo.
    @Test
    @Order(1)
    @Rollback(false)
    @DisplayName("Guardar un administrativo")
    public void r1_GuardarAdministrativo() {
        PersonalAdministrativo administrativo = getSampleAdministrativo(DUI_VALIDO);

        // Guardar el administrativo y verificar si es nuevo
        boolean isNuevo = administrativoService.guardarAdministrativo(administrativo);
        System.out.println("El administrativo fue creado como nuevo: " + isNuevo);

        // Verificar que el registro fue guardado y no es null
        PersonalAdministrativo guardado = administrativoService.buscarPorIdAdministrativo(DUI_VALIDO);
        assertThat(guardado).isNotNull();
        System.out.println("Administrativo guardado: " + guardado.getNombrePersonal() + " " + guardado.getApellidoPersonal());
    }

    // Prueba para buscar un administrativo por ID.
    @Test
    @Order(2)
    @DisplayName("Buscar un administrativo dado un ID válido")
    public void r2_BuscarPorIdAdministrativo() {
        // Buscar administrativo por ID válido
        PersonalAdministrativo encontrado = administrativoService.buscarPorIdAdministrativo(DUI_VALIDO);

        // Verificar que el administrativo fue encontrado y validar el nombre
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getNombrePersonal()).isEqualTo("Juan");
        System.out.println("Administrativo encontrado: " + encontrado.getNombrePersonal() + " " + encontrado.getApellidoPersonal());
    }

    // Prueba para buscar un administrativo con un ID no existente.
    @Test
    @Order(3)
    @DisplayName("Buscar un administrativo dado un ID no existente")
    public void r2_BuscarPorIdAdministrativoNoExistente() {
        // Intentar buscar administrativo con ID inválido
        PersonalAdministrativo encontrado = administrativoService.buscarPorIdAdministrativo(DUI_INVALIDO);

        // Verificar que no se encuentra ningún administrativo
        assertThat(encontrado).isNull();
        System.out.println("Resultado esperado, no se encontró administrativo con ID: " + DUI_INVALIDO);
    }

    // Prueba para listar todos los administrativos.
     
    @Test
    @Order(4)
    @DisplayName("Listar todos los administrativos")
    public void r2_ListarLosAdministrativos() {
        // Listar todos los administrativos
        List<AdministrativoDTO> administrativos = administrativoService.listarAdministrativos();

        // Verificar que la lista no esté vacía
        assertThat(administrativos).isNotEmpty();
        System.out.println("Número de administrativos encontrados: " + administrativos.size());
    }

    // Prueba para actualizar un administrativo existente.
    @Test
    @Order(5)
    @Rollback(false)
    @DisplayName("Actualizar un administrativo existente")
    public void r3_ActualizarAdministrativo() {
        // Buscar un administrativo existente
        PersonalAdministrativo personal = administrativoService.buscarPorIdAdministrativo(DUI_VALIDO);
        assertThat(personal).isNotNull();

        // Actualizar el nombre del administrativo
        personal.setNombrePersonal("Carlos");
        boolean isNuevo = administrativoService.guardarAdministrativo(personal);

        // Verificar que no es un nuevo registro, sino una actualización
        assertThat(isNuevo).isFalse();  
        System.out.println("El administrativo ha sido actualizado: " + personal.getNombrePersonal());

        // Verificar que el nombre fue actualizado correctamente
        PersonalAdministrativo actualizado = administrativoService.buscarPorIdAdministrativo(DUI_VALIDO);
        assertThat(actualizado.getNombrePersonal()).isEqualTo("Carlos");
        System.out.println("Nombre actualizado a: " + actualizado.getNombrePersonal());
    }

    // Prueba para eliminar un administrativo existente.
    @Test
    @Order(6)
    @Rollback(false)
    @DisplayName("Eliminar un administrativo existente")
    public void r4_EliminarAdministrativo() {
        // Buscar administrativo antes de eliminar
        PersonalAdministrativo encontrado = administrativoService.buscarPorIdAdministrativo(DUI_VALIDO);
        assertThat(encontrado).isNotNull();

        // Eliminar administrativo
        administrativoService.eliminar(DUI_VALIDO);
        System.out.println("El administrativo con ID: " + DUI_VALIDO + " ha sido eliminado.");

        // Verificar que el administrativo ya no existe
        encontrado = administrativoService.buscarPorIdAdministrativo(DUI_VALIDO);
        assertThat(encontrado).isNull();
        System.out.println("El administrativo ya no está presente en la base de datos.");
    }
}
