package com.dsi.insibo.sice.Administrativo.Orientadores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.Expediente_alumno.BachilleratoService;
import com.dsi.insibo.sice.Expediente_docente.Docentes.DocenteRepository;
import com.dsi.insibo.sice.Expediente_docente.Docentes.DocenteService;
import com.dsi.insibo.sice.entity.Bachillerato;
import com.dsi.insibo.sice.entity.Orientador;

/**
 * OrientadorController
 */
@Controller
@RequestMapping("/AsignacionOrientador")
public class OrientadorController {

    @Autowired
    BachilleratoService bachilleratoService;
    @Autowired
    DocenteService docenteService;
    @Autowired
    OrientadorService orientadorService;

    @GetMapping("/Asignar")
    public String asignacion(Model model){

        Orientador orientador= new Orientador();
        List<Bachillerato> listaBachilleratos = bachilleratoService.listaBachilleratos(); 

        List<Orientador> listaOrientadores= orientadorService.listaOrientador();

        model.addAttribute("titulo","Asignar");
        model.addAttribute("orientador",orientador);
        model.addAttribute("bachilleratos", listaBachilleratos);
        model.addAttribute("Docentes", docenteService.docentes());
        model.addAttribute("listaOrientadores", listaOrientadores );

        return"Administrativo/GestionOrientador/asignacionOrientador";
    }

    @PostMapping("/guardarOrientacion")
    public String guardar(@ModelAttribute Orientador orientador, RedirectAttributes attributes){

        orientadorService.guardarOrientador(orientador);
        attributes.addFlashAttribute("success", "¡Registro guardado con exito!");

        return "redirect:/AsignacionOrientador/Asignar";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarOrientador(@PathVariable("id") int id, RedirectAttributes attributes){
        
        orientadorService.eliminarOrientador(id);
        attributes.addFlashAttribute("warning", "¡Registro eliminado con éxito!");
        return "redirect:/AsignacionOrientador/Asignar";
    }

    
}