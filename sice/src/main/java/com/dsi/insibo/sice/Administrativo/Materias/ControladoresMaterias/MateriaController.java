package com.dsi.insibo.sice.Administrativo.Materias.ControladoresMaterias;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.MateriasService;
import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.BachilleratosService;
import com.dsi.insibo.sice.entity.Materia;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MateriaController {
    
    @Autowired
    private MateriasService materiasService;
    
    @Autowired
    private BachilleratosService bachilleratosService;

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/GestionMaterias")
    public String gestionarMaterias(Model model){
        List<Materia> materias = materiasService.obtenerMaterias();

        // Convertir la lista de materias a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String materiasJson = objectMapper.writeValueAsString(materias);
            model.addAttribute("materiasJson", materiasJson); // Para JS
        } catch (JsonProcessingException e) {
            System.out.println("El error es" + e);
        }

        model.addAttribute("materias", materias);
        return "Administrativo/GestionMaterias/GestionarMaterias";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardarMateria")
    public String guardarMateria(@RequestParam("nomMateria") String nombre, 
                                 @RequestParam("codMateria") String codigo,
                                 @RequestParam("tipoMateria") String tipo,
                                 RedirectAttributes redirectAttributes) {
        Materia materia = new Materia();
        materia.setCodMateria(codigo);
        materia.setNomMateria(nombre);
        materia.setTipoMateria(tipo);
        materiasService.guardarMateria(materia);
        redirectAttributes.addFlashAttribute("success", "Materia guardada exitosamente.");
        return "redirect:/GestionMaterias";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/actualizarMateria")
    public String actualizarMateria(@RequestParam("idMateria") int id,
                                    @RequestParam("codMateria") String codigo,
                                    @RequestParam("nomMateria") String nombre,
                                    @RequestParam("tipoMateria") String tipo,
                                    RedirectAttributes redirectAttributes) {
        Materia materia = materiasService.obtenerMateriaPorId(id);
        if (materia == null) {
            redirectAttributes.addFlashAttribute("error", "La materia no existe.");
            return "redirect:/GestionMaterias";
        }
        materia.setCodMateria(codigo);
        materia.setNomMateria(nombre);
        materia.setTipoMateria(tipo);
        materiasService.guardarMateria(materia);
        redirectAttributes.addFlashAttribute("success", "Materia actualizada exitosamente.");
        return "redirect:/GestionMaterias";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/EliminarMateria/{id}")
    public String eliminarMateria(@PathVariable("id") int idMateria, RedirectAttributes redirectAttributes){
        Materia materia = materiasService.obtenerMateriaPorId(idMateria);
        if (materia == null) {
            redirectAttributes.addFlashAttribute("error", "La materia no existe.");
            return "redirect:/GestionMaterias";
        }
        materiasService.eliminarMateria(materia);
        redirectAttributes.addFlashAttribute("success", "Materia eliminada exitosamente.");
        return "redirect:/GestionMaterias";
    }
}
