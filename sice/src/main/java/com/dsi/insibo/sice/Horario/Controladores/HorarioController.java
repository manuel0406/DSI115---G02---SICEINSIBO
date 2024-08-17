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
import com.dsi.insibo.sice.Horario.Servicios.HorarioService;
import com.dsi.insibo.sice.entity.Asignacion;
import com.dsi.insibo.sice.entity.AsignacionHorario;
import com.dsi.insibo.sice.entity.Bachillerato;
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

    @GetMapping("/asignarHoras")
    public String asignar(Model model,
            @RequestParam(value = "carrera", required = false) String carrera,
            @RequestParam(value = "grado", required = false) String grado,
            @RequestParam(value = "seccion", required = false) String seccion) {
        prepararVista(model, carrera, grado, seccion, false);
        return "Horario/asignarHoras";
    }

    @GetMapping("/editarHoras")
    public String editarHoras(Model model,
            @RequestParam(value = "carrera", required = false) String carrera,
            @RequestParam(value = "grado", required = false) String grado,
            @RequestParam(value = "seccion", required = false) String seccion) {
        prepararVista(model, carrera, grado, seccion, true);
        return "Horario/editarHoras";
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
        //idBachillerato = extraerPrimerValor(idBachillerato);
        //nombreDia = extraerPrimerValor(nombreDia);
        //horaDia = extraerPrimerValor(horaDia);

        // Verifica que no se formen bloques de mas de 2 horas seguidas de una materia
        // básica
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
        //idBachillerato = extraerPrimerValor(idBachillerato);
        //nombreDia = extraerPrimerValor(nombreDia);
        //horaDia = extraerPrimerValor(horaDia);

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

    // Prepara la vista tanto para asignarHoras como para editarHoras
    private void prepararVista(Model model, String carrera, String grado, String seccion, boolean esEdicion) {
        boolean formSubmitted = (carrera != null || grado != null || seccion != null);

        carrera = normalizarParametro(carrera);
        grado = normalizarParametro(grado);
        seccion = normalizarParametro(seccion);

        // Obtener la lista de carreras disponibles
        List<Bachillerato> listaCarreras = bachilleratoService.listaCarrera();

        // Variables para almacenar los datos necesarios
        List<Integer> horasExistentes = null;
        Bachillerato bachillerato = null;
        List<Asignacion> listaAsignaciones = null;

        // Obtener el bachillerato seleccionado
        if (carrera != null && grado != null && seccion != null) {
            bachillerato = bachilleratoService.obtenerBachilleratoEspecifico(carrera, Integer.parseInt(grado), seccion);
        }

        // Si se ha seleccionado un bachillerato válido
        if (bachillerato != null && bachillerato.getCodigoBachillerato() != 0) {
            horasExistentes = horarioService
                    .obtenerIdHorariosBasePorCodigoBachillerato(bachillerato.getCodigoBachillerato());
            listaAsignaciones = asignacionService
                    .listarAsignacionesCodigoBachillerato(bachillerato.getCodigoBachillerato());

            if (esEdicion) {
                // horasDeClase contiene todas los registros de AsignacionHorario de una sección
                // determinada
                // Estos datos son necesarios unicamente cuando se carga la vista de edición
                List<AsignacionHorario> horasDeClase = null;
                horasDeClase = horarioService.obtenerHorasAsignadas(bachillerato.getCodigoBachillerato());
                model.addAttribute("horasDeClase", horasDeClase != null ? horasDeClase : List.of());
            }
        }

        // Añadir atributos al modelo para su uso en la vista
        model.addAttribute("bachilleratos", listaCarreras);
        model.addAttribute("carrera", carrera);
        model.addAttribute("grado", grado);
        model.addAttribute("seccion", seccion);
        model.addAttribute("bachillerato", bachillerato);
        model.addAttribute("formSubmitted", formSubmitted);
        model.addAttribute("asignaciones", listaAsignaciones != null ? listaAsignaciones : List.of());

        // Transformar a JSON la lista de idHorarioBase de las horas de clase existentes
        try {
            String envioHoras = new ObjectMapper().writeValueAsString(horasExistentes);
            model.addAttribute("envioHoras", envioHoras);
        } catch (JsonProcessingException e) {
            System.out.println("Error al procesar JSON: " + e.getMessage());
        }
    }

    // Agrega parámetros a los RedirectAttributes para mantener el contexto en las redirecciones
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
