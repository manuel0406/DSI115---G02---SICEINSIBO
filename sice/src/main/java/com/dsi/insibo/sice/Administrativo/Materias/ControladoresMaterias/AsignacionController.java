package com.dsi.insibo.sice.Administrativo.Materias.ControladoresMaterias;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.AsignacionService;
import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.BachilleratosService;
import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.MateriasService;
import com.dsi.insibo.sice.Expediente_docente.Docentes.DocenteService;
import com.dsi.insibo.sice.entity.Asignacion;
import com.dsi.insibo.sice.entity.Bachillerato;
import com.dsi.insibo.sice.entity.Docente;
import com.dsi.insibo.sice.entity.Materia;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class AsignacionController {

    // Materias
    @Autowired
    private MateriasService materiasService;
    // Bachilleratos
    @Autowired
    private BachilleratosService bachilleratosService;
    // Profesores
    @Autowired
    private DocenteService docenteService;
    @Autowired
    private AsignacionService asignacionService;

    @GetMapping("/AsignacionMateria")
    public String asignacionMateriasGeneral(Model model){

        String titulo = "Asignaciones de Materias";                                         // Asignar titulo
        List<Asignacion> listadoAsignaciones = asignacionService.obtenerTodaAsignaciones(); // Obtener todas las asignaciones
        List<Docente> docentes = docenteService.listarDocenteAsignacion();                  // Obtener maestros

        // Construccion de infromacion a front-end
        model.addAttribute("asignaciones", listadoAsignaciones);
        model.addAttribute("docentes", docentes);
        model.addAttribute("titulo", titulo);
        return "Administrativo/GestionMaterias/AsignacionMateriaGeneral";
    }

    @GetMapping("/AsignacionMateria/{id}")
    public String asignacionMateria(Model model,  @PathVariable("id") int idMateria, RedirectAttributes attribute){
        
        // ID INVALIDA
        if (idMateria == 0) {
            return "redirect:/GestionMaterias";
        }
        
        // Para obtener el titulo
        Materia materia = materiasService.obtenerMateriaPorId(idMateria);                       // Informacion de la materia
        String titulo = "Asignaciones a: " + materia.getNomMateria();

        // Para obtener todas las asignaciones
        List<Asignacion> listadoAsignaciones = asignacionService.listarAsignaciones(idMateria); // Listado de asignaciones de la materia

        // Filtrado de maximo de docentes - obtener docentes sin repetir
        List<String> docentesMax = asignacionService.listarDocentesMaximo(idMateria);
        List<Docente> docentes = docenteService.listarDocenteAsignacion();
        docentes.removeIf(docente -> docentesMax.contains(docente.getDuiDocente())); // Eliminar de la lista de docentes aquellos cuyo duiDocente está en la lista de docentesMax
        
        model.addAttribute("titulo", titulo);
        model.addAttribute("asignaciones", listadoAsignaciones);
        model.addAttribute("idMateria", idMateria);
        model.addAttribute("docentes", docentes);
        return "Administrativo/GestionMaterias/AsignacionMateria";
    }

    @GetMapping("/NuevaAsignacion")
    public String nuevaAsignacionGeneral(Model model){

        // Obtenemos las materias
        List<Materia> materias = materiasService.obtenerMaterias();

        // Obtenemos los bachilleratos
        List<Bachillerato> primeros = bachilleratosService.obtenerPrimeros();
        List<Bachillerato> segundos = bachilleratosService.obtenerSegundos();
        List<Bachillerato> terceros = bachilleratosService.obtenerTerceros();

        // Obtenemos los docentes
        List<Docente> docentes = docenteService.listarDocenteAsignacion();

        // Obtenemos las asignaciones
        List<Asignacion> asignaciones = asignacionService.obtenerTodaAsignaciones();


        // Convertir la listas  a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String materiasJson = objectMapper.writeValueAsString(materias);
            String docentesJson = objectMapper.writeValueAsString(docentes);
            String asignacionesJson = objectMapper.writeValueAsString(asignaciones);
            String primerosJson = objectMapper.writeValueAsString(primeros);
            String segundosJson = objectMapper.writeValueAsString(segundos);
            String tercerosJson = objectMapper.writeValueAsString(terceros);

            model.addAttribute("materiasJson", materiasJson);
            model.addAttribute("docentesJson", docentesJson);
            model.addAttribute("asignacionesJson", asignacionesJson);
            model.addAttribute("primerosJson", primerosJson);
            model.addAttribute("segundosJson", segundosJson);
            model.addAttribute("tercerosJson", tercerosJson);
        } catch (JsonProcessingException e) {
            System.out.println("El error es: " + e);
        }

        // Crear la vista
        model.addAttribute("materias", materias);


        return "Administrativo/GestionMaterias/NuevaAsignacionGeneral";
        
    }

    @GetMapping("/NuevaAsignacion/{id}")
    public String nuevaAsignacion(Model model, @PathVariable("id") int idMateria, RedirectAttributes attribute) {
    
        // ID INVALIDA
        if (idMateria == 0) {
            return "redirect:/GestionMaterias";
        }
    
        // Obtener la materia correspondiente al ID
        Materia materia = materiasService.obtenerMateriaPorId(idMateria);
    
        // Filtrado de maximo de docentes
        List<String> docentesMax = asignacionService.listarDocentesMaximo(idMateria);
        List<Docente> docentes = docenteService.listarDocenteAsignacion();
        docentes.removeIf(docente -> docentesMax.contains(docente.getDuiDocente())); // Eliminar de la lista de docentes aquellos cuyo duiDocente está en la lista de docentesMax
    
        // Obtener las asignaciones para la materia específica
        List<Asignacion> asignaciones = asignacionService.obtenerAsignacionExistente(idMateria);
        Set<String> codigosBachilleratoAsignaciones = asignaciones.stream()
                    .map(asignacion -> asignacion.getBachillerato().getCodigoBachillerato())
                    .collect(Collectors.toSet());                                               // Codigo de las asignaciones existentes
    
        // Obtener listas de bachilleratos filtrados por los códigos no asignados
        List<Bachillerato> primeros = bachilleratosService.obtenerPrimeros().stream()
                .filter(bachillerato -> !codigosBachilleratoAsignaciones.contains(bachillerato.getCodigoBachillerato()))
                .collect(Collectors.toList());
    
        List<Bachillerato> segundos = bachilleratosService.obtenerSegundos().stream()
                .filter(bachillerato -> !codigosBachilleratoAsignaciones.contains(bachillerato.getCodigoBachillerato()))
                .collect(Collectors.toList());
    
        List<Bachillerato> terceros = bachilleratosService.obtenerTerceros().stream()
                .filter(bachillerato -> !codigosBachilleratoAsignaciones.contains(bachillerato.getCodigoBachillerato()))
                .collect(Collectors.toList());
    
        // Añadir los atributos necesarios al modelo para la vista
        model.addAttribute("primeros", primeros);
        model.addAttribute("segundos", segundos);
        model.addAttribute("terceros", terceros);
        model.addAttribute("materia", materia);
        model.addAttribute("docentes", docentes);
    
        // Retornar la vista correspondiente
        return "Administrativo/GestionMaterias/NuevaAsignacion";
    }
    
    @PostMapping("/crearAsignacion")
    public String crearAsignacionMateria(
            @RequestParam("idMateria") int idMateria,
            @RequestParam("duiDocente") String duiDocente,
            @RequestParam("codigoBachillerato") List<String> codigosBachillerato,
            RedirectAttributes redirectAttributes) {
        
        Docente docente = docenteService.buscarPorIdDocente(duiDocente);
        Materia materia = materiasService.obtenerMateriaPorId(idMateria);

        List<Asignacion> asignaciones = new ArrayList<>();
        for (String codigo : codigosBachillerato) {
            Bachillerato bachillerato = bachilleratosService.obtenerBachilleratoPorId(codigo);
            
            Asignacion asignacion = new Asignacion();
            asignacion.setMateria(materia);
            asignacion.setDocente(docente);
            asignacion.setBachillerato(bachillerato);
            asignaciones.add(asignacion);
        }

        asignacionService.saveAsignaciones(asignaciones);
        return "redirect:/AsignacionMateria/" + idMateria; // Redireccionar a la página deseada después de guardar
    }

    @PostMapping("/actualizarAsignacion")
    public String actualizarAsignacion(
        @RequestParam("idAsignacion") int idAsignacion,
        @RequestParam("materia") String nomMateria,  // Nombre actualizado para coincidir con el formulario
        @RequestParam("bachillerato") String codigoBachillerato, // Nombre actualizado para coincidir con el formulario
        @RequestParam("duiDocente") String duiDocente,
        @RequestParam("idMateria") int idMateria, // Asegúrate de recibir idMateria
        RedirectAttributes redirectAttributes) 
    {
        // Crear o encontrar la entidad Asignacion
        Asignacion asignacion = asignacionService.buscarAsignacion(idAsignacion);
        if (asignacion != null) {
            // Actualiza los detalles de la asignación
            Docente docenteNuevo = docenteService.buscarPorIdDocente(duiDocente);
            if (docenteNuevo != null) {
                asignacion.setDocente(docenteNuevo);
            } else {
                // Si no se encuentra el docente, redirige con un mensaje de error
                redirectAttributes.addFlashAttribute("error", "Docente no encontrado.");
                return "redirect:/AsignacionMateria/" + idMateria;
            }
    
            // Actualiza otros detalles de la asignación
            // Por ejemplo, actualiza la materia y el bachillerato si es necesario
            // asignacion.setMateria(nomMateria);
            // asignacion.setCodigoBachillerato(codigoBachillerato);
    
            // Guarda la asignación actualizada
            asignacionService.saveAsignacion(asignacion);
    
            // Redirige con un mensaje de éxito si lo deseas
            redirectAttributes.addFlashAttribute("success", "Asignación actualizada correctamente.");
        } else {
            // Si no se encuentra la asignación, redirige con un mensaje de error
            redirectAttributes.addFlashAttribute("error", "Asignación no encontrada.");
        }
    
        // Redirige a la página correcta usando idMateria
        return "redirect:/AsignacionMateria/" + idMateria;
    }
    

    @GetMapping("/eliminarAsignacion")
    public String eliminarAsignacion(
        @RequestParam("idMateria") int idMateria,
        @RequestParam("idAsignacion") int idAsignacion,
        RedirectAttributes redirectAttributes) {
    
        // Buscar la asignación por idAsignacion
        Asignacion asignacion = asignacionService.buscarAsignacion(idAsignacion);
        
        if (asignacion != null) {
            // Eliminar la asignación
            asignacionService.eliminarAsignacion(asignacion);
            
            // Redirigir con mensaje de éxito
            redirectAttributes.addFlashAttribute("mensaje", "Asignación eliminada con éxito.");
        } else {
            // Redirigir con mensaje de error si la asignación no se encuentra
            redirectAttributes.addFlashAttribute("error", "Asignación no encontrada.");
        }
        
        // Redirigir a la página de asignación de materia
        return "redirect:/AsignacionMateria/" + idMateria;
    }
    
       
    
}
