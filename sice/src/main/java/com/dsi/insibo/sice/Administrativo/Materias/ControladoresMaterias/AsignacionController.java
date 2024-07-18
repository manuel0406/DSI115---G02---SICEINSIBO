package com.dsi.insibo.sice.Administrativo.Materias.ControladoresMaterias;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.MateriasService;
import com.dsi.insibo.sice.entity.Materia;

@Controller
public class AsignacionController {

    // Materias
    @Autowired
    private MateriasService materiasService;

    @GetMapping("/AsignacionMateria/")
    public String fallaEnlace(){
        return "redirect:/GestionMaterias";
    }

    @GetMapping("/AsignacionMateria/{id}")
    public String asignacionMateria(Model model,  @PathVariable("id") int idMateria, RedirectAttributes attribute){
        
        // ID INVALIDA
        if (idMateria == 0) {
            return "redirect:/GestionMaterias";
        }
        
        Materia materia = materiasService.obtenerMateriaPorId(idMateria);
        model.addAttribute("materia", materia);
        return "Administrativo/GestionMaterias/AsignacionMateria";
    }

    @GetMapping("/NuevaAsignacion/{id}")
    public String nuevaAsignacion(){
        return "Administrativo/GestionMaterias/NuevaAsignacion";
    }
}
