package com.dsi.insibo.sice.Expediente_alumno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
		return "redirect:/ExpedienteAlumno/ver";
	}

	@GetMapping("/Crear")
	public String crear(Model model) {

		Alumno alumno = new Alumno();
		
		List<Bachillerato> listaBachilleratos = bachilleratoService.listaBachilleratos();
		model.addAttribute("alumno", alumno);
		model.addAttribute("bachilleratos", listaBachilleratos);


		return "/Expediente_alumno/registro";
	}

	@GetMapping("/editar/{nie}")
	public String editar(@PathVariable("nie") int nie,Model model) {

		Alumno alumno = alumnoService.buscarPorIdAlumno(nie);
		
		List<Bachillerato> listaBachilleratos = bachilleratoService.listaBachilleratos();
		model.addAttribute("alumno", alumno);
		model.addAttribute("bachilleratos", listaBachilleratos);
		return "/Expediente_alumno/registro";
	}
	@GetMapping("/delete/{nie}")
	public String eliminar(@PathVariable("nie") int nie) {

		alumnoService.eliminar(nie);
		return "redirect:/ExpedienteAlumno/ver";
	}

	@GetMapping("/ver")
	public String verAlumno(Model model, @Param("carrera") String carrera, @Param("grado") String grado, @Param("seccion") String seccion) {

		Alumno alumno = new Alumno();
		List<Alumno> listaAlumnos= alumnoService.listarAlumnos(carrera, grado, seccion);
		model.addAttribute("alumnos", listaAlumnos);
		model.addAttribute("carrera", carrera);
		model.addAttribute("grado", grado);
		model.addAttribute("seccion", seccion);

		
		return "Expediente_alumno/verAlumno";
	}

	
}
