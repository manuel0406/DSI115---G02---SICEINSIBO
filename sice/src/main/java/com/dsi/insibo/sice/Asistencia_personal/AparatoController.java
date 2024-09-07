package com.dsi.insibo.sice.Asistencia_personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.Asistencia_personal.Aparato_Docente.AparatoDocenteService;
import com.dsi.insibo.sice.Asistencia_personal.Aparato_Personal.AparatoPersonalService;
import com.dsi.insibo.sice.Expediente_docente.Administrativos.AdministrativoDTO;
import com.dsi.insibo.sice.Expediente_docente.Administrativos.AdministrativoService;
import com.dsi.insibo.sice.Expediente_docente.Docentes.DocenteDTO;
import com.dsi.insibo.sice.Expediente_docente.Docentes.DocenteService;
import com.dsi.insibo.sice.entity.Docente;
import com.dsi.insibo.sice.entity.DocenteAparato;
import com.dsi.insibo.sice.entity.PersonalAdministrativo;
import com.dsi.insibo.sice.entity.PersonalAparato;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/aparato")
public class AparatoController {

	@Autowired
	private AparatoDocenteService aparatoDocenteService;

	@Autowired
	private AparatoPersonalService aparatoPersonalService;

	@Autowired
	private DocenteService docenteService;

	@Autowired
	private AdministrativoService administrativoService;

	// LISTAR LISTA TANTO DOCENTE COMO ADMINISTRATIVOS
	@GetMapping("/listarAparato")
	public String listarApartoDocente(Model model) {
		// Personal docente
		List<DocenteAparato> listadoAparatoDocente = aparatoDocenteService.aparatoTodos();
		model.addAttribute("aparatoDocente", listadoAparatoDocente);
		// Personal administrativo
		List<PersonalAparato> aparatoPersonalTodos = aparatoPersonalService.aparatoTodosPersonal();
		model.addAttribute("aparatoPersonal", aparatoPersonalTodos);
		return "Aparato_Asistencia/aparatoListar";
	}

	// VISTA CREAR CON SELECT DOCENTE
	@GetMapping("/asignarNumero")
	public String asignarNumero(Model model) {
		DocenteAparato docenteAparato = new DocenteAparato();
		List<DocenteDTO> listadoDocentes = docenteService.listarDocentes();
		model.addAttribute("docenteAparato", docenteAparato);
		model.addAttribute("docentes", listadoDocentes);
		model.addAttribute("txtBoton", "Asignar");
		model.addAttribute("txtTitulo", "Asignar número asistencia");
		return "Aparato_Asistencia/agregarNumeroAparato";
	}
	// VISTA CREAR CON SELECT ADMINISTRATIVO
	@GetMapping("/asignarNumeroPersonal")
	public String asignarNumeroPersonal(Model model) {
		PersonalAparato personalAparato = new PersonalAparato();
		List<AdministrativoDTO> listadoPersonal = administrativoService.listarAdministrativos();
		model.addAttribute("personalAparato", personalAparato);
		model.addAttribute("administrativos", listadoPersonal);
		model.addAttribute("txtBoton", "Asignar");
		model.addAttribute("txtTitulo", "Asignar número asistencia");
		return "Aparato_Asistencia/agregarNumeroAparatoPersonal";
	}

