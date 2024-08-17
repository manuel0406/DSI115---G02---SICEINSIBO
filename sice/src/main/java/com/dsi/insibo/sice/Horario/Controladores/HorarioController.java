package com.dsi.insibo.sice.Horario.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String guardarhora(@RequestParam("asignacionSeleccionada") int asignacionID,
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
        idBachillerato = extraerPrimerValor(idBachillerato);
        nombreDia = extraerPrimerValor(nombreDia);
        horaDia = extraerPrimerValor(horaDia);

        if(horarioService.existeBloqueDeDosHoras(Integer.parseInt(idBachillerato), nombreDia,
        Integer.parseInt(horaDia), asignacionID) == false){
            AsignacionHorario hora = new AsignacionHorario();
            Asignacion asignacion = new Asignacion();
            HorarioBase horarioBase = new HorarioBase();
    
            asignacion.setIdAsignacion(asignacionID);
            hora.setAsignacion(asignacion);
    
            horarioBase.setIdHorarioBase(horarioBaseID);
            hora.setHorarioBase(horarioBase);
    
            horarioService.guardarHoraAsignacion(hora);
            redirectAttributes.addFlashAttribute("success", "Hora agregada");

        } else{
            redirectAttributes.addFlashAttribute("warning", "No puedes asignar bloques de mas de 2 horas para una misma materia de tipo basica");
        }

        redirectAttributes.addAttribute("carrera", carrera);
        redirectAttributes.addAttribute("grado", grado);
        redirectAttributes.addAttribute("seccion", seccion);

        return "redirect:/horarios/asignarHoras";
    }

    @PostMapping("/actualizarHora")
    public String editarhora(@RequestParam("idAsignacionHorario") int asignacionHorarioID,
            @RequestParam("asignacionSeleccionada") int asignacionID,
            @RequestParam("horaSeleccionada") int horarioBaseID,
            @RequestParam(value = "carrera", required = false) String carrera,
            @RequestParam(value = "grado", required = false) String grado,
            @RequestParam(value = "seccion", required = false) String seccion,
            RedirectAttributes redirectAttributes) {

        carrera = extraerPrimerValor(carrera);
        grado = extraerPrimerValor(grado);
        seccion = extraerPrimerValor(seccion);

        AsignacionHorario hora = new AsignacionHorario();
        Asignacion asignacion = new Asignacion();
        HorarioBase horarioBase = new HorarioBase();

        hora.setIdAsignacionHorario(asignacionHorarioID);

        asignacion.setIdAsignacion(asignacionID);
        hora.setAsignacion(asignacion);

        horarioBase.setIdHorarioBase(horarioBaseID);
        hora.setHorarioBase(horarioBase);

        horarioService.guardarHoraAsignacion(hora);

        redirectAttributes.addAttribute("carrera", carrera);
        redirectAttributes.addAttribute("grado", grado);
        redirectAttributes.addAttribute("seccion", seccion);

        return "redirect:/horarios/editarHoras";
    }

    @GetMapping("/eliminarHora/{id}")
    public String eliminarDocente(@PathVariable("id") Integer idAsignacionHorario,
            @RequestParam(value = "carrera", required = false) String carrera,
            @RequestParam(value = "grado", required = false) String grado,
            @RequestParam(value = "seccion", required = false) String seccion,
            RedirectAttributes redirectAttributes) {

        carrera = extraerPrimerValor(carrera);
        grado = extraerPrimerValor(grado);
        seccion = extraerPrimerValor(seccion);
        // System.out.println(grado + carrera + seccion);

        horarioService.eliminarHoraAsignacion(idAsignacionHorario);
        redirectAttributes.addFlashAttribute("warning", "La hora de clase se elimino");

        redirectAttributes.addAttribute("carrera", carrera);
        redirectAttributes.addAttribute("grado", grado);
        redirectAttributes.addAttribute("seccion", seccion);

        return "redirect:/horarios/editarHoras";
    }

    // Se encarga de preparar la vista tanto para asignación y edición
    // Se utiliza un metodo porque ambas vistas usan los mismos datos
    // Cuando esEdicion = True se envía un dato adicional, necesario para la edición
    private void prepararVista(Model model, String carrera, String grado, String seccion, boolean esEdicion) {
        boolean formSubmitted = (carrera != null || grado != null || seccion != null);

        carrera = normalizarParametro(carrera);
        grado = normalizarParametro(grado);
        seccion = normalizarParametro(seccion);

        List<Bachillerato> listaCarreras = bachilleratoService.listaCarrera();
        List<Integer> horasExistentes = null;
        Bachillerato bachillerato = null;

        if (carrera != null && grado != null && seccion != null) {
            bachillerato = bachilleratoService.obtenerBachilleratoEspecifico(carrera, Integer.parseInt(grado), seccion);
        }

        if (bachillerato != null && bachillerato.getCodigoBachillerato() != 0) {
            horasExistentes = horarioService
                    .obtenerIdHorariosBasePorCodigoBachillerato(bachillerato.getCodigoBachillerato());
        }

        List<Asignacion> listaAsignaciones = null;
        List<AsignacionHorario> horasDeClase = null;
        if (bachillerato != null) {
            listaAsignaciones = asignacionService
                    .listarAsignacionesCodigoBachillerato(bachillerato.getCodigoBachillerato());

            if (esEdicion) {
                horasDeClase = horarioService.obtenerHorasAsignadas(bachillerato.getCodigoBachillerato());
            }
        }

        // Agrega los atributos al modelo
        model.addAttribute("bachilleratos", listaCarreras);
        model.addAttribute("carrera", carrera);
        model.addAttribute("grado", grado);
        model.addAttribute("seccion", seccion);
        model.addAttribute("bachillerato", bachillerato);
        model.addAttribute("formSubmitted", formSubmitted);
        model.addAttribute("asignaciones", listaAsignaciones != null ? listaAsignaciones : List.of());
        model.addAttribute("horasDeClase", horasDeClase != null ? horasDeClase : List.of());

        // Agrega una lista de idHorarioBase de las horas de clase de una sección
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String envioHoras = objectMapper.writeValueAsString(horasExistentes);
            model.addAttribute("envioHoras", envioHoras);
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
    }

    // Convierte las cadenas vacías a null para evitar problemas con las consultas
    private String normalizarParametro(String parametro) {
        if (parametro != null && parametro.isEmpty()) {
            return null;
        }
        return parametro;
    }

    // Controla la repeticion de parametros
    private String extraerPrimerValor(String parametro) {
        if (parametro != null && parametro.contains(",")) {
            return parametro.split(",")[0];
        }
        return parametro;
    }
}
