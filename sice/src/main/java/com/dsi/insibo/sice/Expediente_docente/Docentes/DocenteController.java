package com.dsi.insibo.sice.Expediente_docente.Docentes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.dsi.insibo.sice.entity.Docente;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class DocenteController {


    @GetMapping("/fichaDocente")
    public String docentes(Model model) {
        Docente profesor = new Docente();

		model.addAttribute("profesor", profesor);
        return "Expediente_docente/Docentes/fichaDocente";
    }

    @Autowired
    private DocenteService docenteService;
    @GetMapping("/personalDocente")
    public String listarDocentes(Model model) {
        List<DocenteDTO> listadoDocentes = docenteService.listarDocentes();

        model.addAttribute("titulo", "Listado de docentes");
        model.addAttribute("Docentes", listadoDocentes);

        return "Expediente_docente/Docentes/listarDocentes";
    }
    

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Docente docente) {

        docenteService.guardarDocente(docente);
        return "redirect:/personalDocente";
    }
}
