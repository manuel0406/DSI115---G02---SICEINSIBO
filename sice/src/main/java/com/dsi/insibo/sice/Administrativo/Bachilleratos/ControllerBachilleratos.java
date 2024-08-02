package com.dsi.insibo.sice.Administrativo.Bachilleratos;

import java.util.List;

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
    public String prueba(Model model) {

        AnioAcademico anio = new AnioAcademico();
        List<AnioAcademico> listaAnio = anioService.listaAnio();

        model.addAttribute("titulo", "Año");
        model.addAttribute("anio", anio);
        model.addAttribute("lista", listaAnio);
        return "Administrativo/GestionBachilleratos/anio.html";
    }

    @PostMapping("/guardarAnio")
    public String guardarAnio(@ModelAttribute AnioAcademico anio, RedirectAttributes attributes) {

        AnioAcademico anioExistente= anioService.buscarPoridAnioAcademico(anio.getIdAnioAcademico());
        if (anioExistente != null) {
			attributes.addFlashAttribute("error", "Error: El año ya existe.");
            return "redirect:/Bachillerato/anio";
		}else{

            anioService.guardarAnio(anio);
            attributes.addFlashAttribute("success", "¡Registro guardado con exito!");

        }
        
        return "redirect:/Bachillerato/anio";
    }
    @PostMapping("/editarAnio")
    public String editarAnio(@ModelAttribute AnioAcademico anio, RedirectAttributes attributes) {

        try {            
            anioService.guardarAnio(anio);
            attributes.addFlashAttribute("success", "¡Registro editado con exito!");

        } catch (Exception sql) {
            System.out.println("El error es: " + sql);
            attributes.addFlashAttribute("error", "¡Ese año ya existe!");

        }
        return "redirect:/Bachillerato/anio";
    }
}
