package com.dsi.insibo.sice.Administrativo.Bachilleratos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.entity.AnioAcademico;
import com.dsi.insibo.sice.entity.Bachillerato;

@Controller
@RequestMapping("/Bachillerato")
public class ControllerBachilleratos {

    @Autowired
    AnioService anioService;

   
   @GetMapping("/anio")
public String prueba(Model model) {

    AnioAcademico anio = new AnioAcademico();
    
    List<AnioAcademico> listaAnio = anioService.listaAnio();
    List<Bachillerato> listaNullos = new ArrayList<>();
    Map<Integer, Boolean> anioBachilleratoMap = new HashMap<>();

    for (AnioAcademico anioAcademico : listaAnio) {
        listaNullos.addAll(anioService.listaNullos(Collections.singletonList(anioAcademico)));
        boolean tieneBachilleratos = anioService.tieneBachilleratos(anioAcademico.getIdAnioAcademico());
        anioBachilleratoMap.put(anioAcademico.getIdAnioAcademico(), tieneBachilleratos);
    }

    model.addAttribute("titulo", "Año");
    model.addAttribute("lista", listaAnio);
    model.addAttribute("anioBachilleratoMap", anioBachilleratoMap);
    model.addAttribute("anio", anio);
    
    return "Administrativo/GestionBachilleratos/anio.html";
}


    @PostMapping("/guardarAnio")
    public String guardarAnio(@ModelAttribute AnioAcademico anio, RedirectAttributes attributes) {

        AnioAcademico anioExistente = anioService.buscandoAnio(anio.getAnio());
        if (anioExistente != null) {
            attributes.addFlashAttribute("error", "Error: El año ya existe.");
            return "redirect:/Bachillerato/anio";
        } else {

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
