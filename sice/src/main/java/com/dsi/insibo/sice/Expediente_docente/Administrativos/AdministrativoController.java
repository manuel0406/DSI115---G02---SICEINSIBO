package com.dsi.insibo.sice.Expediente_docente.Administrativos;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.dsi.insibo.sice.entity.Docente;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AdministrativoController {
    @GetMapping("/fichaAdministrativo")
    public String getMethodName() {
        return "Expediente_docente/Administrativos/fichaAdministrativo";
    }
    
}
