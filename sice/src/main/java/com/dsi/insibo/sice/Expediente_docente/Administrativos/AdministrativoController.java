package com.dsi.insibo.sice.Expediente_docente.Administrativos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.dsi.insibo.sice.Expediente_docente.Docentes.DocenteDTO;
import com.dsi.insibo.sice.Expediente_docente.Docentes.DocenteService;
import com.dsi.insibo.sice.entity.Docente;
import com.dsi.insibo.sice.entity.PersonalAdministrativo;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/expedienteadministrativo")
public class AdministrativoController {

    // Ficha general de expediente docente /expedientedocente/formulario
    @GetMapping("/formulario")
    public String ficha(Model model) {
        PersonalAdministrativo administrativo = new PersonalAdministrativo();

        model.addAttribute("administrativo", administrativo);
        return "Expediente_docente/Administrativos/fichaAdministrativo";
    }

    // guardando formulario
    /*
     * @Autowired
     * private AdministrativoService administrativoService;
     */

    @PostMapping("/guardar")
    public String guardar(@Validated @ModelAttribute PersonalAdministrativo administrativo, BindingResult result,
            Model model, RedirectAttributes attribute) {

        if (result.hasErrors()) {
            model.addAttribute("administrativo", administrativo);
            System.out.println("Se tienen errores en el formulario");
            return "expedienteadministrativo/formulario";
        }

        boolean creado = administrativoService.guardarAdministrativo(administrativo);

        if (creado) {
            attribute.addFlashAttribute("success", "Expediente creado con exito!");
        } else {
            attribute.addFlashAttribute("success", "Expediente actualizado con exito!");
        }

        return "redirect:plantaadministrativa";
    }

    @Autowired
    private AdministrativoService administrativoService;

    @GetMapping("/plantaadministrativa")
    public String listarAdministrativos(Model model) {
        List<AdministrativoDTO> listadoAdministrativos = administrativoService.listarAdministrativos();
        model.addAttribute("titulo", "Planta Administrativa");
        model.addAttribute("Administrativos", listadoAdministrativos);
        return "Expediente_docente/Administrativos/listarAdministrativos";
    }

    // Editando docente
    @GetMapping("/editarexpediente/{id}")
    public String editarAdministrativo(@PathVariable("id") String idAdministrativo, Model model, RedirectAttributes attribute) {
        PersonalAdministrativo administrativo = administrativoService.buscarPorIdAdministrativo(idAdministrativo);
        if (administrativo == null) {
            System.out.println("El docente no existe");
            attribute.addFlashAttribute("error", "El expediente no existe");
            return "redirect:/expedienteadministrativo/plantaadministrativa";
        }

        model.addAttribute("administrativo", administrativo);
        model.addAttribute("editar", true); // Indica que se está editando un docente
        return "Expediente_docente/Administrativos/fichaAdministrativo";
    }

        // Eliminar ficha
        @GetMapping("/eliminarexpediente/{id}")
        public String eliminarAdministrativo(@PathVariable("id") String idAdministrativo, RedirectAttributes attribute) {
            PersonalAdministrativo administrativo = administrativoService.buscarPorIdAdministrativo(idAdministrativo);
    
    
            if(administrativo == null){
                attribute.addFlashAttribute("error", "El expediente no existe");
                return "redirect:/expedienteadministrativo/plantaadministrativa";
            }
    
            administrativoService.eliminar(idAdministrativo);
            attribute.addFlashAttribute("warning", "El expediente se elimino");
    
            return "redirect:/expedienteadministrativo/plantaadministrativa";
        }
}
