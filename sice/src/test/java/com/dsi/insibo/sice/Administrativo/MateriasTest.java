package com.dsi.insibo.sice.Administrativo;

import static org.assertj.core.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.MateriasService;
import com.dsi.insibo.sice.entity.Materia;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({ MateriasService.class })
@TestMethodOrder(OrderAnnotation.class)
public class MateriasTest {

    @Autowired
    private MateriasService materiasService;

    private final int ID_VALIDO = 32;
    private final int ID_INVALIDO = 999;

    // Método auxiliar para crear una materia de ejemplo
    private Materia getSampleMateria(int id) {
        return new Materia(
                id,
                "QMC",
                "Lenguaje y literatura",
                "Módulo");
    }

    // Prueba para guardar una materia
    @Test
    @Order(1)
    @Rollback(false)
    @DisplayName("Guardar una materia")
    public void r9_guardarMateria() {
        Materia materia = getSampleMateria(ID_VALIDO);

        // Guardar la materia
        materiasService.guardarMateria(materia);

        // Verificar que el registro fue guardado y no es null
        Materia guardada = materiasService.obtenerMateriaPorId(ID_VALIDO);
        assertThat(guardada).isNotNull();
        System.out.println("Materia guardada: " + guardada.getNomMateria());
    }

    // 2. Prueba para buscar una materia por ID
    @Test
    @Order(2)
    @DisplayName("Buscar materia por ID válido")
    public void r10_buscarMateriaPorId() {
        // Buscar materia por ID válido
        Materia materia = materiasService.obtenerMateriaPorId(ID_VALIDO);

        // Verificar que la materia fue encontrada y validar el nombre
        assertThat(materia).isNotNull();
        assertThat(materia.getNomMateria()).isEqualTo("Lenguaje y literatura");
        System.out.println("Materia encontrada: " + materia.getNomMateria());
    }

    // Prueba para buscar una materia con ID no existente
    @Test
    @Order(3)
    @DisplayName("Buscar materia por ID no existente")
    public void r10_buscarMateriaPorIdNoExistente() {
        // Intentar buscar materia con ID inválido
        Materia materia = materiasService.obtenerMateriaPorId(ID_INVALIDO);

        // Verificar que no se encuentra ningúna materia
        assertThat(materia).isNull();
        System.out.println("No se encontró materia con el ID: " + ID_INVALIDO);
    }

    // Prueba para listar todas las materias
    @Test
    @Order(4)
    @DisplayName("Listar todas las materias")
    public void r10_listarLasMaterias() {
        // Listar todos las materias
        List<Materia> materias = materiasService.obtenerMaterias();

        // Verificar que la lista no esté vacía
        assertThat(materias).isNotEmpty();
        System.out.println("Número de materias encontradas: " + materias.size());
    }

    // Prueba para actualizar una materia existente
    @Test
    @Order(5)
    @Rollback(false)
    @DisplayName("Actualizar una materia existente")
    public void r11_actualizarMateria() {
        // Buscar una materia existente
        Materia materia = materiasService.obtenerMateriaPorId(ID_VALIDO);
        assertThat(materia).isNotNull();

        // Actualizar nombre
        materia.setNomMateria("Lengua y Literatura");
        materiasService.guardarMateria(materia);

        // Verificar que el nombre fue actualizado correctamente
        Materia actualizada = materiasService.obtenerMateriaPorId(ID_VALIDO);
        assertThat(actualizada.getNomMateria()).isEqualTo("Lengua y Literatura");
        System.out.println("Materia actualizada: " + actualizada.getNomMateria());
    }

    // Prueba para eliminar una materia existente
    @Test
    @Order(6)
    @Rollback(false)
    @DisplayName("Eliminar una materia existente")
    public void r12_eliminarMateria() {
        // Buscar materia antes de eliminar
        Materia materia = materiasService.obtenerMateriaPorId(ID_VALIDO);
        assertThat(materia).isNotNull();

        // Eliminar materia
        materiasService.eliminarMateria(materia);
        System.out.println("Materia eliminada con ID: " + ID_VALIDO);

        // Verificar que el administrativo ya no existe
        Materia eliminada = materiasService.obtenerMateriaPorId(ID_VALIDO);
        assertThat(eliminada).isNull();
        System.out.println("La materia ya no está presente en la base de datos.");
    }

    // Prueba para eliminar una materia que no existe
    @Test
    @Order(7)
    @DisplayName("Eliminar una materia no existente")
    @Rollback(false)
    public void eliminarMateriaNoExistente() {
        // Intentar eliminar una materia con un ID no existente
        Materia materia = new Materia(ID_INVALIDO, "ABC", "Materia No Existente", "Avanzada");
        assertThatCode(() -> materiasService.eliminarMateria(materia))
                .doesNotThrowAnyException();
        System.out.println("Intento de eliminar materia inexistente no generó excepciones.");
    }
}
