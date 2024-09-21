package com.dsi.insibo.sice.Paquetes_escolar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsi.insibo.sice.Administrativo.Bachilleratos.Servicios.BachilleratoService;
import com.dsi.insibo.sice.Administrativo.Orientadores.OrientadorService;
import com.dsi.insibo.sice.Expediente_alumno.AlumnoService;
import com.dsi.insibo.sice.Expediente_docente.Docentes.DocenteService;
import com.dsi.insibo.sice.Paquetes_escolar.Uniforme.UniformeService;
import com.dsi.insibo.sice.Paquetes_escolar.Utiles.UtilesService;
import com.dsi.insibo.sice.Paquetes_escolar.Zapatos.ZapatosService;
import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Bachillerato;
import com.dsi.insibo.sice.entity.UtilesEscolares;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/entregasPaquetes")
public class EntregasController {
    @Autowired
    private EntregasService entregasService;
    @Autowired
    BachilleratoService bachilleratoService;
    @Autowired
    DocenteService docenteService;
    @Autowired
    OrientadorService orientadorService;
    @Autowired
    AlumnoService alumnoService;

    @Autowired
    private UniformeService uniformeService;
    @Autowired
    private UtilesService utilesEscolaresService;
    @Autowired
    private ZapatosService zapatosService;

    @GetMapping("/seccionAsigada/{id}")
    public String verAlumno(Model model, @PathVariable("id") int id,
            @RequestParam(value = "tipoPaquete", required = false) String tipoPaquete) {

        // Obtener la información del Bachillerato
        Bachillerato bachillerato = bachilleratoService.bachilleratoPorId(id);

        // Añadir atributos al modelo
        model.addAttribute("bachillerato", bachillerato);
        model.addAttribute("id", id); // Pasar el ID del bachillerato
        model.addAttribute("tipoPaquete", tipoPaquete); // Paquete seleccionado

        return "Paquetes_escolares/generarListado";
    }

    @GetMapping("/seccion/{id}")
    public String miSeccion(Model model, @PathVariable("id") int id,
            @RequestParam(value = "tipoPaquete", required = false) String tipoPaquete,
            @RequestParam(value = "genero", required = false) String genero) {

        // Obtener la fecha actual
        LocalDate currentDate = LocalDate.now();

        // Obtener la información del Bachillerato por ID
        Bachillerato bachillerato = bachilleratoService.bachilleratoPorId(id);

        // Si el filtro de género está vacío, asignarlo a null
        if (genero != null && genero.isEmpty()) {
            genero = null;
        }

        // Obtener la lista de alumnos según los parámetros
        List<Alumno> listaAlumnos = alumnoService.listarAlumnos(bachillerato.getNombreCarrera(),
                String.valueOf(bachillerato.getGrado()), bachillerato.getSeccion(), genero);

        // Ordenar la lista de alumnos por apellido
        listaAlumnos.sort(Comparator.comparing(Alumno::getApellidoAlumno));

        // Agregar atributos al modelo para la vista
        model.addAttribute("bachillerato", bachillerato);
        model.addAttribute("alumnos", listaAlumnos); // Lista de alumnos
        model.addAttribute("tipoPaquete", tipoPaquete); // Tipo de paquete seleccionado
        model.addAttribute("id", id); // ID del bachillerato

        return "Paquetes_escolares/generarListado";
    }

