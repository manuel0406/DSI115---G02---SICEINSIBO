package com.dsi.insibo.sice.Expediente_docente.Docentes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.dsi.insibo.sice.Seguridad.UsuarioService;
import com.dsi.insibo.sice.entity.Docente;
import com.dsi.insibo.sice.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/expedientedocente")
public class DocenteController {

    @Autowired
    private UsuarioService usuarioService;
    
    // Direccionadores estaticos
    @GetMapping("/anexosDocente")
    public String docentes() {
        return "Expediente_docente/Docentes/anexosDocente";
    }

    @GetMapping("/barra")
    public String barra() {
        return "Expediente_docente/barra";
    }

    // Direccionadores de acción

    // Ficha general de expediente docente /expedientedocente/formulario
    @GetMapping("/formulario")
    public String docentes(Model model) {
        Docente profesor = new Docente();

        model.addAttribute("profesor", profesor);
        return "Expediente_docente/Docentes/fichaDocente";
    }

    //guardando formulario
    @PostMapping("/guardar")
    public String guardar(@Validated @ModelAttribute Docente docente, BindingResult result, Model model, RedirectAttributes attribute) {
    
        //Obtenemos informacion relevante del usuario
        String correo = docente.getCorreoDocente() ;
        String rol = "Docente";
        String estado = "Desactivado";
        boolean inicio = true;
        String contrasena = "";

        //Asignaciones al objeto usuario
        Usuario usuario = new Usuario();
        usuario.setCorreoUsuario(correo);
        usuario.setDocente(docente);
        usuario.setRolUsuario(rol);
        usuario.setEstadoUsuario(estado);
        usuario.setPrimerIngreso(inicio);
        usuario.setContrasenaUsuario(contrasena);

        if(result.hasErrors()){
            model.addAttribute("profesor", docente);
            System.out.println("Se tienen errores en el formulario");
            return "expedientedocente/formulario";
        }
    
        boolean creado = docenteService.guardarDocente(docente);
        usuarioService.guardarUsuario(usuario);
    
        if (creado) {
            attribute.addFlashAttribute("success", "Expediente creado con exito!");
        } else {
            attribute.addFlashAttribute("success", "Expediente actualizado con exito!");
        }
    
        return "redirect:plantadocente";
    }
    
    // Lista docentes usando la DB /expedientedocente/plantadocente
    @Autowired
    private DocenteService docenteService;

    @GetMapping("/plantadocente")
    public String listarDocentes(Model model) {
        List<DocenteDTO> listadoDocentes = docenteService.listarDocentes();
        model.addAttribute("titulo", "Listado de docentes");
        model.addAttribute("Docentes", listadoDocentes);
        return "Expediente_docente/Docentes/listarDocentes";  // Vista HTML
    }

    // Editando docente /expedientedocente/editarformulario/{id}
    @GetMapping("/editarexpediente/{id}")
    public String editarDocente(@PathVariable("id") String idDocente, Model model, RedirectAttributes attribute) {
        Docente profesor = docenteService.buscarPorIdDocente(idDocente);
        if (profesor == null) {
            System.out.println("El docente no existe");
            attribute.addFlashAttribute("error", "El expediente no existe");
            return "redirect:/expedientedocente/plantadocente";
        }
    
        model.addAttribute("profesor", profesor);
        return "Expediente_docente/Docentes/fichaDocente";
    }
    

    // Eliminar ficha
    @GetMapping("/eliminarexpediente/{id}")
    public String eliminarDocente(@PathVariable("id") String idDocente, RedirectAttributes attribute) {
        Docente profesor = docenteService.buscarPorIdDocente(idDocente);


        if(profesor == null){
            System.out.println("El docente no existe");
            attribute.addFlashAttribute("error", "El expediente no existe");
            return "redirect:/expedientedocente/plantadocente";
        }

        docenteService.eliminar(idDocente);
        attribute.addFlashAttribute("warning", "El expediente se elimino");

        return "redirect:/expedientedocente/plantadocente";
    }
}
