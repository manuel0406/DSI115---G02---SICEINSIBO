package com.dsi.insibo.sice.Administrativo.Bachilleratos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dsi.insibo.sice.entity.Bachillerato;

@Repository
public interface BachilleratoRepository extends JpaRepository<Bachillerato, Integer> {

    // OBTENER UNA SECCION EN BASE AL FILTRO CARRERA, GRADO Y AÑO
    @Query("SELECT b FROM Bachillerato b WHERE b.nombreCarrera = :nombreCarrera AND b.grado = :grado AND b.seccion = :seccion AND b.anioAcademico.activoAnio = true")
    Optional<Bachillerato> findByNombreCarreraAndGradoAndSeccion(String nombreCarrera, int grado, String seccion);
}