    @PostMapping("/entregarPaquete")
    public String entregarPaquete(@RequestParam("tipoPaquete") String tipoPaquete,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        try {
            // Obtener los IDs de los alumnos desde el formulario
            String[] alumnoIds = request.getParameterValues("alumnoId");

            for (String idAlumno : alumnoIds) {
                Alumno alumno = alumnoService.buscarPorIdAlumno(Integer.parseInt(idAlumno));
                // Verificar el tipo de paquete y realizar la entrega correspondiente
                if (tipoPaquete.equals("paqueteUniforme")) {
                    // Obtener la talla del uniforme
                    String tallaUniforme = request.getParameter("talla_" + idAlumno);
                    // Verificar si se ha proporcionado una talla
                    if (tallaUniforme == null || tallaUniforme.trim().isEmpty()) {
                        redirectAttributes.addFlashAttribute("error", "La talla del uniforme es obligatoria.");
                        return "redirect:/entregasPaquetes/seccion/" + request.getParameter("id");
                    }
                    // Llamar al servicio para guardar el uniforme
                    boolean fueEntregado = request.getParameter("entregado_" + idAlumno) != null;
                    uniformeService.saveUniforme(alumno, tallaUniforme, fueEntregado);
                } else if (tipoPaquete.equals("paqueteUtiles")) {
                    boolean fueEntregado = request.getParameter("entregado_" + idAlumno) != null;
                    utilesEscolaresService.saveUtilesEscolares(alumno, fueEntregado);
                } else if (tipoPaquete.equals("paqueteZapatos")) {
                    String tallaZapatosStr = request.getParameter("talla_" + idAlumno);
                    if (tallaZapatosStr == null || tallaZapatosStr.trim().isEmpty()) {
                        redirectAttributes.addFlashAttribute("error", "La talla de zapatos es obligatoria.");
                        return "redirect:/entregasPaquetes/seccion/" + request.getParameter("id");
                    }
                    int tallaZapato;
                    try {
                        tallaZapato = Integer.parseInt(tallaZapatosStr);
                    } catch (NumberFormatException e) {
                        redirectAttributes.addFlashAttribute("error", "La talla de zapatos debe ser un número válido.");
                        return "redirect:/entregasPaquetes/seccion/" + request.getParameter("id");
                    }
                    boolean fueEntregado = request.getParameter("entregado_" + idAlumno) != null;
                    zapatosService.saveZapatos(alumno, tallaZapato, fueEntregado);
                }
            }
            // Mensaje de éxito
            redirectAttributes.addFlashAttribute("success", "Paquetes entregados con éxito.");
        } catch (Exception e) {
            // Mensaje de error
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error al entregar los paquetes.");
        }

        // Redirigir a la misma URL (vista del formulario)
        return "redirect:/entregasPaquetes/seccion/" + request.getParameter("id");
    }

    @GetMapping("/reportesEntrega/{id}")
    public String generarReporte(
            @RequestParam(required = false) String tipoPaquete, // Tipo de paquete (zapatos, útiles, uniformes)
            @RequestParam(required = false) String estadoEntrega, // Estado de entrega (entregado, no entregado, todos)
            @PathVariable int id, // ID del bachillerato o sección para filtrar alumnos
            Model model) {

        // Si no se ha seleccionado un estado de entrega, por defecto se usan todos
        // ("todos")
        if (estadoEntrega == null || estadoEntrega.isEmpty()) {
            estadoEntrega = "todos";
        }

        // Obtener la lista de alumnos filtrados por el ID del bachillerato o sección
        // Aquí puedes ajustar el método de obtención de alumnos según lo necesites
        List<Alumno> alumnos = alumnoService.findAlumnosByBachilleratoCodigoBachillerato(id);

        // Filtrar los alumnos según el estado de entrega (entregado, no entregado,
        // todos)
        List<Alumno> alumnosFiltrados = entregasService.filtrarPorEstadoEntrega(alumnos, estadoEntrega);

        // Pasar los datos al modelo para la vista
        model.addAttribute("alumnos", alumnosFiltrados); // Lista de alumnos filtrados
        model.addAttribute("estadoEntrega", estadoEntrega); // Estado de entrega seleccionado
        model.addAttribute("tipoPaquete", tipoPaquete); // Tipo de paquete seleccionado
        model.addAttribute("id", id); // ID del bachillerato o sección

        // Devolver el nombre de la vista para mostrar los resultados
        return "Paquetes_escolares/reporteEntrega";
    }

    // @GetMapping("/reportesEntrega/fechas/{id}")
    // @ResponseBody
    // public List<String> obtenerFechasPorTipoPaquete(
    //         @PathVariable int id,
    //         @RequestParam String tipoPaquete) {

    //     List<String> fechas = new ArrayList<>();

    //     if ("paqueteZapatos".equals(tipoPaquete)) {
    //         fechas = entregasService.obtenerFechasZapatos(id); // Implementa este método en tu servicio
    //     } else if ("paqueteUtiles".equals(tipoPaquete)) {
    //         fechas = entregasService.obtenerFechasUtiles(id); // Implementa este método en tu servicio
    //     } else if ("paqueteUniforme".equals(tipoPaquete)) {
    //         fechas = entregasService.obtenerFechasUniforme(id); // Implementa este método en tu servicio
    //     }

    //     return fechas; // Devuelve la lista de fechas en formato JSON
    // }

}
