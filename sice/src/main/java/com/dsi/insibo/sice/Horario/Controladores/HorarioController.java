package com.dsi.insibo.sice.Horario.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.Administrativo.Bachilleratos.Servicios.BachilleratoService;
import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.AsignacionService;
import com.dsi.insibo.sice.Expediente_docente.Docentes.DocenteService;
import com.dsi.insibo.sice.Horario.PDF.HorarioDTO;
import com.dsi.insibo.sice.Horario.PDF.HorarioEditarDTO;
import com.dsi.insibo.sice.Horario.Servicios.HorarioService;
import com.dsi.insibo.sice.entity.Asignacion;
import com.dsi.insibo.sice.entity.AsignacionHorario;
import com.dsi.insibo.sice.entity.Bachillerato;
import com.dsi.insibo.sice.entity.Docente;
import com.dsi.insibo.sice.entity.HorarioBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/horarios")
public class HorarioController {

    @Autowired
    private BachilleratoService bachilleratoService;

    @Autowired
    private AsignacionService asignacionService;

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private DocenteService docenteService;

    @GetMapping("/asignarHoras")
    public String asignar(Model model,
            @RequestParam(value = "carrera", required = false) String carrera,
            @RequestParam(value = "grado", required = false) String grado,
            @RequestParam(value = "seccion", required = false) String seccion) {

        boolean formSubmitted = (carrera != null || grado != null || seccion != null);

        carrera = normalizarParametro(carrera);
        grado = normalizarParametro(grado);
        seccion = normalizarParametro(seccion);

        // Obtener la lista de carreras disponibles
        List<Bachillerato> listaCarreras = bachilleratoService.listaCarrera();

        // Variables para almacenar los datos necesarios
        Bachillerato bachillerato = null;
        List<Asignacion> listaAsignaciones = null;

        // Si el formulario no se ha enviado, muestra la alerta 'info'
        if (!formSubmitted) {
            model.addAttribute("info", "Selecciona una sección para comenzar");
        }

        // Obtener el bachillerato seleccionado
        if (carrera != null && grado != null && seccion != null) {
            bachillerato = bachilleratoService.obtenerBachilleratoEspecifico(carrera, Integer.parseInt(grado), seccion);
        }

        if (bachillerato != null && bachillerato.getCodigoBachillerato() != 0) {
            listaAsignaciones = asignacionService
                    .listarAsignacionesCodigoBachillerato(bachillerato.getCodigoBachillerato());
            // Obtener el horario y convertirlo a DTO
            List<HorarioDTO> horarioDTO = horarioService.obtenerHorasAsignadasDTO(
                    horarioService.obtenerHorasAsignadas(bachillerato.getCodigoBachillerato()));

            try {
                // Convertir el horario a JSON y agregar al modelo
                String horasDeClaseJson = new ObjectMapper().writeValueAsString(horarioDTO);
                model.addAttribute("horasDeClaseJson", horasDeClaseJson);
                model.addAttribute("horarioDTO", horarioDTO);
            } catch (JsonProcessingException e) {
                System.out.println("Error al procesar JSON: " + e.getMessage());
                model.addAttribute("horarioDTO", List.of()); // Lista vacía en caso de error
            }
        } else if (formSubmitted) {
            // Mostrar alerta 'warning' si no se encontró el bachillerato tras filtrar
            model.addAttribute("warning", "Selecciona una sección válida");
            model.addAttribute("horarioDTO", List.of()); // Lista vacía si no hay bachillerato
        }

        // Añadir atributos al modelo para su uso en la vista
        model.addAttribute("bachilleratos", listaCarreras);
        model.addAttribute("carrera", carrera);
        model.addAttribute("grado", grado);
        model.addAttribute("seccion", seccion);
        model.addAttribute("bachillerato", bachillerato);
        model.addAttribute("formSubmitted", formSubmitted);
        model.addAttribute("asignaciones", listaAsignaciones != null ? listaAsignaciones : List.of());

        return "Horario/asignarHoras";
    }

