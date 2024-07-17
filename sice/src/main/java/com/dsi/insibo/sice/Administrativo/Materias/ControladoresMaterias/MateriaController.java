package com.dsi.insibo.sice.Administrativo.Materias.ControladoresMaterias;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.MateriasService;
import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.BachilleratosService;
import com.dsi.insibo.sice.entity.Bachillerato;
import com.dsi.insibo.sice.entity.Materia;

@Controller
public class MateriaController {
    
    // Materias
    @Autowired
    private MateriasService materiasService;
    // Bachilleratos
    @Autowired
    private BachilleratosService bachilleratosService;

    //@PreAuthorize("hasRole()")    
    @GetMapping("/GestionMaterias")
    public String gestionarMaterias(Model model){
        List<Materia> materias = materiasService.obtenerMaterias();
        Set<Integer> grados = bachilleratosService.obtenerGrados();
        Set<String> tecnicos = bachilleratosService.obtenerTecnicos();
        model.addAttribute("materias", materias);
        model.addAttribute("grados", grados);
        model.addAttribute("tecnicos", tecnicos);
        return "Administrativo/GestionMaterias/GestionarMaterias";
    }

    @GetMapping("/getSecciones")
    @ResponseBody
    public Set<String> getSecciones(@RequestParam Integer grado, @RequestParam String tecnico) {
        return bachilleratosService.obtenerSecciones(tecnico, grado);
    }

    @PostMapping("/guardarMateria")
    public String guardarMateria(@RequestParam("nomMateria") String nombre, 
                                 @RequestParam("codMateria") String codigo,
                                 @RequestParam("tipoMateria") String tipo) {
        Materia materia = new Materia();
        materia.setCodMateria(codigo);
        materia.setNomMateria(nombre);
        materia.setTipoMateria(tipo);
        materiasService.guardarMateria(materia);
        return "redirect:/GestionMaterias";
    }





    @GetMapping("/NuevaMateria")
    public String nuevaMateria(Model model){
        List<Bachillerato> primeros = bachilleratosService.obtenerPrimeros();
        List<Bachillerato> segundos = bachilleratosService.obtenerSegundos();
        List<Bachillerato> terceros = bachilleratosService.obtenerTerceros();
        model.addAttribute("primeros", primeros);
        model.addAttribute("segundos", segundos);
        model.addAttribute("terceros", terceros);
        return "Administrativo/GestionMaterias/NuevaMateria";
    }

}
