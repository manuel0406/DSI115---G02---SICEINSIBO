package com.dsi.insibo.sice.Horario;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.BachilleratosService;
import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.MateriasService;
import com.dsi.insibo.sice.Calificaciones.NotaService;
import com.dsi.insibo.sice.Expediente_alumno.AlumnoService;
import com.dsi.insibo.sice.Expediente_alumno.AnexoAlumnoService;
import com.dsi.insibo.sice.Expediente_alumno.BachilleratoService;
import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Bachillerato;
import com.dsi.insibo.sice.entity.Materia;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/horarios")
public class HorarioController {
    // Materias
    @Autowired
    private MateriasService materiasService;
    // Bachilleratos
    @Autowired
    private BachilleratosService bachilleratosService;
    @Autowired
    private BachilleratoService bachilleratoService;

    @GetMapping("/editarHoras")
    public String editar() {
        return "Horario/editarHoras";
    }

    @GetMapping("/asignarHoras")
    public String asignar(Model model,
            @RequestParam(value = "carrera", required = false) String carrera,
            @RequestParam(value = "grado", required = false) String grado,
            @RequestParam(value = "seccion", required = false) String seccion) {

        // Convertir cadenas vacías a null para evitar problemas con las consultas
        if (carrera != null && carrera.isEmpty()) {
            carrera = null;
        }
        if (grado != null && grado.isEmpty()) {
            grado = null;
        }
        if (seccion != null && seccion.isEmpty()) {
            seccion = null;
        }

        // Obtener la lista de carreras (bachilleratos)
        List<Bachillerato> listaCarreras = bachilleratoService.listaCarrera();

        // Agregar atributos al modelo para ser utilizados en la vista
        model.addAttribute("bachilleratos", listaCarreras);
        model.addAttribute("carrera", carrera);
        model.addAttribute("grado", grado);
        model.addAttribute("seccion", seccion);

        // Retornar el nombre de la vista a ser renderizada
        return "Horario/asignarHoras";
    }

}
