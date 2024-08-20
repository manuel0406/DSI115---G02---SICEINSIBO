package com.dsi.insibo.sice.Horario.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/horarios")
public class HorarioController {

    @Autowired
    private BachilleratoService bachilleratoService;
    @Autowired
    private AsignacionService asignacionService;
    @Autowired
    private HorarioService horarioService;

    // Carga la vista para asignar horas
    @GetMapping("/asignarHoras")
    public String asignar(Model model,
            @RequestParam(value = "carrera", required = false) String carrera,
            @RequestParam(value = "grado", required = false) String grado,
            @RequestParam(value = "seccion", required = false) String seccion) {

        boolean formSubmitted = (carrera != null || grado != null || seccion != null);

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
        List<Integer> horasExistentes = null;

        // Obtener el objeto Bachillerato si se proporcionan los parámetros necesarios
        Bachillerato bachillerato = null;
        if (carrera != null && grado != null && seccion != null) {
            bachillerato = bachilleratoService.obtenerBachilleratoEspecifico(carrera, Integer.parseInt(grado), seccion);

            if (bachillerato != null && bachillerato.getCodigoBachillerato() != 0) {
                horasExistentes = horarioService
                        .obtenerIdHorariosBasePorCodigoBachillerato(bachillerato.getCodigoBachillerato());

                // for (Integer numero : horasExistentes) {System.out.print(numero + " ");}
            }
        }

        // Obtener las asignaciones (carga academica) de una seccion dada su llave
        // primaria
        List<Asignacion> listaAsignaciones = null;
        if (bachillerato != null) {
            listaAsignaciones = asignacionService
                    .listarAsignacionesCodigoBachillerato(bachillerato.getCodigoBachillerato());
        }

        // Agregar atributos al modelo
        model.addAttribute("bachilleratos", listaCarreras);
        model.addAttribute("carrera", carrera);
        model.addAttribute("grado", grado);
        model.addAttribute("seccion", seccion);
        model.addAttribute("bachillerato", bachillerato);
        model.addAttribute("formSubmitted", formSubmitted);
        model.addAttribute("asignaciones", listaAsignaciones != null ? listaAsignaciones : List.of());

        // Convertir la lista de horasExistentes a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String envioHoras = objectMapper.writeValueAsString(horasExistentes);
            model.addAttribute("envioHoras", envioHoras); // Para JS
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }

        // Cargar la vista
        return "Horario/asignarHoras";
    }

    // Maneja la vista para editar las horas asignadas
    @GetMapping("/editarHoras")
    public String editarHoras(Model model,
            @RequestParam(value = "carrera", required = false) String carrera,
            @RequestParam(value = "grado", required = false) String grado,
            @RequestParam(value = "seccion", required = false) String seccion) {

        boolean formSubmitted = (carrera != null || grado != null || seccion != null);

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
        List<Integer> horasExistentes = null;

        // Obtener el objeto Bachillerato si se proporcionan los parámetros necesarios
        Bachillerato bachillerato = null;
        if (carrera != null && grado != null && seccion != null) {
            bachillerato = bachilleratoService.obtenerBachilleratoEspecifico(carrera, Integer.parseInt(grado), seccion);

            if (bachillerato != null && bachillerato.getCodigoBachillerato() != 0) {
                horasExistentes = horarioService
                        .obtenerIdHorariosBasePorCodigoBachillerato(bachillerato.getCodigoBachillerato());
            }
        }

        // Obtener las asignaciones (carga academica) de una seccion dada su llave
        // primaria
        List<Asignacion> listaAsignaciones = null;
        if (bachillerato != null) {
            listaAsignaciones = asignacionService
                    .listarAsignacionesCodigoBachillerato(bachillerato.getCodigoBachillerato());
        }

        // Agregar atributos al modelo
        model.addAttribute("bachilleratos", listaCarreras);
        model.addAttribute("carrera", carrera);
        model.addAttribute("grado", grado);
        model.addAttribute("seccion", seccion);
        model.addAttribute("bachillerato", bachillerato);
        model.addAttribute("formSubmitted", formSubmitted);
        model.addAttribute("asignaciones", listaAsignaciones != null ? listaAsignaciones : List.of());

        // Convertir la lista de horasExistentes a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String envioHoras = objectMapper.writeValueAsString(horasExistentes);
            model.addAttribute("envioHoras", envioHoras); // Para JS
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }

        // Cargar la vista
        return "Horario/editarHoras";
    }

    // Guarda una hora de clase y redirige a la vista para asignar horas
    @PostMapping("/guardarHora")
    public String guardarhora(@RequestParam("asignacionSeleccionada") int asignacionID,
            @RequestParam("horaSeleccionada") int horarioBaseID,
            @RequestParam(value = "carrera", required = false) String carrera,
            @RequestParam(value = "grado", required = false) String grado,
            @RequestParam(value = "seccion", required = false) String seccion,
            RedirectAttributes redirectAttributes) {

        // Extraer el primer valor antes de la coma
        if (carrera != null && carrera.contains(",")) {
            carrera = carrera.split(",")[0];
        }
        if (grado != null && grado.contains(",")) {
            grado = grado.split(",")[0];
        }
        if (seccion != null && seccion.contains(",")) {
            seccion = seccion.split(",")[0];
        }

        AsignacionHorario hora = new AsignacionHorario();
        Asignacion asignacion = new Asignacion();
        HorarioBase horarioBase = new HorarioBase();

        asignacion.setIdAsignacion(asignacionID);
        hora.setAsignacion(asignacion);

        horarioBase.setIdHorarioBase(horarioBaseID);
        hora.setHorarioBase(horarioBase);

        horarioService.guardarHoraAsignacion(hora);

        redirectAttributes.addAttribute("carrera", carrera);
        redirectAttributes.addAttribute("grado", grado);
        redirectAttributes.addAttribute("seccion", seccion);

        return "redirect:/horarios/asignarHoras";
    }
}