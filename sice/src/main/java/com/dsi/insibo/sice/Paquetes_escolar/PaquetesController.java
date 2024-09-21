package com.dsi.insibo.sice.Paquetes_escolar;

import java.time.LocalDate;
import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.Administrativo.Bachilleratos.Servicios.BachilleratoService;
import com.dsi.insibo.sice.Paquetes_escolar.Donaciones.InventarioDonacionService;
import com.dsi.insibo.sice.Seguridad.SeguridadService.SessionService;
import com.dsi.insibo.sice.entity.Bachillerato;
import com.dsi.insibo.sice.entity.InventarioDonacion;

@Controller
@RequestMapping("/paquetes")
public class PaquetesController {
    @Autowired
    private InventarioDonacionService inventarioDonacionService;

    @Autowired
    private BachilleratoService bachilleratoService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/inicio")
    public String paquetesInicio() {
        return "Paquetes_escolares/inicioPaquetes";
    }

    // Donaciones
    @GetMapping("/listaDonaciones")
    public String paquetesDonaciones(Model model) {
        model.addAttribute("donaciones", inventarioDonacionService.obtenerTodasLasDonaciones());
        return "Paquetes_escolares/listaDonaciones";
    }

    // Guardar donaciones
    @PostMapping("/guardar")
    public String guardarDonacion(InventarioDonacion donacion, RedirectAttributes attributes) {
        if ("0".equals(donacion.getTipoPrenda())) {
            attributes.addFlashAttribute("error", "¡Debe seleccionar un tipo de prenda válido!");
            return "redirect:/paquetes/listaDonaciones";
        }

        // Convertir talla a mayusculas
        donacion.setTallaPrenda(donacion.getTallaPrenda().toUpperCase());

        // Verificar si ya existe una donación con la combinación de tipoPrenda y
        // tallaPrenda
        if (inventarioDonacionService.existeDonacion(donacion.getTipoPrenda(), donacion.getTallaPrenda())) {
            attributes.addFlashAttribute("error", "¡Ya existe una donación con este tipo y talla de prenda!");
            return "redirect:/paquetes/listaDonaciones";
        }

        try {
            inventarioDonacionService.guardarDonacion(donacion);
            attributes.addFlashAttribute("success", "¡Donación guardada con éxito!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "¡Error al guardar la donación!");
        }

        return "redirect:/paquetes/listaDonaciones";
    }

    // Modificar la cantidad de donacion
    @PostMapping("/modificarCantidad")
    public String modificarCantidad(
            @RequestParam("donacionId") int donacionId,
            @RequestParam("nuevaCantidad") int nuevaCantidad,
            RedirectAttributes redirectAttributes) {

        // Obtener la cantidad actual desde el servicio
        int cantidadActual = inventarioDonacionService.obtenerCantidadPorId(donacionId);

        // Calcular la nueva cantidad sumando la cantidad actual y la nueva cantidad
        // ingresada
        int cantidadTotal = cantidadActual + nuevaCantidad;

        // Llamar al servicio para actualizar la cantidad
        boolean success = inventarioDonacionService.actualizarCantidadDonacion(donacionId, cantidadTotal);

        if (success) {
            redirectAttributes.addFlashAttribute("success", "Cantidad actualizada con éxito.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo actualizar la cantidad.");
        }

        return "redirect:/paquetes/listaDonaciones";
    }

    // Modificar la cantidad de donación
    @PostMapping("/modificarMenosCantidad")
    public String modificarMenosCantidad(
            @RequestParam("donacionId") int donacionId,
            @RequestParam("nuevaCantidad") int nuevaCantidad,
            RedirectAttributes redirectAttributes) {

        // Obtener la cantidad actual desde el servicio
        int cantidadActual = inventarioDonacionService.obtenerCantidadPorId(donacionId);
        if (cantidadActual >= nuevaCantidad) {
            // Calcular la nueva cantidad sumando la cantidad actual y la nueva cantidad
            // ingresada
            int cantidadTotal = cantidadActual - nuevaCantidad;

            // Llamar al servicio para actualizar la cantidad
            boolean success = inventarioDonacionService.actualizarCantidadDonacion(donacionId, cantidadTotal);

            if (success) {
                redirectAttributes.addFlashAttribute("success", "Cantidad actualizada con éxito.");
            } else {
                redirectAttributes.addFlashAttribute("error", "No se pudo actualizar la cantidad.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo actualizar la cantidad.");
        }

        return "redirect:/paquetes/listaDonaciones";
    }

    // eliminar donacion solo si esta en 0(cero) validacion en vista
    @PostMapping("/eliminar/{id}")
    public String eliminarDonacion(@PathVariable int id, RedirectAttributes redirectAttributes) {
        inventarioDonacionService.eliminarDonacion(id);
        redirectAttributes.addFlashAttribute("success", "Donación eliminada con éxito.");
        return "redirect:/paquetes/listaDonaciones";
    }

    @GetMapping("/listaAlumnos")
    public String listaAlumnos(Model model) {
        Integer codigoBachillerato = 27; // O puedes obtenerlo dinámicamente como lo necesites

        // Obtener la lista de alumnos
        List<List<Object>> alumnos = inventarioDonacionService.obtenerAlumnosPorCodigoBachillerato(codigoBachillerato);

        // Agregar la lista de alumnos al modelo
        model.addAttribute("alumnos", alumnos);
        return "alumnos/lista";
    }

    @GetMapping("/entregaLista")
    public String paquetesEntregaLista(Model model) {
        // Obtener el año académico activo desde el servicio
        Integer idAnioAcademico = inventarioDonacionService.obtenerAnioAcademicoActivo();

        // Obtener los bachilleratos basados en el año académico activo desde el
        // servicio
        List<Object[]> bachilleratos = inventarioDonacionService.obtenerBachilleratosPorAnioAcademico(idAnioAcademico);

        // Agregar los bachilleratos al modelo
        model.addAttribute("bachilleratos", bachilleratos);

        return "Paquetes_escolares/generarEntregas";
    }

    // Endpoint para obtener los grados según la carrera seleccionada
    @GetMapping("/grados")
    @ResponseBody
    public List<Integer> obtenerGrados(@RequestParam("carrera") String carrera) {
        return inventarioDonacionService.obtenerGradosPorCarrera(carrera);
    }

    // Endpoint para obtener las secciones según la carrera y el grado seleccionados
    @GetMapping("/secciones")
    @ResponseBody
    public List<String> obtenerSecciones(@RequestParam("carrera") String carrera, @RequestParam("grado") int grado) {
        return inventarioDonacionService.obtenerSeccionesPorCarreraYGrado(carrera, grado);
    }
}
