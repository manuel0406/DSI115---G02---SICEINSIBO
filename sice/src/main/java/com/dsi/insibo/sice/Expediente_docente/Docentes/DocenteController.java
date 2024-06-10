package com.dsi.insibo.sice.Expediente_docente.Docentes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.dsi.insibo.sice.entity.Docente;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DocenteController {
    
    // Direccionadores estaticos
    @GetMapping("/anexosDocente")
    public String docentes() {
        return "Expediente_docente/Docentes/anexosDocente";
    }

    @GetMapping("/barra")
    public String barra() {
        return "Expediente_docente/barra";
    }

    // Direccionadores de acción

    // Creando docente
    @GetMapping("/fichaDocente")
    public String docentes(Model model) {
        Docente profesor = new Docente();

        model.addAttribute("profesor", profesor);
        return "Expediente_docente/Docentes/fichaDocente";
    }

    //guardando
    @PostMapping("/guardar")
    public String guardar(@Validated @ModelAttribute Docente docente, BindingResult result, Model model, RedirectAttributes attribute) {
    
        if(result.hasErrors()){
            model.addAttribute("profesor", docente);
            System.out.println("Se tienen errores en el formulario");
            return "Expediente_docente/Docentes/fichaDocente";
        }
    
        boolean creado = docenteService.guardarDocente(docente);
    
        if (creado) {
            attribute.addFlashAttribute("success", "Expediente creado con exito!");
        } else {
            attribute.addFlashAttribute("success", "Expediente actualizado con exito!");
        }
    
        return "redirect:/personalDocente";
    }
    

    // Lista docentes usando la DB
    @Autowired
    private DocenteService docenteService;

    @GetMapping("/personalDocente")
    public String listarDocentes(Model model) {
        List<DocenteDTO> listadoDocentes = docenteService.listarDocentes();

        model.addAttribute("titulo", "Listado de docentes");
        model.addAttribute("Docentes", listadoDocentes);

        return "Expediente_docente/Docentes/listarDocentes";
    }

    // Editar ficha
    // Editando docente
    @GetMapping("/editfichaDocente/{id}")
    public String editarDocente(@PathVariable("id") String idDocente, Model model, RedirectAttributes attribute) {
        Docente profesor = docenteService.buscarPorIdDocente(idDocente);
        if(profesor == null){
            System.out.println("El docente no existe");
            attribute.addFlashAttribute("error", "El expediente no existe");
            return "redirect:/personalDocente";
        }


        model.addAttribute("profesor", profesor);
        return "Expediente_docente/Docentes/fichaDocente";
    }

    // Eliminar ficha
    @GetMapping("/deletefichaDocente/{id}")
    public String eliminarDocente(@PathVariable("id") String idDocente, RedirectAttributes attribute) {
        Docente profesor = docenteService.buscarPorIdDocente(idDocente);


        if(profesor == null){
            System.out.println("El docente no existe");
            attribute.addFlashAttribute("error", "El expediente no existe");
            return "redirect:/personalDocente";
        }

        docenteService.eliminar(idDocente);
        attribute.addFlashAttribute("warning", "El expediente se elimino");

        return "redirect:/personalDocente";
    }
}
