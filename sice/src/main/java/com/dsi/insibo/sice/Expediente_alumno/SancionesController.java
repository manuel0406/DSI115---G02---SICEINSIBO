package com.dsi.insibo.sice.Expediente_alumno;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Sancion;

@Controller
@RequestMapping("/ExpedienteAlumno")
@PreAuthorize("hasRole('ADMINISTRADOR')") // SOLO PARA DOCENTES
public class SancionesController {
    @Autowired
    private AlumnoService alumnoService;
    @Autowired
    private SancionesService sancionesService;

    @GetMapping("/Sanciones/{nie}")
    public String sancionesAlumno(@PathVariable("nie") int nie, Model model, RedirectAttributes attributes) {
        Alumno alumno = null;
        if (nie > 0) {
            // Busca al alumno por su número de identificación estudiantil (NIE)
            alumno = alumnoService.buscarPorIdAlumno(nie);

            // Verifica que el alumno exista
            if (alumno == null) {
                System.out.println("Error: ¡El NIE ingresado no existe!");
                attributes.addFlashAttribute("error", "Error: ¡El NIE ingresado no existe!");
                return "redirect:/ExpedienteAlumno/ver";
            }

        } else {
            // Maneja el caso donde el NIE no es válido
            System.out.println("Error: ¡El NIE ingresado no es válido!");
            attributes.addFlashAttribute("error", "Error: ¡El NIE ingresado no es válido!");
            return "redirect:/ExpedienteAlumno/ver";
        }

        List<Sancion> listaSanciones= sancionesService.buscarSancionAlumno(nie);
        Sancion sancion= new Sancion();

        // Agregar atributos al modelo para ser utilizados en la vista
        model.addAttribute("titulo", "Sanciones");
        model.addAttribute("alumno", alumno);
        model.addAttribute("sancion", sancion);
        model.addAttribute("listaSanciones", listaSanciones);
        return "Expediente_alumno/AlumnoSanciones";
    }

    @PostMapping("/GuardarSancion/{nie}")
    public String guardarSancion(@PathVariable("nie") int nie,@ModelAttribute Sancion sancion, RedirectAttributes attributes){

        //Buscar el alumno por su NIE
        Alumno alumno =alumnoService.buscarPorIdAlumno(nie);
        sancion.setAlumno(alumno);
        sancion.setFechaSancion(new Date());
        sancionesService.guardarSancion(sancion);
        attributes.addFlashAttribute("success", "¡Sanción guardada o actalizada con éxito!");
        return "redirect:/ExpedienteAlumno/Sanciones/{nie}";
    }

    @GetMapping("/eliminarSancion/{nie}/{id}")
    public String eliminarSancion(@PathVariable("id") int id,@PathVariable("id") int nie, RedirectAttributes attributes)
    {
        sancionesService.EliminarSancion(id);
        attributes.addFlashAttribute("warning", "¡Registro eliminado con éxito!");
        return "redirect:/ExpedienteAlumno/Sanciones/{nie}";
    }
}
