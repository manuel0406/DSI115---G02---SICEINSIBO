package com.dsi.insibo.sice.Administrativo.Bachilleratos.Repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dsi.insibo.sice.entity.Bachillerato;
@Repository
public interface BachilleratoRepository extends JpaRepository<Bachillerato, Integer> {    

    @Query("SELECT b FROM Bachillerato b WHERE b.anioAcademico.idAnioAcademico=:idAnioAcademico ORDER BY b.grado, b.nombreCarrera, b.seccion")
    List<Bachillerato> ofertaPorAnio(int idAnioAcademico);

    @Query("SELECT b  FROM Bachillerato b WHERE b.nombreCarrera=:carrera AND b.grado=:grado AND b.seccion=:seccion AND b.anioAcademico.idAnioAcademico=:idAnioAcademico")
    Bachillerato existe(String carrera, int grado, String seccion, int idAnioAcademico);

    // OBTENER UNA SECCION EN BASE AL FILTRO CARRERA, GRADO Y AÑO
    @Query("SELECT b FROM Bachillerato b WHERE b.nombreCarrera = :nombreCarrera AND b.grado = :grado AND b.seccion = :seccion AND b.anioAcademico.activoAnio = true")
    Optional<Bachillerato> findByNombreCarreraAndGradoAndSeccion(String nombreCarrera, int grado, String seccion);

    @Query("SELECT b FROM Bachillerato b WHERE b.nombreCarrera = :carrera AND b.grado = :grado AND b.seccion = :seccion AND b.anioAcademico.activoAnio = true")
    Bachillerato especialidad(String carrera, String seccion, String grado);

    @Query("SELECT b FROM Bachillerato b WHERE b.anioAcademico.activoAnio=true ")
    List<Bachillerato> findAll();

    @Query("SELECT b.seccion FROM Bachillerato b WHERE b.nombreCarrera = :carrera AND b.grado = :grado AND b.anioAcademico.activoAnio=true")
    List<String> findSeccionesByCarrera(@Param("carrera") String carrera, @Param("grado") String grado);

    
    @Query("SELECT b FROM Bachillerato b WHERE b.nombreCarrera = :carrera AND b.grado = :grado AND b.seccion = :seccion AND b.anioAcademico.activoMatricula = true")
    Bachillerato especialidadMatricula(String carrera, String seccion, String grado);

}