    @GetMapping("/editarHoras")
    public String editarHoras(Model model,
            @RequestParam(value = "carrera", required = false) String carrera,
            @RequestParam(value = "grado", required = false) String grado,
            @RequestParam(value = "seccion", required = false) String seccion) {

        boolean formSubmitted = (carrera != null || grado != null || seccion != null);

        carrera = normalizarParametro(carrera);
        grado = normalizarParametro(grado);
        seccion = normalizarParametro(seccion);

        // Obtener la lista de carreras disponibles
        List<Bachillerato> listaCarreras = bachilleratoService.listaCarrera();

        // Variables para almacenar los datos necesarios
        Bachillerato bachillerato = null;
        List<Asignacion> listaAsignaciones = null;

        // Si el formulario no se ha enviado, muestra la alerta 'info'
        if (!formSubmitted) {
            model.addAttribute("info", "Selecciona una sección para comenzar");
        }

        // Obtener el bachillerato seleccionado
        if (carrera != null && grado != null && seccion != null) {
            bachillerato = bachilleratoService.obtenerBachilleratoEspecifico(carrera, Integer.parseInt(grado), seccion);
        }

        if (bachillerato != null && bachillerato.getCodigoBachillerato() != 0) {

            listaAsignaciones = asignacionService
                    .listarAsignacionesCodigoBachillerato(bachillerato.getCodigoBachillerato());
            List<HorarioEditarDTO> horarioDTO = horarioService.obtenerHorasAsignadasEditarDTO(
                    horarioService.obtenerHorasAsignadas(bachillerato.getCodigoBachillerato()));

            model.addAttribute("horasDeClase", horarioDTO != null ? horarioDTO : List.of());
        } else if (formSubmitted) {
            // Mostrar alerta 'warning' si no se encontró el bachillerato tras filtrar
            model.addAttribute("warning", "Selecciona una sección válida");
            model.addAttribute("horarioDTO", List.of()); // Lista vacía si no hay bachillerato
        }

        // Añadir atributos al modelo para su uso en la vista
        model.addAttribute("bachilleratos", listaCarreras);
        model.addAttribute("carrera", carrera);
        model.addAttribute("grado", grado);
        model.addAttribute("seccion", seccion);
        model.addAttribute("bachillerato", bachillerato);
        model.addAttribute("formSubmitted", formSubmitted);
        model.addAttribute("asignaciones", listaAsignaciones != null ? listaAsignaciones : List.of());
        return "Horario/editarHoras";
    }

    @GetMapping("/generarHorarioSeccion")
    public String generarHorarioSeccion(Model model,
            @RequestParam(value = "carrera", required = false) String carrera,
            @RequestParam(value = "grado", required = false) String grado,
            @RequestParam(value = "seccion", required = false) String seccion) {

        boolean formSubmitted = (carrera != null || grado != null || seccion != null);

        carrera = normalizarParametro(carrera);
        grado = normalizarParametro(grado);
        seccion = normalizarParametro(seccion);

        // Obtener la lista de carreras disponibles
        List<Bachillerato> listaCarreras = bachilleratoService.listaCarrera();

        // Variables para almacenar los datos necesarios
        Bachillerato bachillerato = null;

        // Si el formulario no se ha enviado, muestra la alerta 'info'
        if (!formSubmitted) {
            model.addAttribute("info", "Selecciona una sección para comenzar");
        }

        // Obtener el bachillerato seleccionado
        if (carrera != null && grado != null && seccion != null) {
            bachillerato = bachilleratoService.obtenerBachilleratoEspecifico(carrera, Integer.parseInt(grado), seccion);
        }

        if (bachillerato != null && bachillerato.getCodigoBachillerato() != 0) {

            // Obtener el horario y convertirlo a DTO
            List<HorarioDTO> horarioDTO = horarioService.obtenerHorasAsignadasDTO(
                    horarioService.obtenerHorasAsignadas(bachillerato.getCodigoBachillerato()));

            try {
                // Convertir el horario a JSON y agregar al modelo
                String horasDeClaseJson = new ObjectMapper().writeValueAsString(horarioDTO);
                model.addAttribute("horasDeClaseJson", horasDeClaseJson);
                model.addAttribute("horarioDTO", horarioDTO);

            } catch (JsonProcessingException e) {
                System.out.println("Error al procesar JSON: " + e.getMessage());
                model.addAttribute("horarioDTO", List.of()); // Lista vacía en caso de error
            }
        } else if (formSubmitted) {
            // Mostrar alerta 'warning' si no se encontró el bachillerato tras filtrar
            model.addAttribute("warning", "Selecciona una sección válida");
            model.addAttribute("horarioDTO", List.of()); // Lista vacía si no hay bachillerato
        }

        // Añadir atributos al modelo para su uso en la vista
        model.addAttribute("bachilleratos", listaCarreras);
        model.addAttribute("carrera", carrera);
        model.addAttribute("grado", grado);
        model.addAttribute("seccion", seccion);
        model.addAttribute("bachillerato", bachillerato);
        model.addAttribute("formSubmitted", formSubmitted);
        return "Horario/generarHorarioSeccion";
    }

