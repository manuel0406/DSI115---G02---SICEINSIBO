package com.dsi.insibo.sice.Administrativo.Bachilleratos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dsi.insibo.sice.entity.AnioAcademico;

@Repository
public interface AnioRepository extends JpaRepository<AnioAcademico, Integer> {
    
    @Query("SELECT a FROM AnioAcademico a WHERE a.anio = :anio")
    AnioAcademico buscarAnio(@Param("anio") int anio);
}
