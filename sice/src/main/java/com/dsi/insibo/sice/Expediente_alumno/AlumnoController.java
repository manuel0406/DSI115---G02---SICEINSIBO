package com.dsi.insibo.sice.Expediente_alumno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.Bachillerato;
import com.dsi.insibo.sice.entity.Usuario;



@Controller
@RequestMapping("/ExpedienteAlumno")
public class AlumnoController {

	@Autowired
	private AlumnoService alumnoService;
    @Autowired
	private BachilleratoService bachilleratoService;

	@PostMapping("/guardar")
	public String guardarAlumno(@ModelAttribute Alumno alumno) {

		alumnoService.guardarAlumno(alumno);
		return "redirect:/ExpedienteAlumno/Crear";
	}

	@GetMapping("/Crear")
	public String crear(Model model) {

		Alumno alumno = new Alumno();
		
		List<Bachillerato> listaBachilleratos = bachilleratoService.listaBachilleratos();
		model.addAttribute("alumno", alumno);
		model.addAttribute("bachilleratos", listaBachilleratos);


		return "/Expediente_alumno/registro";
	}

	@GetMapping("/editar")
	public String editar() {
		return "Expediente_alumno/editar";
	}

	@GetMapping("/ver")
	public String verAlumno(Model model) {

		Alumno alumno = new Alumno();
		List<Alumno> listaAlumnos= alumnoService.listarAlumnos();
		model.addAttribute("alumnos", listaAlumnos);
		return "Expediente_alumno/verAlumno";
	}
}
