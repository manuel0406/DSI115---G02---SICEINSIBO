package com.dsi.insibo.sice.Administrativo.Bachilleratos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.entity.AnioAcademico;

@Controller
@RequestMapping("/Bachillerato")
public class ControllerBachilleratos {
    
    @Autowired
    AnioService anioService;

    @GetMapping("/anio")
    public String prueba(Model model){

        AnioAcademico anio= new AnioAcademico();
        model.addAttribute("titulo", "Año");
        model.addAttribute("anio", anio);
        return "Administrativo/GestionBachilleratos/anio.html";
    }

    @PostMapping("/guardarAnio")
    public String guardarAnio(@ModelAttribute AnioAcademico academico, RedirectAttributes attributes){
        
        academico.setActivoAnio(true);
        anioService.guardarAnio(academico);
        attributes.addFlashAttribute("success", "¡Registro guardado con exito!");
        return "redirect:/Bachillerato/anio";
    }
}
