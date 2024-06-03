package com.dsi.insibo.sice.Asistencia_personal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AsistenciaController {

	@GetMapping("/AsistenciaPersonal/Cargar")
	public String subiendoArchivo(){
		return "Asistencia_personal/cargarArchivo";
	}
    @GetMapping("/AsistenciaPersonal/AsistenciaGeneral")
	public String llegadaGeneral(){
		return "Asistencia_personal/asistenciaGeneral";
	}
    @GetMapping("/AsistenciaPersonal/AsistenciaTardia")
	public String llegadasTardia(){
		return "Asistencia_personal/asistenciaTardia";
	}
	@GetMapping("/AsistenciaPersonal/Justificacion")
	public String mostrarJustificacion(){
		return "Asistencia_personal/mostrarJustificacion";
	}
}