    @GetMapping("/generarHorarioDocente")
    public String generarHorarioDocente(Model model,
            @RequestParam(value = "duiDocente", required = false) String duiDocente,
            RedirectAttributes redirectAttributes) {

        boolean formSubmitted = (duiDocente != null);
        duiDocente = normalizarParametro(duiDocente);
        List<Docente> docentes = docenteService.listarDocenteAsignacion();
        Docente docenteSeleccionado = null;

        // Si el formulario no se ha enviado, muestra la alerta 'info'
        if (!formSubmitted) {
            model.addAttribute("info", "Selecciona un docente para comenzar");
        }

        if (duiDocente != null && !duiDocente.isEmpty()) {
            // Obtener el docente asociado al duiDocente
            for (Docente docente : docentes) {
                if (duiDocente.equals(docente.getDuiDocente())) {
                    docenteSeleccionado = docente;
                    break;
                }
            }

            if (docenteSeleccionado != null) {
                // Obtener el horario y convertirlo a DTO
                List<HorarioDTO> horarioDTO = horarioService.obtenerHorasAsignadasDTO(
                        horarioService.obtenerHorasAsignadasDocente(duiDocente));

                try {
                    // Convertir el horario a JSON y agregar al modelo
                    String horasDeClaseJson = new ObjectMapper().writeValueAsString(horarioDTO);
                    model.addAttribute("horasDeClaseJson", horasDeClaseJson);
                    model.addAttribute("docenteSeleccionado", docenteSeleccionado);
                    model.addAttribute("horarioDTO", horarioDTO);

                } catch (JsonProcessingException e) {
                    System.out.println("Error al procesar JSON: " + e.getMessage());
                    model.addAttribute("horarioDTO", List.of()); // Lista vacía en caso de error
                }
            } else {
                model.addAttribute("horarioDTO", List.of()); // Lista vacía si no se encuentra el docente
            }
        } else if (formSubmitted) {
            // Mostrar alerta 'warning' si no se encontró el bachillerato tras filtrar
            model.addAttribute("warning", "Selecciona una docente válido");
            model.addAttribute("horarioDTO", List.of()); // Lista vacía si no hay duiDocente
        }

        model.addAttribute("duiDocente", duiDocente);
        model.addAttribute("docentes", docentes);
        model.addAttribute("formSubmitted", formSubmitted);
        return "Horario/generarHorarioDocente";
    }

    @PostMapping("/guardarHora")
    public String guardarHora(@RequestParam("asignacionSeleccionada") int asignacionID,
            @RequestParam("horaSeleccionada") int horarioBaseID,
            @RequestParam("codigoBachillerato") String idBachillerato,
            @RequestParam("dia") String nombreDia,
            @RequestParam("horaBase") String horaDia,
            @RequestParam(value = "carrera", required = false) String carrera,
            @RequestParam(value = "grado", required = false) String grado,
            @RequestParam(value = "seccion", required = false) String seccion,
            RedirectAttributes redirectAttributes) {

        carrera = extraerPrimerValor(carrera);
        grado = extraerPrimerValor(grado);
        seccion = extraerPrimerValor(seccion);

        List<AsignacionHorario> horario = horarioService.obtenerHorasAsignadas(Integer.parseInt(idBachillerato));
        Integer horasExistentes = horario.size();

        // Si el grado es 1º o 2º
        if (Integer.parseInt(grado) <= 2) {
            // Limita las horas de clase que puede tener una sección
            if (horasExistentes != null && horasExistentes <= 46) {
                // Verifica que no se formen bloques de mas de 2 horas de clase seguidas de una
                // materia básica
                if (!horarioService.existeBloqueDeDosHoras(Integer.parseInt(idBachillerato), nombreDia,
                        Integer.parseInt(horaDia), asignacionID)) {
                    AsignacionHorario hora = new AsignacionHorario();
                    Asignacion asignacion = new Asignacion();
                    HorarioBase horarioBase = new HorarioBase();

                    asignacion.setIdAsignacion(asignacionID);
                    horarioBase.setIdHorarioBase(horarioBaseID);

                    hora.setHorarioBase(horarioBase);
                    hora.setAsignacion(asignacion);

                    horarioService.guardarHoraAsignacion(hora);
                    redirectAttributes.addFlashAttribute("success", "Hora agregada");
                } else {
                    redirectAttributes.addFlashAttribute("warning",
                            "No puedes asignar bloques de más de 2 horas para una misma materia de tipo básica");
                }
            } else {
                redirectAttributes.addFlashAttribute("warning",
                        "No puedes agregar mas horas de clase. Los 1º y 2º años tienen un máximo de 47 horas de clase");
            }
        } else {
            // Limita las horas de clase que puede tener una sección
            if (horasExistentes != null && horasExistentes <= 29) {
                // Verifica que no se formen bloques de mas de 2 horas de clase seguidas de una
                // materia básica
                if (!horarioService.existeBloqueDeDosHoras(Integer.parseInt(idBachillerato), nombreDia,
                        Integer.parseInt(horaDia), asignacionID)) {
                    AsignacionHorario hora = new AsignacionHorario();
                    Asignacion asignacion = new Asignacion();
                    HorarioBase horarioBase = new HorarioBase();

                    asignacion.setIdAsignacion(asignacionID);
                    horarioBase.setIdHorarioBase(horarioBaseID);

                    hora.setHorarioBase(horarioBase);
                    hora.setAsignacion(asignacion);

                    horarioService.guardarHoraAsignacion(hora);
                    redirectAttributes.addFlashAttribute("success", "Hora agregada");
                } else {
                    redirectAttributes.addFlashAttribute("warning",
                            "No puedes asignar bloques de más de 2 horas para una misma materia de tipo básica");
                }
            } else {
                redirectAttributes.addFlashAttribute("warning",
                        "No puedes agregar mas horas de clase. Los 3º años tienen un máximo de 30 horas de clase");
            }
        }

        agregarParametrosRedireccion(redirectAttributes, carrera, grado, seccion);
        return "redirect:/horarios/asignarHoras";
    }