	// METODO GUARDAR DOCENTE
	@PostMapping("/guardarNumeroDocente")
	public String guardarDocenteAp(@Valid @ModelAttribute DocenteAparato docenteAparato,
			@RequestParam("docente") String docenteDui, BindingResult result, Model model,
			RedirectAttributes attribute) {
		List<DocenteDTO> listadoDocentes = docenteService.listarDocentes();
		if (result.hasErrors()) {
			model.addAttribute("docenteAparato", docenteAparato);
			model.addAttribute("Docentes", listadoDocentes);
			// attribute.addFlashAttribute("error","El número ya esta asignado.");
			return "Aparato_Asistencia/agregarNumeroAparato";
		}

		Docente docente = docenteService.buscarPorIdDocente(docenteDui);
		docenteAparato.setDocente(docente);
		aparatoDocenteService.guardarDocenteAparato(docenteAparato);
		attribute.addFlashAttribute("error", "Asignado con exito.");
		return "redirect:/aparato/listarAparato";
	}
	// METODO GUARDAR ADMINISTRATIVO
	@PostMapping("/guardarNumeroPersonal")
	public String guardaPersonalAp(@Valid @ModelAttribute PersonalAparato personalAparato,
			@RequestParam("personal") String personalDui, BindingResult result, Model model,
			RedirectAttributes attribute) {
				List<AdministrativoDTO> listadoPersonales= administrativoService.listarAdministrativos();
				if(result.hasErrors()){
					model.addAttribute("personalAparato", personalAparato);
					model.addAttribute("personal", listadoPersonales);
					attribute.addFlashAttribute("error","El número ya esta asignado.");
					return "Aparato_Asistencia/agregarNumeroAparatoPersonal";
				}
				PersonalAdministrativo administrativos = administrativoService.buscarPorIdAdministrativo(personalDui);
				personalAparato.setPersonal(administrativos);
				aparatoPersonalService.guardarPersonalAparato(personalAparato);
		attribute.addFlashAttribute("error", "Asignado con exito.");
		return "redirect:/aparato/listarAparato";
	}
	// METODO EDITAR DOCENTE
	@GetMapping("/editarNumeroDocente/{id}")
	public String editarNumeroDocente(@PathVariable("id") int idAparato, Model model, RedirectAttributes attribute) {
		DocenteAparato docenteAparato = aparatoDocenteService.buscarPorIdAparatoDocente(idAparato);
		if(docenteAparato == null){
			attribute.addFlashAttribute("error", "El ID no existe");
            return "redirect:/aparato/listarAparato";
		}
		List<DocenteDTO> listadoDocentes = docenteService.listarDocentes();
		model.addAttribute("docenteAparato", docenteAparato);
		model.addAttribute("docentes", listadoDocentes);
		model.addAttribute("txtBoton", "Editar");
		model.addAttribute("txtTitulo", "Editar asignación");
		return "Aparato_Asistencia/agregarNumeroAparato";
	}
	// METODO EDITAR ADMINISTRATIVO
	@GetMapping("/editarNumeroPersonal/{id}")
	public String editarNumeroPersonal(@PathVariable("id") int idAparato, Model model, RedirectAttributes attribute) {
		PersonalAparato personalAparato = aparatoPersonalService.buscarPorIdAparatoPersonal(idAparato);
		if(personalAparato == null){
			attribute.addFlashAttribute("error", "El ID no existe");
            return "redirect:/aparato/listarAparato";
		}
		List<AdministrativoDTO> listadoPersonal = administrativoService.listarAdministrativos();
		model.addAttribute("personalAparato", personalAparato);
		model.addAttribute("administrativos", listadoPersonal);
		model.addAttribute("txtBoton", "Editar");
		model.addAttribute("txtTitulo", "Editar asignación");
		return "Aparato_Asistencia/agregarNumeroAparatoPersonal";
	}
	// METODO ELIMINAR DOCENTE
	@GetMapping("/eliminarNumeroDocente/{id}")
	public String eliminarNumeroDocente(@PathVariable("id") int idAparato, Model model, RedirectAttributes attribute) {
		DocenteAparato docenteAparato = null;
		if (idAparato > 0) {
			docenteAparato = aparatoDocenteService.buscarPorIdAparatoDocente(idAparato);
			if (docenteAparato == null) {
				// error: el id del aparato docente no existe
				attribute.addFlashAttribute("error", "El ID no existe.");
				return "redirect:/aparato/listarAparato";
			}
		} else {
			// error: el id del aparato docente
			attribute.addFlashAttribute("error", "Error con el ID.");
			return "redirect:/aparato/listarAparato";
		}
		aparatoDocenteService.eliminarDocenteAparato(idAparato);
		attribute.addFlashAttribute("warning", "Registro eliminado con exito.");
		return "redirect:/aparato/listarAparato";
	}
	// METODO ELIMINAR ADMINISTRATIVO
	@GetMapping("/eliminarNumeroAdmnistrativo/{id}")
	public String eliminarNumeroAdministrativo(@PathVariable("id") int idAparato, Model model, RedirectAttributes attribute) {
		PersonalAparato personalAparato = null;
		if (idAparato > 0) {
			personalAparato = aparatoPersonalService.buscarPorIdAparatoPersonal(idAparato);
			if (personalAparato == null) {
				// error: el id del aparato docente no existe
				attribute.addFlashAttribute("error", "El ID no existe.");
				return "redirect:/aparato/listarAparato";
			}
		} else {
			// error: el id del aparato docente
			attribute.addFlashAttribute("error", "Error con el ID.");
			return "redirect:/aparato/listarAparato";
		}
		aparatoPersonalService.eliminarPersonalAparato(idAparato);
		attribute.addFlashAttribute("warning", "Registro eliminado con exito.");
		return "redirect:/aparato/listarAparato";
	}

}
