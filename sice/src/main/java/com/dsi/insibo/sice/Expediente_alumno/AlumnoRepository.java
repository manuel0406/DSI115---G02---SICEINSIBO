package com.dsi.insibo.sice.Expediente_alumno;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.dsi.insibo.sice.entity.Alumno;


public interface  AlumnoRepository extends JpaRepository<Alumno, Integer> {
    
    @Query("SELECT a FROM Alumno a JOIN a.bachillerato b WHERE b.nombreCarrera = :carrera AND b.grado = :grado AND b.seccion = :seccion")
   public List<Alumno> findAll(String carrera, String grado, String seccion );

   List<Alumno> findByBachilleratoCodigoBachillerato(String codigoBachillerato);

}
