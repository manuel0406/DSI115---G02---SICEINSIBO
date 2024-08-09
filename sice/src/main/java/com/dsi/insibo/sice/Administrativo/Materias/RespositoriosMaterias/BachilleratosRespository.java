package com.dsi.insibo.sice.Administrativo.Materias.RespositoriosMaterias;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dsi.insibo.sice.entity.Bachillerato;

public interface BachilleratosRespository extends JpaRepository<Bachillerato, Integer>{
    // OBTIENE DE MANERA APARTE LOS PRIMEROS, SEGUNDOS Y TERCERO AÑOS CONSIDERANDO QUE ESTEN ACTIVOS
    @Query("SELECT b FROM Bachillerato b "
        + "JOIN b.anioAcademico a "   
        + "WHERE b.grado = :grado AND a.activoAnio = true "
        + "ORDER BY b.nombreCarrera ASC, b.seccion ASC")
    List<Bachillerato> findPorSecciones(Integer grado);
}
