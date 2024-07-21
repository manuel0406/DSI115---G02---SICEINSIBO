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

    @GetMapping("/AsignacionMateria/")
    public String fallaEnlace(){
        return "redirect:/GestionMaterias";
    }

    @GetMapping("/AsignacionMateria/{id}")
    public String asignacionMateria(Model model,  @PathVariable("id") int idMateria, RedirectAttributes attribute){
        
        // ID INVALIDA
        if (idMateria == 0) {
            return "redirect:/GestionMaterias";
        }
        
        
        Materia materia = materiasService.obtenerMateriaPorId(idMateria);
        List<Asignacion> listadoAsignaciones = asignacionService.listarAsignaciones(idMateria);
        model.addAttribute("materia", materia);
        model.addAttribute("asignaciones", listadoAsignaciones);
        return "Administrativo/GestionMaterias/AsignacionMateria";
    }

    @GetMapping("/NuevaAsignacion/{id}")
    public String nuevaAsignacion(Model model, @PathVariable("id") int idMateria, RedirectAttributes attribute) {
    
        // ID INVALIDA
        if (idMateria == 0) {
            return "redirect:/GestionMaterias";
        }
    
        // Obtener la materia correspondiente al ID
        Materia materia = materiasService.obtenerMateriaPorId(idMateria);
    
        // Obtener lista de docentes que tienen un máximo de tres materias asignadas
        List<String> docentesMax = asignacionService.listarDocentesMaximo();
        List<String> nuevaLista = new ArrayList<>();
    
        // Filtrado de docentes con el máximo de tres materias
        for (String docente : docentesMax) {
            // Buscar asignaciones del docente para la materia específica
            List<Asignacion> materiasAsignadas = asignacionService.buscarDocenteMateria(idMateria, docente);
            if (materiasAsignadas.isEmpty()) {
                // Si no hay asignaciones para esta materia, agregar el docente a la nueva lista
                nuevaLista.add(docente);
            }
        }
    
        // Actualizar la lista de docentesMax con los docentes filtrados
        docentesMax.clear();
        docentesMax.addAll(nuevaLista);
    
        // Obtener listado completo de docentes
        List<Docente> docentes = docenteService.listarDocenteAsignacion();
    
        // Eliminar de la lista de docentes aquellos cuyo duiDocente está en la lista de docentesMax
        docentes.removeIf(docente -> docentesMax.contains(docente.getDuiDocente()));
    
        // Obtener las asignaciones para la materia específica
        List<Asignacion> asignaciones = asignacionService.obtenerAsignacionExistente(idMateria);
    
        // Obtener los códigos de bachillerato de las asignaciones existentes
        Set<String> codigosBachilleratoAsignaciones = asignaciones.stream()
                    .map(asignacion -> asignacion.getBachillerato().getCodigoBachillerato())
                    .collect(Collectors.toSet());
    
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
}
