package com.dsi.insibo.sice.Expediente_docente;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class DocenteController {


    @GetMapping("/zzz")
    public String docentes() {
        return "Expediente_docente/fichaDocente";
    }

    @GetMapping("/fichaAdministrativo")
    public String getMethodName() {
        return "Expediente_docente/fichaAdministrativo";
    }
    


}
