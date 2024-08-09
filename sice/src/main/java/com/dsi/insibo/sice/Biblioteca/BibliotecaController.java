package com.dsi.insibo.sice.Biblioteca;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.Biblioteca.Service.InventarioLibroService;
import com.dsi.insibo.sice.entity.InventarioLibro;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/Biblioteca")
public class BibliotecaController {

    @GetMapping("/")
    public String inicioBiblioteca(Model model){
        model.addAttribute("titulo","Gestión de Biblioteca");

        return "/Biblioteca/inicioBiblioteca.html";
    }

    @GetMapping("/Prestamos")
    public String PrestamosBiblioteca(Model model){
        model.addAttribute("titulo","Gestión de Prestamos");

        return "/Biblioteca/prestamos.html";
    }

    @Autowired
    private InventarioLibroService inventarioLibroService;

    // @GetMapping("/InventarioLibros")
    // public String inventarioLibros(Model model){
    //     List<InventarioLibro> listadoLibros =inventarioLibroService.listarLibros();
    //     model.addAttribute("titulo","Inventario de Libros");
    //     model.addAttribute("libros", listadoLibros);
    //     model.addAttribute("nuevoLibro", new InventarioLibro());

    //     return "/Biblioteca/inventarioLibros.html";
    // }

    @GetMapping("/InventarioLibros")
    public String inventarioLibros(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<InventarioLibro> listadoLibros = inventarioLibroService.listarLibros();
        int start = page * size;
        int end = Math.min((start + size), listadoLibros.size());

        if (start > listadoLibros.size()) {
            start = listadoLibros.size() - size;
            if (start < 0) {
                start = 0;
            }
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<InventarioLibro> pageLibros = new PageImpl<>(
                listadoLibros.subList(start, end),
                pageRequest, 
                listadoLibros.size()
        );

        model.addAttribute("titulo", "Inventario de Libros");
        model.addAttribute("libros", pageLibros.getContent());
        model.addAttribute("nuevoLibro", new InventarioLibro());
        model.addAttribute("page", pageLibros);

        return "/Biblioteca/inventarioLibros.html";
    }

    @PostMapping("/InventarioLibros")
    public String guardarLibro(Model model, @Validated @ModelAttribute("nuevoLibro") InventarioLibro nuevoLibro, BindingResult result, RedirectAttributes attributes) {
        List<InventarioLibro> listadoLibros = inventarioLibroService.listarLibros();

        if (result.hasErrors()) {
            model.addAttribute("titulo","Inventario de Libros");
            model.addAttribute("libros", listadoLibros);
            return "redirect:/Biblioteca/InventarioLibros";
        }
        
        inventarioLibroService.guardar(nuevoLibro);
        attributes.addFlashAttribute("success", "Libro Agregado con éxito");
        return "redirect:/Biblioteca/InventarioLibros";
    }
    
    @GetMapping("/InventarioLibros/delete/{idInventarioLibros}")
    public String eliminarLibro(@PathVariable("idInventarioLibros") int idInventarioLibros, RedirectAttributes attribute){
        inventarioLibroService.eliminar(idInventarioLibros);
        attribute.addFlashAttribute("success", "El registro se ha eliminado éxitosamente");
        return "redirect:/Biblioteca/InventarioLibros";
    }

    @GetMapping("/InventarioLibros/edit/{idInventarioLibros}")
    @ResponseBody
    public InventarioLibro obtenerLibroPorId(@PathVariable("idInventarioLibros") int idInventarioLibros) {
        return inventarioLibroService.buscarPorId(idInventarioLibros);
    }

    @PostMapping("/InventarioLibros/update")
    public String actualizarLibro(@Validated @ModelAttribute("libro") InventarioLibro libro, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "redirect:/Biblioteca/InventarioLibros";
        }

        inventarioLibroService.guardar(libro);
        attributes.addFlashAttribute("success", "Libro actualizado con éxito");
        return "redirect:/Biblioteca/InventarioLibros";
    }

}
