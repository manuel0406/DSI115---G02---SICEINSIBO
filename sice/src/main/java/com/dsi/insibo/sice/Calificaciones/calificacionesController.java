package com.dsi.insibo.sice.Calificaciones;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
// import com.dsi.insibo.sice.Administrativo.Bachilleratos.Servicios.BachilleratoService;
import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.AsignacionService;
import com.dsi.insibo.sice.Expediente_alumno.AlumnoService;
import com.dsi.insibo.sice.entity.Actividad;
import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Asignacion;
import com.dsi.insibo.sice.entity.Nota;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/Calificaciones")
public class calificacionesController {
	// @Autowired
	// private BachilleratoService bachilleratoService;
	// @Autowired
	// private PeriodoService periodoService;
	// @Autowired
	// private MateriaService materiaService;
	@Autowired
	private AlumnoService alumnoService;
	@Autowired
	private NotaService notaService;
	@Autowired
	ActividadService actividadService;
	@Autowired
	AsignacionService asignacionService;

	// @Autowired
	// private MateriaBachilleratoRepository materiaBachilleratoRepository;

	@GetMapping("/calificaciones")
	public String verCalificaciones(Model model) {

		// List<Bachillerato> listaBachilleratos =
		// bachilleratoService.listaBachilleratos();
		// model.addAttribute("grados", listaBachilleratos);

		// List<Periodo> listaPeriodos = periodoService.listaPeriodos();
		// model.addAttribute("periodos", listaPeriodos);

		// List<Alumno> alumnos =
		// alumnoService.obtenerAlumnosPorMateriaYPeriodo(codMateria, idPeriodo);

		// List<Materia> listaMaterias = materiaService.listaMaterias();
		// model.addAttribute("materias", listaMaterias);
		// model.addAttribute("materias", new ArrayList<Materia>());
		// model.addAttribute("alumnos", new ArrayList<Alumno>());
		List<Actividad> listadoActividades = actividadService.listaActividades("33333333-3", 14);
		model.addAttribute("listadoActividades", listadoActividades);

		return "Calificaciones/vistaCalificaciones";
	}

	@GetMapping("/registro/{idActividad}")
	public String registro(Model model, @PathVariable("idActividad") int idActividad) {

		Actividad actividad = actividadService.buscarActividadPorId(idActividad);
		List<Alumno> listaAlumno = alumnoService
				.alumnosPorBachilerato(actividad.getAsignacion().getBachillerato().getCodigoBachillerato());
		List<Nota> listaNotas = notaService.notasPorBachilleratoActivdad(actividad.getIdActividad());
		Asignacion asignacion = asignacionService.asignacionParaActividad(
				actividad.getAsignacion().getDocente().getDuiDocente(),
				actividad.getAsignacion().getBachillerato().getCodigoBachillerato());

		// se crea un Set para almacenar los IDs de alumnos que ya tienen notas
		Set<Integer> idsAlumnosConNotas = new HashSet<>();

		// Llenar el Set con los IDs de los alumnos que ya tienen una nota
		for (Nota nota : listaNotas) {
			idsAlumnosConNotas.add(nota.getAlumno().getIdAlumno());
		}

		// Iterar sobre la lista de alumnos
		for (Alumno alumno : listaAlumno) {
			// Verificar si el alumno ya tiene una nota
			if (!idsAlumnosConNotas.contains(alumno.getIdAlumno())) {
				System.out.println("Se puede guardar: " + alumno.getNombreAlumno());
				// Al no ecnontrar registro se crea una nueva nota
				Nota nota = new Nota();
				nota.setNotaObtenida(0);
				nota.setActividad(actividad);
				nota.setAlumno(alumno);
				notaService.guardarNota(nota);
			}
		}
		Nota nota= new Nota();
		listaNotas = notaService.notasPorBachilleratoActivdad(3);

		model.addAttribute("titulo", "Registro notas");
		model.addAttribute("asignacion", asignacion);
		model.addAttribute("listadoNotas", listaNotas);
		model.addAttribute("nota", nota);
		return "Calificaciones/registroCalificaciones";
	}
	@PostMapping("/registro/add")
	public String guardarCalificacion(@ModelAttribute Nota nota) {
	
		notaService.guardarNota(nota);
		// System.out.println( "idNota "+ nota.getIdNota()+" idAlumno: "+nota.getAlumno().getIdAlumno()+ " idActividad: "+ nota.getActividad().getIdActividad()+" nota: "+nota.getNotaObtenida());
		
		return "redirect:/Calificaciones/registro/"+nota.getActividad().getIdActividad();
	}
	

	/*
	 * @GetMapping("calificaciones/materiasPorBachillerato")
	 * 
	 * @ResponseBody
	 * public List<Materia> getMateriasPorBachillerato(@RequestParam String
	 * codigoBachillerato) {
	 * List<MateriaBachillerato> materiaBachilleratoList =
	 * materiaBachilleratoRepository.findByBachilleratoCodigoBachillerato(
	 * codigoBachillerato);
	 * return
	 * materiaBachilleratoList.stream().map(MateriaBachillerato::getMateria).collect
	 * (Collectors.toList());
	 * }
	 * 
	 * @GetMapping("calificaciones/alumnosPorBachillerato")
	 * public String getAlumnosPorBachillerato(@RequestParam String
	 * codigoBachillerato, Model model) {
	 * List<Alumno> alumnos =
	 * alumnoService.findAlumnosByBachilleratoCodigoBachillerato(codigoBachillerato)
	 * ;
	 * model.addAttribute("alumnos", alumnos);
	 * 
	 * // Recuperar y agregar nuevamente la lista de Bachilleratos
	 * List<Bachillerato> listaBachilleratos =
	 * bachilleratoService.listaBachilleratos();
	 * model.addAttribute("grados", listaBachilleratos);
	 * return "Calificaciones/vistaCalificaciones";
	 * }
	 * 
	 * @GetMapping("/calificaciones/Alumno/{nie}")
	 * public String informacionAlumno(@PathVariable("nie") int nie, Model model) {
	 * // Recuperar la información del alumno por NIE
	 * Alumno alumno = alumnoService.buscarPorIdAlumno(nie);
	 * 
	 * List<Materia> materias = materiaService.listaMaterias();
	 * List<Periodo> periodos = periodoService.listaPeriodos();
	 * 
	 * model.addAttribute("materias", materias);
	 * model.addAttribute("periodos", periodos);
	 * 
	 * if (alumno != null) {
	 * model.addAttribute("alumno", alumno);
	 * 
	 * List<Nota> notas = notaService.findNotasByAlumno(alumno);
	 * // Asumiendo que tienes un servicio para buscar notas por alumno
	 * model.addAttribute("notas", notas);
	 * // Puedes agregar más atributos al modelo si es necesario, como las notas del
	 * alumno
	 * // model.addAttribute("notas", notasService.findNotasByAlumnoNie(nie));
	 * } else {
	 * // Manejar el caso donde no se encuentra el alumno (opcional)
	 * model.addAttribute("error", "Alumno no encontrado");
	 * }
	 * 
	 * 
	 * return "Calificaciones/AlumnoCalificaciones";
	 * }
	 */
}
