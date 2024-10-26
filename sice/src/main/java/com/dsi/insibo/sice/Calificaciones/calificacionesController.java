package com.dsi.insibo.sice.Calificaciones;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.dsi.insibo.sice.Administrativo.Bachilleratos.Servicios.BachilleratoService;
import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.AsignacionService;
import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.MateriasService;
import com.dsi.insibo.sice.Expediente_alumno.AlumnoService;
import com.dsi.insibo.sice.Expediente_docente.Docentes.DocenteService;
import com.dsi.insibo.sice.Seguridad.SeguridadService.SessionService;
import com.dsi.insibo.sice.entity.Actividad;
import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Asignacion;
import com.dsi.insibo.sice.entity.Bachillerato;
import com.dsi.insibo.sice.entity.Materia;
import com.dsi.insibo.sice.entity.Nota;
import com.dsi.insibo.sice.entity.Periodo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/Calificaciones")
public class calificacionesController {
	@Autowired
	private BachilleratoService bachilleratoService;
	@Autowired
	private MateriasService materiaService;
	@Autowired
	private AlumnoService alumnoService;
	@Autowired
	private NotaService notaService;
	@Autowired
	private ActividadService actividadService;
	@Autowired
	private AsignacionService asignacionService;
	@Autowired
	private SessionService sesion;
	@Autowired
	private PeriodoService periodoService;
	@Autowired
	private DocenteService docenteService;

	// @Autowired
	// private MateriaBachilleratoRepository materiaBachilleratoRepository;

	@GetMapping("/{idMateria}/{codigoBachillerato}")
	public String verCalificaciones(Model model, @PathVariable("idMateria") int idMateria,
			@PathVariable("codigoBachillerato") int codigoBachillerato,
			@RequestParam(value = "pe", required = false) String pe) {
		String dui = sesion.duiSession();
		// System.out.println(dui + " " + codigoBachillerato);

		if (pe != null && pe.isEmpty()) {
			pe = null;
		}

		Asignacion asignacion = asignacionService.asignacionParaActividad(dui, idMateria, codigoBachillerato);
		if (asignacion.getMateria().getTipoMateria().equals("Módulo")) {
			pe = "1";
			System.out.println("entro");

		}
		List<Actividad> listadoActividades = actividadService.listaActividades(dui,
				asignacion.getMateria().getIdMateria(), pe, codigoBachillerato);
		List<Nota> listaNotas = notaService.listaNotaActividadBachillerato(asignacion.getDocente().getDuiDocente(),
				asignacion.getBachillerato().getCodigoBachillerato(), pe);
		List<Alumno> listaAlumnos = alumnoService
				.alumnosPorBachilerato(asignacion.getBachillerato().getCodigoBachillerato());

		// Crear un mapa para almacenar las notas por alumno y actividad
		Map<Integer, Map<Integer, Nota>> notasPorAlumno = new HashMap<>();
		// Crear un mapa para almacenar el total de notas por alumno
		Map<Integer, Double> totalNotasPorAlumno = new HashMap<>();

		// Organizar las notas por alumno y actividad
		for (Nota nota : listaNotas) {
			int idAlumno = nota.getAlumno().getIdAlumno();
			int idActividad = nota.getActividad().getIdActividad();

			notasPorAlumno
					.computeIfAbsent(idAlumno, k -> new HashMap<>())
					.put(idActividad, nota);

			// Calcular el total de notas por alumno
			BigDecimal notafinal = new BigDecimal(
					(double) (nota.getNotaObtenida()) * (nota.getActividad().getPonderacionActividad() / 100));
			notafinal = notafinal.setScale(2, RoundingMode.HALF_UP);
			totalNotasPorAlumno.merge(idAlumno, notafinal.doubleValue(), Double::sum);
		}
		// if (listadoActividades.size() == 0) {
		// System.out.println("La lista está vacía.");
		// } else if (listadoActividades.size() >= 0) {
		// System.out.println("La lista tiene elementos.");
		// }
		// Obtén tipos únicos

		// Contar las actividades por tipo
		Map<String, Long> conteoPorTipo = listadoActividades.stream()
				.collect(Collectors.groupingBy(Actividad::getTipoActividad, Collectors.counting()));

		// Convertir el mapa a una lista de ActividadDTO
		List<ActividadDTO> actividadDTOList = conteoPorTipo.entrySet().stream()
				.map(entry -> new ActividadDTO(entry.getKey(), entry.getValue().intValue()))
				.collect(Collectors.toList());

		// Agregar la lista al modelo
		model.addAttribute("actividadDTOList", actividadDTOList);

		List<Periodo> periodos = periodoService.listaPeriodos();
		model.addAttribute("periodos", periodos);
		model.addAttribute("listadoActividades", listadoActividades);
		model.addAttribute("listaAlumnos", listaAlumnos);
		model.addAttribute("notasPorAlumno", notasPorAlumno);
		model.addAttribute("totalNotasPorAlumno", totalNotasPorAlumno);
		model.addAttribute("asignacion", asignacion);
		model.addAttribute("pe", pe);

		return "Calificaciones/vistaCalificaciones";
	}

