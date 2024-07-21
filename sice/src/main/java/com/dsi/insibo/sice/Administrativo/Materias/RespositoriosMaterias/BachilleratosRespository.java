package com.dsi.insibo.sice.Administrativo.Materias.RespositoriosMaterias;
import java.util.Set;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dsi.insibo.sice.entity.Bachillerato;

public interface BachilleratosRespository extends JpaRepository<Bachillerato, String>{

    @Query("SELECT DISTINCT b.grado FROM Bachillerato b ORDER BY b.grado ASC")
    Set<Integer> findGrados();

    @Query("SELECT DISTINCT b.nombreCarrera FROM Bachillerato b ORDER BY b.nombreCarrera ASC")
    Set<String> findTecnicos();

    @Query("SELECT DISTINCT b.seccion FROM Bachillerato b WHERE b.grado = :grado AND b.nombreCarrera = :tecnico  ORDER BY b.seccion ASC")
    Set<String> findSecciones(String tecnico, Integer grado);

    @Query("SELECT b FROM Bachillerato b WHERE b.grado = :grado ORDER BY b.nombreCarrera ASC, b.seccion ASC")
    List<Bachillerato> findPorSecciones(Integer grado);
}
