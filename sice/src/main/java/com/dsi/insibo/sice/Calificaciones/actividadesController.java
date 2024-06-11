package com.dsi.insibo.sice.Calificaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dsi.insibo.sice.entity.Actividad;
import com.dsi.insibo.sice.entity.Bachillerato;
import com.dsi.insibo.sice.entity.Periodo;
import com.dsi.insibo.sice.entity.Materia;
import com.dsi.insibo.sice.entity.Actividad;

import org.springframework.ui.Model;

@Controller
public class actividadesController {

	@Autowired
	private GradoService gradoService;
	@Autowired
	private PeriodoService periodoService;
	@Autowired
	private MateriaService materiaService;
	@Autowired
	private ActividadService actividadService;


	@Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private PeriodoRepository periodoRepository;
	@Autowired
    private ActividadRepository actividadRepository;

    @GetMapping("/Actividades")
	public String verActividades(Model model){

		List<Bachillerato> listaBachilleratos = gradoService.listaBachilleratos();
		model.addAttribute("grados", listaBachilleratos);

		List<Periodo> listaPeriodos = periodoService.listaPeriodos();
		model.addAttribute("periodos", listaPeriodos);

		List<Materia> listaMaterias = materiaService.listaMaterias();
		model.addAttribute("materias", listaMaterias);

		List<Actividad> listaActividads = actividadService.listaActividades();
		model.addAttribute("actividades", listaActividads);

		model.addAttribute("actividad", new Actividad());

		return "Calificaciones/vistaActividades";
	}

	@PostMapping("/Actividades/add")
    public String addActividadFromModal(@ModelAttribute Actividad actividad) {
        actividadRepository.save(actividad);
        return "redirect:/Actividades";
    }
}
