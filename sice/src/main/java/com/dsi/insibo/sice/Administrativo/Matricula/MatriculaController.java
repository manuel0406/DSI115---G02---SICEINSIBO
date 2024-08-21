package com.dsi.insibo.sice.Administrativo.Matricula;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.Administrativo.Bachilleratos.Servicios.AnioService;
import com.dsi.insibo.sice.Administrativo.Bachilleratos.Servicios.BachilleratoService;
import com.dsi.insibo.sice.Expediente_alumno.AlumnoService;
import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.AnioAcademico;
import com.dsi.insibo.sice.entity.Bachillerato;

@Controller
public class MatriculaController {

	@Autowired
	AlumnoService alumnoService;
	@Autowired
	AnioService anioService;
	@Autowired
	BachilleratoService bachilleratoService;

	@GetMapping("/AntiguoIngreso")
	public String antiguoIngreso(Model model, @RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "apellido", required = false) String apellido) {

		AnioAcademico anioAcademico = anioService.activoMatricula();
		int anioActivo = anioAcademico.getAnio() - 1;

		List<Alumno> busqueda = alumnoService.matricula(nombre, apellido, anioActivo);
		List<Alumno> listadoMatriculado = alumnoService.yaMatriculado();

		List<Alumno> listado = new ArrayList<>();

		for (Alumno alumnoB : busqueda) {
			boolean existeEnMatriculados = false;

			for (Alumno alumnoM : listadoMatriculado) {
				if (alumnoB.getNie() == alumnoM.getNie()) {
					existeEnMatriculados = true;
					break;
				}
			}

			if (!existeEnMatriculados) {
				listado.add(alumnoB);
				// System.out.println("Alumno añadido: " + alumnoB.getNombreAlumno()); //
				// Opcional, para verificar
			}
		}

		model.addAttribute("alumnos", listado);
		model.addAttribute("titulo", "Antiguo ingreso");
		model.addAttribute("nombre", nombre);
		model.addAttribute("apellido", apellido);

		return "Administrativo/GestionMatricula/antiguoIngreso";
	}

	@GetMapping("/fichaMatricula/{idAlumno}")
	public String fichaMatricula(@PathVariable("idAlumno") int idAlumno, Model model, RedirectAttributes attributes) {

		Alumno alumno = null;
		if (idAlumno > 0) {
			// Busca al alumno por su número de identificación estudiantil (NIE)
			alumno = alumnoService.buscarPorIdAlumno(idAlumno);

			// Verifica que el alumno exista
			if (alumno == null) {
				System.out.println("Error: ¡El NIE ingresado no existe!");
				attributes.addFlashAttribute("error", "Error: ¡El NIE ingresado no existe!");
				return "redirect:/ExpedienteAlumno/ver";
			}

		} else {
			// Maneja el caso donde el NIE no es válido
			System.out.println("Error: ¡El NIE ingresado no es válido!");
			attributes.addFlashAttribute("error", "Error: ¡El NIE ingresado no es válido!");
			return "redirect:/ExpedienteAlumno/ver";
		}

		String carrera, grado, seccion;
		carrera = alumno.getBachillerato().getNombreCarrera();
		grado = String.valueOf(alumno.getBachillerato().getGrado());
		seccion = alumno.getBachillerato().getSeccion();

		// Si el alumno existe, obtiene la lista de bachilleratos para el formulario
		List<Bachillerato> listaCarreras = bachilleratoService.listaCarrera();
		model.addAttribute("titulo", "Ficha Matricula");
		model.addAttribute("alumno", alumno);
		model.addAttribute("bachilleratos", listaCarreras);
		model.addAttribute("editar", true); // Indica que se está en modo edición
		model.addAttribute("carrera", carrera);
		model.addAttribute("grado", grado);
		model.addAttribute("seccion", seccion);

		// Retorna el nombre de la vista de edición del alumno
		return "Expediente_alumno/registro";
	}

	@GetMapping("/saveMatricula/{idAlumno}")
	public String saveMatricula(@PathVariable("idAlumno") int idAlumno, RedirectAttributes attributes) {

		Alumno alumno = alumnoService.buscarPorIdAlumno(idAlumno);

		Bachillerato bachillerato = bachilleratoService.debolverBachilleratoMatricula(
				alumno.getBachillerato().getNombreCarrera(), alumno.getBachillerato().getSeccion(),
				String.valueOf(alumno.getBachillerato().getGrado() + 1));

		Alumno alumnoNuevo = new Alumno(alumno.getNie(), alumno.getNombreAlumno(), alumno.getApellidoAlumno(),
				alumno.getSexoAlumno(), alumno.getFechaNacimientoAlumno(), alumno.getDuiAlumno(),
				alumno.getTelefonoAlumno(),
				alumno.getDireccionAlumno(), alumno.getMunicipioAlumno(), alumno.getDepartamentoAlumno(),
				alumno.getDistritoAlumno(),
				alumno.getZonaAlumno(), alumno.getCorreoAlumno(), alumno.getViveCon(), alumno.getMedicamento(),
				alumno.getNombreEncargado(),
				alumno.getApellidoEncargado(), alumno.getFormaMedicacion(), alumno.getTelefonoEncargado(),
				alumno.getPadecimientos(), alumno.getParentescoEncargado(), alumno.getLugarDeTrabajo(),
				alumno.getCorreoEncargado(),
				alumno.getDuiEncargado(), bachillerato);

		alumnoService.guardarAlumno(alumnoNuevo);
		attributes.addFlashAttribute("success", "¡Alumno guardado con éxito!");
		return "redirect:/matriculados";
	}

	@GetMapping("/matriculados")
	public String verAlumno(Model model,
			@RequestParam(value = "carrera", required = false) String carrera,
			@RequestParam(value = "grado", required = false) String grado,
			@RequestParam(value = "seccion", required = false) String seccion,
			@RequestParam(value = "genero", required = false) String genero,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "50") int size) {

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
		if (genero != null && genero.isEmpty()) {
			genero = null;
		}

		// Obtener la lista completa de alumnos filtrada por los parámetros
		List<Alumno> alumnosAnioActivo = alumnoService.listarAlumnos(carrera, grado, seccion, genero);
		List<Alumno> alumnosMatriculados = alumnoService.yaMatriculado();

		List<Alumno> listaAlumnos = new ArrayList<>();

		for (Alumno alumno : alumnosMatriculados) {
			boolean existeEnMatriculados = false;

			for (Alumno alumno2 : alumnosAnioActivo) {
				if (alumno.getNie() == alumno2.getNie()) {
					existeEnMatriculados = true;
					break;
				}
			}
			if (existeEnMatriculados) {
				listaAlumnos.add(alumno);
				// System.out.println("Alumno añadido: " + alumno.getNombreAlumno()); //
				// Opcional, para verificar
			}
		}
		// Ordenar la lista por "apellidoAlumno"
		listaAlumnos.sort(Comparator.comparing(Alumno::getApellidoAlumno));

		// Crear una estructura paginada manualmente
		PageRequest pageRequest = PageRequest.of(page - 1, size);
		int start = (int) pageRequest.getOffset();
		int end = Math.min((start + pageRequest.getPageSize()), listaAlumnos.size());
		Page<Alumno> pageAlumnos = new PageImpl<>(listaAlumnos.subList(start, end), pageRequest, listaAlumnos.size());

		// Obtener la lista de carreras (bachilleratos)
		List<Bachillerato> listaCarreras = bachilleratoService.listaCarrera();

		// Agregar atributos al modelo para ser utilizados en la vista
		model.addAttribute("titulo", "Matriculados");
		model.addAttribute("alumnos", pageAlumnos.getContent());
		model.addAttribute("bachilleratos", listaCarreras);
		model.addAttribute("carrera", carrera);
		model.addAttribute("grado", grado);
		model.addAttribute("seccion", seccion);
		model.addAttribute("page", page);
		model.addAttribute("totalPages", pageAlumnos.getTotalPages());
		model.addAttribute("totalElements", listaAlumnos.size());
		model.addAttribute("url", "/matriculados");
		int baseIndex = (page - 1) * size;// sirve para mantener la base de la numeración de lo alumnos cuando cambia de
											// pagina
		model.addAttribute("baseIndex", baseIndex);

		// Retornar el nombre de la vista a ser renderizada
		return "Expediente_alumno/verAlumno";
	}
}
