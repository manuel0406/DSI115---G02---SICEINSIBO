package com.dsi.insibo.sice.Calificaciones;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dsi.insibo.sice.Administrativo.Bachilleratos.Servicios.BachilleratoService;
import com.dsi.insibo.sice.Administrativo.Materias.ServiciosMaterias.AsignacionService;
import com.dsi.insibo.sice.Seguridad.SeguridadService.SessionService;
import com.dsi.insibo.sice.entity.Actividad;
import com.dsi.insibo.sice.entity.Asignacion;
import com.dsi.insibo.sice.entity.Bachillerato;
import com.dsi.insibo.sice.entity.Periodo;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/Actividad")
public class actividadesController {

	@Autowired
	private PeriodoService periodoService;

	@Autowired
	private ActividadService actividadService;
	@Autowired
	BachilleratoService bachilleratosService;

	@Autowired
	SessionService sesion;
	@Autowired
	AsignacionService asignacionService;

	@PreAuthorize("hasAnyRole('DOCENTE')")
	@GetMapping("/{codigoBachillerato}")
	public String verActividades(Model model, @PathVariable("codigoBachillerato") int codigoBachillerato,
			RedirectAttributes attributes) {

		Bachillerato bachillerato = null;

		if (codigoBachillerato > 0) {
			// Busca al bachillerato por su codigo
			bachillerato = bachilleratosService.bachilleratoPorId(codigoBachillerato);

			// Verifica que el bachillerato exista
			if (bachillerato == null) {
				System.out.println("Error: ¡El código ingresado no existe");
				attributes.addFlashAttribute("error", "Error: ¡El código ingresado no existe");
				return "redirect:/Actividad/" + codigoBachillerato;
			}

		} else {
			// Maneja el caso donde el codigo no es válido
			System.out.println("Error: ¡El código ingresado no es válido!");
			attributes.addFlashAttribute("error", "Error: ¡El código ingresado no es válido!");
			return "redirect:/Actividad/" + codigoBachillerato;
		}
		// Se extra el listado de periodos existentes
		List<Periodo> periodos = periodoService.listaPeriodos();
		// Objecto activada nuevo
		Actividad actividad = new Actividad();

		// Acá se obtienen el objeto asignación correspondiente al docente y
		// bachillerato
		String dui = sesion.duiSession();
		// System.out.println(dui + " " + codigoBachillerato);
		Asignacion asignacion = asignacionService.asignacionParaActividad(dui, codigoBachillerato);
		// Listado de las actividaes que ha creado un docente por bachillerato
		List<Actividad> listadoActividades = actividadService.listaActividades(dui, codigoBachillerato);

		model.addAttribute("actividad", actividad);
		model.addAttribute("periodos", periodos);
		model.addAttribute("listadoActividades", listadoActividades);
		model.addAttribute("asignacion", asignacion);
		model.addAttribute("titulo", "Actividades");

		return "Calificaciones/vistaActividades";
	}

	@PreAuthorize("hasAnyRole('DOCENTE')")
	@PostMapping("/add")
	public String addActividadFromModal(@ModelAttribute Actividad actividad, RedirectAttributes redirectAttributes) {

		float totalPoderación = 0;
		List<Actividad> listadoActividades = actividadService.listaActividades(
				actividad.getAsignacion().getDocente().getDuiDocente(),
				actividad.getAsignacion().getBachillerato().getCodigoBachillerato());

		for (Actividad actividad2 : listadoActividades) {
			if (actividad2.getIdActividad() != actividad.getIdActividad()) {
				totalPoderación += actividad2.getPonderacionActividad();
			}
		}

		totalPoderación += actividad.getPonderacionActividad();
		System.out.println("Suma pondereación: " + totalPoderación);
		if (totalPoderación <= 100) {
			actividadService.guardarActividad(actividad);
			redirectAttributes.addFlashAttribute("success",
					"Actividad agregada exitosamente.");
		} else {
			redirectAttributes.addFlashAttribute("error",
					"La suma de poderaciones no debe de superar el 100%. Por verificar los porcentajes ya ha asignados.");
		}
		return "redirect:/Actividad/" + actividad.getAsignacion().getBachillerato().getCodigoBachillerato();
	}

	@PreAuthorize("hasAnyRole('DOCENTE')")
	@GetMapping("/{codigoBachillerato}/delete/{idActividad}")
	public String eliminarAlumno(@PathVariable("codigoBachillerato") int codigoBachillerato,
			@PathVariable("idActividad") int idActividad, RedirectAttributes attributes) {

		Actividad actividad = null;
		if (idActividad > 0) {
			// Busca al bachillerato por su codigo
			actividad = actividadService.buscarActividadPorId(idActividad);

			// Verifica que el bachillerato exista
			if (actividad == null) {
				// System.out.println("Error: ¡No exite este idActividad no existe");
				attributes.addFlashAttribute("error", "Error: ¡El idActividad ingresado no existe");
				return "redirect:/Actividad/" + codigoBachillerato;
			}
		} else {
			// Maneja el caso donde el codigo no es válido
			// System.out.println("Error: ¡El idActividad ingresado no es válido!");
			attributes.addFlashAttribute("error", "Error: ¡El idActividad ingresado no es válido!");
			return "redirect:/Actividad/" + codigoBachillerato;
		}
		// Elimina el registro
		actividadService.eliminarActividad(idActividad);
		attributes.addFlashAttribute("warning", "¡Registro eliminado con éxito!");
		return "redirect:/Actividad/" + codigoBachillerato;
	}

}
