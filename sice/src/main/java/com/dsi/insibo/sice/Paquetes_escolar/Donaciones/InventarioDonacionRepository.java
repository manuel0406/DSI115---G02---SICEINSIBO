package com.dsi.insibo.sice.Paquetes_escolar.Donaciones;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Bachillerato;
import com.dsi.insibo.sice.entity.InventarioDonacion;

@Repository
public interface InventarioDonacionRepository extends JpaRepository<InventarioDonacion, Integer> {
    // Metodo para buscar una donación por tipo de prenda y talla
    Optional<InventarioDonacion> findByTipoPrendaAndTallaPrenda(String tipoPrenda, String tallaPrenda);

    @Query("SELECT i.cantidadPrenda FROM InventarioDonacion i WHERE i.idInventarioDonacion = :donacionId")
    int findCantidadById(@Param("donacionId") int donacionId);

    @Query("SELECT DISTINCT b.grado FROM Bachillerato b WHERE b.nombreCarrera = :nombreCarrera")
    List<Integer> findDistinctGrados(@Param("nombreCarrera") String nombreCarrera);

    @Query("SELECT DISTINCT b.seccion FROM Bachillerato b WHERE b.nombreCarrera = :nombreCarrera AND b.grado = :grado")
    List<String> findDistinctSecciones(@Param("nombreCarrera") String nombreCarrera, @Param("grado") int grado);


    // Consulta para obtener los detalles del bachillerato basados en su código
    // Método para encontrar Bachillerato por código de tipo Integer
    @Query("SELECT b FROM Bachillerato b WHERE b.codigoBachillerato = :codigo")
    Bachillerato findBachilleratoByCodigo(@Param("codigo") Integer codigoBachillerato);

    @Query(value = "SELECT nie, apellido_alumno, nombre_alumno, sexo_alumno, bachillerato_codigo_bachillerato, id_alumno "
            +
            "FROM public.alumno WHERE bachillerato_codigo_bachillerato = :codigoBachillerato", nativeQuery = true)
    List<Object[]> findAlumnosByCodigoBachillerato(@Param("codigoBachillerato") Integer codigoBachillerato);


    //Obtener año academico activo
    @Query("SELECT a.idAnioAcademico FROM AnioAcademico a WHERE a.activoAnio = true")
    Integer findActiveAnioAcademicoId();

    // Opción si prefieres usar Object[] 
    @Query("SELECT b.codigoBachillerato, b.grado, b.nombreCarrera, b.seccion " +
           "FROM Bachillerato b WHERE b.anioAcademico.idAnioAcademico = :idAnioAcademico")
    List<Object[]> findBachilleratoByAnioAcademicoNoDTO(@Param("idAnioAcademico") Integer idAnioAcademico);
}