    @PostMapping("/actualizarHora")
    public String editarHora(@RequestParam("idAsignacionHorario") int asignacionHorarioID,
            @RequestParam("asignacionSeleccionada") int asignacionID,
            @RequestParam("horaSeleccionada") int horarioBaseID,
            @RequestParam("codigoBachillerato") String idBachillerato,
            @RequestParam("dia") String nombreDia,
            @RequestParam("horaBase") String horaDia,
            @RequestParam(value = "carrera", required = false) String carrera,
            @RequestParam(value = "grado", required = false) String grado,
            @RequestParam(value = "seccion", required = false) String seccion,
            RedirectAttributes redirectAttributes) {

        carrera = extraerPrimerValor(carrera);
        grado = extraerPrimerValor(grado);
        seccion = extraerPrimerValor(seccion);

        // Verifica que no se formen bloques de mas de 2 horas seguidas de una materia
        // básica
        if (!horarioService.existeBloqueDeDosHoras(Integer.parseInt(idBachillerato), nombreDia,
                Integer.parseInt(horaDia), asignacionID)) {
            AsignacionHorario hora = horarioService.obtenerHoraAsignacionPorId(asignacionHorarioID);
            Asignacion asignacion = new Asignacion();
            HorarioBase horarioBase = new HorarioBase();

            asignacion.setIdAsignacion(asignacionID);
            horarioBase.setIdHorarioBase(horarioBaseID);

            hora.setHorarioBase(horarioBase);
            hora.setAsignacion(asignacion);

            horarioService.guardarHoraAsignacion(hora);
            redirectAttributes.addFlashAttribute("success", "Hora actualizada");
        } else {
            redirectAttributes.addFlashAttribute("warning",
                    "No puedes asignar bloques de más de 2 horas para una misma materia de tipo básica");
        }

        agregarParametrosRedireccion(redirectAttributes, carrera, grado, seccion);
        return "redirect:/horarios/editarHoras";
    }

    @GetMapping("/eliminarHora/{id}")
    public String eliminarHora(@PathVariable("id") Integer idAsignacionHorario,
            @RequestParam(value = "carrera", required = false) String carrera,
            @RequestParam(value = "grado", required = false) String grado,
            @RequestParam(value = "seccion", required = false) String seccion,
            RedirectAttributes redirectAttributes) {

        carrera = extraerPrimerValor(carrera);
        grado = extraerPrimerValor(grado);
        seccion = extraerPrimerValor(seccion);

        horarioService.eliminarHoraAsignacion(idAsignacionHorario);
        redirectAttributes.addFlashAttribute("warning", "La hora de clase se eliminó");

        agregarParametrosRedireccion(redirectAttributes, carrera, grado, seccion);
        return "redirect:/horarios/editarHoras";
    }

    // Agrega parámetros a los RedirectAttributes para mantener el contexto en las
    // redirecciones
    private void agregarParametrosRedireccion(RedirectAttributes redirectAttributes, String carrera, String grado,
            String seccion) {
        redirectAttributes.addAttribute("carrera", carrera);
        redirectAttributes.addAttribute("grado", grado);
        redirectAttributes.addAttribute("seccion", seccion);
    }

    // Extrae el primer valor de una cadena separada por comas
    private String extraerPrimerValor(String parametro) {
        if (parametro != null && parametro.contains(",")) {
            return parametro.split(",")[0];
        }
        return parametro;
    }

    // Normaliza un parámetro nulo o vacío a null
    private String normalizarParametro(String parametro) {
        return (parametro == null || parametro.isEmpty() || parametro.equals("null")) ? null : parametro;
    }
}
