package com.dsi.insibo.sice.Expediente_docente.Docentes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.dsi.insibo.sice.entity.Docente;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class DocenteController {
    //Direccionadores estaticos
    @GetMapping("/anexosDocente")
    public String docentes() {
        return "Expediente_docente/Docentes/anexosDocente";
    }

    @GetMapping("/barra")
    public String barra() {
        return "Expediente_docente/barra";
    }




    // Direccionadores de acción

    //Creando docente
    @GetMapping("/fichaDocente")
    public String docentes(Model model) {
        Docente profesor = new Docente();

		model.addAttribute("profesor", profesor);
        return "Expediente_docente/Docentes/fichaDocente";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Docente docente) {

        docenteService.guardarDocente(docente);
        return "redirect:/personalDocente";
    }

    //Lista docentes usando la DB
    @Autowired
    private DocenteService docenteService;
    @GetMapping("/personalDocente")
    public String listarDocentes(Model model) {
        List<DocenteDTO> listadoDocentes = docenteService.listarDocentes();

        model.addAttribute("titulo", "Listado de docentes");
        model.addAttribute("Docentes", listadoDocentes);

        return "Expediente_docente/Docentes/listarDocentes";
    }


    //Editar ficha
        //Creando docente
        @GetMapping("/editfichaDocente/{id}")
        public String editarDocente(@PathVariable("id") String idDocente, Model model) {
            Docente profesor = docenteService.buscarPorIdDocente(idDocente);
    
            model.addAttribute("profesor", profesor);
            return "Expediente_docente/Docentes/fichaDocente";
        }

            //Eliminar ficha
        //Creando docente
        @GetMapping("/deletefichaDocente/{id}")
        public String eliminarDocente(@PathVariable("id") String idDocente) {
            docenteService.eliminar(idDocente);
            return "redirect:/personalDocente";
        }
}
