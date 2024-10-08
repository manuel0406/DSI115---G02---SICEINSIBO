package com.dsi.insibo.sice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.dsi.insibo.sice.Administrativo.Materias.RespositoriosMaterias.MateriasRepository;
import com.dsi.insibo.sice.entity.Materia;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional  // Asegura que las pruebas sean transaccionales y limpien después de ejecutarse
public class MateriaTest {

    @Autowired 
    private MateriasRepository materiasRepository;

    private Materia materia;

    @BeforeEach
    public void setUp() {
        // Inicializar la materia antes de cada prueba
        materia = new Materia(1001, "DSI215", "Diseño de Sistemas II", "Módulo");
    }

    @Test
    @Rollback(true) // Cambia a true para mantener el entorno limpio
    public void testGuardarMateria() {
        // Guardar la materia
        Materia savedMateria = materiasRepository.save(materia);

        // Verificar que se haya guardado correctamente
        assertThat(savedMateria).isNotNull();
        assertThat(savedMateria.getIdMateria()).isNotNull(); // Asegúrate de que el ID haya sido generado
        assertThat(savedMateria.getNomMateria()).isEqualTo("Diseño de Sistemas II");
    }

    @Test
    @Rollback(true) // Cambia a true para mantener el entorno limpio
    public void testEliminarMateria() {
        // Guardar la materia y luego eliminarla
        Materia savedMateria = materiasRepository.save(materia);
        materiasRepository.delete(savedMateria);

        // Verificar que la materia ya no exista
        assertThat(materiasRepository.findById(savedMateria.getIdMateria())).isEmpty();
    }

    @Test
    @Rollback(true)
    public void testListarMaterias() {
        // Guardar algunas materias de ejemplo
       // materiasRepository.save(new Materia(1001, "BAS101", "Materia2", "Módulo"));
       // materiasRepository.save(new Materia(1010, "DSI216", "Materia1", "Módulo"));
        
        // Listar todas las materias
        List<Materia> materias = materiasRepository.findAll();

        // Imprimir las materias en la consola
        imprimirMateria(materias);
        // Verifica que las materias se han guardado correctamente
        assertThat(materias).isNotEmpty(); // Asegúrate de que no esté vacío
    }

    public void imprimirMateria (List<Materia> materias){
        System.out.println("Listado de Materias:");
        for (Materia materia : materias) {
            System.out.println("ID: " + materia.getIdMateria() +
                            ", Código: " + materia.getCodMateria() +
                            ", Nombre: " + materia.getNomMateria() +
                            ", Tipo: " + materia.getTipoMateria());
        }
    }

    @Test
    @Rollback(true) // Cambia a true para mantener el entorno limpio
    public void testActualizarMateria() {
        // Guardar la materia
        Materia savedMateria = materiasRepository.save(materia);
        
        // Actualizar la materia
        savedMateria.setNomMateria("Diseño de Sistemas II Actualizado");
        Materia updatedMateria = materiasRepository.save(savedMateria);

        // Verificar que la materia se haya actualizado correctamente
        assertThat(updatedMateria.getNomMateria()).isEqualTo("Diseño de Sistemas II Actualizado");
    }
}
