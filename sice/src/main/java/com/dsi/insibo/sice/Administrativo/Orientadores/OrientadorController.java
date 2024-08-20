package com.dsi.insibo.sice.Administrativo.Orientadores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.Administrativo.Bachilleratos.Servicios.BachilleratoService;
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
    public String asignacion(Model model,@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "20") int size){

        Orientador orientador= new Orientador();
        List<Bachillerato> listaBachilleratos = bachilleratoService.listaBachilleratos(); 

        List<Orientador> listaOrientadores= orientadorService.listaOrientador();

        //Crear una estructura de paginado manualmente
        PageRequest pageRequest= PageRequest.of(page -1, size);
        int start =(int) pageRequest.getOffset();
        int end =Math.min((start+pageRequest.getPageSize()), listaOrientadores.size());
        Page<Orientador> pageOrientadores= new PageImpl<>(listaOrientadores.subList(start, end), pageRequest,listaOrientadores.size());

        model.addAttribute("titulo","Asignar");
        model.addAttribute("orientador",orientador);
        model.addAttribute("bachilleratos", listaBachilleratos);
        model.addAttribute("Docentes", docenteService.docentes());
        model.addAttribute("listaOrientadores", pageOrientadores.getContent());
        model.addAttribute("page", page);
		model.addAttribute("totalPages", pageOrientadores.getTotalPages());
        model.addAttribute("totalElements", listaOrientadores.size());
		int baseIndex = (page - 1) * size;//sirve para mantener la base de la numeración de lo alumnos cuando cambia de pagina
		model.addAttribute("baseIndex", baseIndex);

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