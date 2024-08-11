package com.dsi.insibo.sice.Administrativo.Bachilleratos.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dsi.insibo.sice.entity.Bachillerato;
@Repository
public interface BachilleratoRepository extends JpaRepository<Bachillerato, Integer> {    

    @Query("SELECT b FROM Bachillerato b WHERE b.anioAcademico.idAnioAcademico=:idAnioAcademico ORDER BY b.grado, b.nombreCarrera, b.seccion")
    List<Bachillerato> ofertaPorAnio(int idAnioAcademico);

    @Query("SELECT b  FROM Bachillerato b WHERE b.nombreCarrera=:carrera AND b.grado=:grado AND b.seccion=:seccion AND b.anioAcademico.idAnioAcademico=:idAnioAcademico")
    Bachillerato existe(String carrera, int grado, String seccion, int idAnioAcademico);
}