	@PreAuthorize("hasAnyRole('DOCENTE')")
	@GetMapping("/registro/{idActividad}")
	public String registro(Model model, @PathVariable("idActividad") int idActividad) {

		Actividad actividad = actividadService.buscarActividadPorId(idActividad);
		List<Alumno> listaAlumno = alumnoService
				.alumnosPorBachilerato(actividad.getAsignacion().getBachillerato().getCodigoBachillerato());
		List<Nota> listaNotas = notaService.notasPorBachilleratoActivdad(actividad.getIdActividad());
		Asignacion asignacion = asignacionService.asignacionParaActividad(
				actividad.getAsignacion().getDocente().getDuiDocente(),
				actividad.getAsignacion().getMateria().getIdMateria(),
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
				// System.out.println("Se puede guardar: " + alumno.getNombreAlumno());
				// Al no ecnontrar registro se crea una nueva nota
				Nota nota = new Nota();
				nota.setNotaObtenida(0);
				nota.setActividad(actividad);
				nota.setAlumno(alumno);
				notaService.guardarNota(nota);
			}
		}
		Nota nota = new Nota();
		listaNotas = notaService.notasPorBachilleratoActivdad(actividad.getIdActividad());

		model.addAttribute("titulo", "Registro notas");
		model.addAttribute("asignacion", asignacion);
		model.addAttribute("actividad", actividad);
		model.addAttribute("listadoNotas", listaNotas);
		model.addAttribute("nota", nota);
		return "Calificaciones/registroCalificaciones";
	}

	@PostMapping("/registro/add")
	public String guardarCalificacion(@ModelAttribute Nota nota, RedirectAttributes redirectAttributes) {

		nota.setFechaModificacion(new Date());
		notaService.guardarNota(nota);

		redirectAttributes.addFlashAttribute("success", "Calificación actualizada exitosamente.");
		return "redirect:/Calificaciones/registro/" + nota.getActividad().getIdActividad();
	}

	// verAlumno(Model model,
	// @RequestParam(value = "carrera", required = false) String carrera,
	// @RequestParam(value = "grado", required = false) String grado,
	// @RequestParam(value = "seccion", required = false) String seccion,

	@GetMapping("/General")
	public String verCalificacioneGeneral(Model model,
			@RequestParam(value = "idMateria", required = false) String idMateria,
			@RequestParam(value = "docente", required = false) String docente,
			@RequestParam(value = "carrera", required = false) String carrera,
			@RequestParam(value = "grado", required = false) String grado,
			@RequestParam(value = "seccion", required = false) String seccion,
			@RequestParam(value = "pe", required = false) String pe) {

		// Validaciones para los parámetros vacíos
		if (carrera != null && carrera.isEmpty())
			carrera = null;
		if (grado != null && grado.isEmpty())
			grado = null;
		if (seccion != null && seccion.isEmpty())
			seccion = null;
		if (pe != null && pe.isEmpty())
			pe = null;

		String dui = docente;

		// Obtener el bachillerato
		Bachillerato bachillerato = bachilleratoService.debolverBachillerato(carrera, seccion, grado);
		int codigoBachillerato = 0;

		if (bachillerato != null) {
			codigoBachillerato = bachillerato.getCodigoBachillerato();
			Asignacion asignacion = asignacionService.asignacionParaActividad(dui, idMateria, codigoBachillerato);

			// Validación si asignación es nula
			if (asignacion != null) {
				if ("Módulo".equals(asignacion.getMateria().getTipoMateria())) {
					pe = "1";
					System.out.println("entro");
				}

				List<Actividad> listadoActividades = actividadService.listaActividades(dui,
						asignacion.getMateria().getIdMateria(), pe, codigoBachillerato);
				List<Nota> listaNotas = notaService.listaNotaActividadBachillerato(
						asignacion.getDocente().getDuiDocente(),
						asignacion.getBachillerato().getCodigoBachillerato(),
						pe);
				List<Alumno> listaAlumnos = alumnoService.alumnosPorBachilerato(
						asignacion.getBachillerato().getCodigoBachillerato());

				// Crear un mapa para almacenar las notas por alumno y actividad
				Map<Integer, Map<Integer, Nota>> notasPorAlumno = new HashMap<>();
				// Crear un mapa para almacenar el total de notas por alumno
				Map<Integer, Double> totalNotasPorAlumno = new HashMap<>();

				for (Nota nota : listaNotas) {
					int idAlumno = nota.getAlumno().getIdAlumno();
					int idActividad = nota.getActividad().getIdActividad();

					notasPorAlumno.computeIfAbsent(idAlumno, k -> new HashMap<>()).put(idActividad, nota);

					BigDecimal notafinal = new BigDecimal(
							nota.getNotaObtenida() * (nota.getActividad().getPonderacionActividad() / 100.0));
					notafinal = notafinal.setScale(2, RoundingMode.HALF_UP);
					totalNotasPorAlumno.merge(idAlumno, notafinal.doubleValue(), Double::sum);
				}

				Map<String, Long> conteoPorTipo = listadoActividades.stream()
						.collect(Collectors.groupingBy(Actividad::getTipoActividad, Collectors.counting()));

				List<ActividadDTO> actividadDTOList = conteoPorTipo.entrySet().stream()
						.map(entry -> new ActividadDTO(entry.getKey(), entry.getValue().intValue()))
						.collect(Collectors.toList());

				model.addAttribute("actividadDTOList", actividadDTOList);
				model.addAttribute("listadoActividades", listadoActividades);
				model.addAttribute("listaAlumnos", listaAlumnos);
				model.addAttribute("notasPorAlumno", notasPorAlumno);
				model.addAttribute("totalNotasPorAlumno", totalNotasPorAlumno);
				model.addAttribute("asignacion", asignacion);
			}
		}

		// Cargar listas de periodos, carreras y materias
		List<Periodo> periodos = periodoService.listaPeriodos();
		List<Bachillerato> listaCarreras = bachilleratoService.listaCarrera(false);
		List<Materia> listaMaterias = materiaService.obtenerMaterias();

		model.addAttribute("docentes", docenteService.listarDocenteAsignacion());
		model.addAttribute("periodos", periodos);
		model.addAttribute("pe", pe);
		model.addAttribute("bachilleratos", listaCarreras);
		model.addAttribute("listaMaterias", listaMaterias);

		return "Calificaciones/vistaCalificaciones";
	}

	@GetMapping("/alumnos")
	public String verAlumnosCalificaciones(Model model, @RequestParam(value = "carrera", required = false) String carrera,
	@RequestParam(value = "grado", required = false) String grado,
	@RequestParam(value = "seccion", required = false) String seccion){

		
		// Obtener la lista completa de alumnos filtrada por los parámetros
		List<Alumno> listaAlumnos = alumnoService.listarAlumnos(carrera, grado, seccion, null);
		// Ordenar la lista por "apellidoAlumno"
		listaAlumnos.sort(Comparator.comparing(Alumno::getApellidoAlumno));


		// Obtener la lista de carreras (bachilleratos)
		List<Bachillerato> listaCarreras = bachilleratoService.listaCarrera(false);

		model.addAttribute("bachilleratos", listaCarreras);
		model.addAttribute("carrera", carrera);
		model.addAttribute("grado", grado);
		model.addAttribute("seccion", seccion);
		model.addAttribute("alumnos", listaAlumnos);

		return "Calificaciones/AlumnoCalificaciones";
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
