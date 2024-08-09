package com.dsi.insibo.sice.Biblioteca;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.Biblioteca.Service.EntregaPapeleriaService;
import com.dsi.insibo.sice.Biblioteca.Service.InventarioPapeleriaService;
import com.dsi.insibo.sice.entity.EntregaPapeleria;
import com.dsi.insibo.sice.entity.InventarioPapeleria;


@Controller
@RequestMapping("/Biblioteca/Papeleria")
//@PreAuthorize("hasRole('ADMINISTRADOR')") 
public class PapeleriaController {
    @Autowired
    private InventarioPapeleriaService inventarioPapeleriaService;
    @Autowired
    private EntregaPapeleriaService entregaPapeleriaService;

    // @GetMapping("/InventarioPapeleria")
    // public String inventarioPapeleria(Model model){
    //     List<InventarioPapeleria> listadoProductos = inventarioPapeleriaService.listarProductos();
    //     model.addAttribute("titulo","Inventario Papelería");
    //     model.addAttribute("productos",listadoProductos);
    //     model.addAttribute("nuevoProducto", new InventarioPapeleria());

    //     return "/Biblioteca/inventarioPapeleria.html";
    // }

    @GetMapping("/InventarioPapeleria")
    public String inventarioPapeleria(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {

        List<InventarioPapeleria> listadoProductos = inventarioPapeleriaService.listarProductos();
        int start = page * size;
        int end = Math.min((start + size), listadoProductos.size());

        if (start > listadoProductos.size()) {
            start = listadoProductos.size() - size;
            if (start < 0) {
                start = 0;
            }
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<InventarioPapeleria> pageProductos = new PageImpl<>(
                listadoProductos.subList(start, end),
                pageRequest, 
                listadoProductos.size()
        );

        model.addAttribute("titulo", "Inventario Papelería");
        model.addAttribute("productos", pageProductos.getContent());
        model.addAttribute("nuevoProducto", new InventarioPapeleria());
        model.addAttribute("page", pageProductos);

        return "/Biblioteca/inventarioPapeleria.html";
    }

    @PostMapping("/InventarioPapeleria")
    public String guardarProducto(Model model, @Validated @ModelAttribute("nuevoProducto") InventarioPapeleria nuevoProducto, BindingResult result, RedirectAttributes attributes) {
        List<InventarioPapeleria> listadoProductos = inventarioPapeleriaService.listarProductos();

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Inventario Papelería");
            model.addAttribute("productos", listadoProductos);
            return "redirect:/Biblioteca/Papeleria/InventarioPapeleria";
        }

        inventarioPapeleriaService.guardar(nuevoProducto);
        attributes.addFlashAttribute("success", "Artículo guardado con éxito");
        return "redirect:/Biblioteca/Papeleria/InventarioPapeleria";
    }


    @GetMapping("/InventarioPapeleria/delete/{idArticulo}")
    public String eliminarProducto(@PathVariable("idArticulo") int idArticulo, RedirectAttributes attribute){

        inventarioPapeleriaService.eliminar(idArticulo);
        attribute.addFlashAttribute("success", "El registro se ha eliminado éxitosamente");
        return "redirect:/Biblioteca/Papeleria/InventarioPapeleria";
    }

    @GetMapping("/InventarioPapeleria/edit/{idArticulo}")
    @ResponseBody
    public InventarioPapeleria obtenerArticuloPorId(@PathVariable("idArticulo") int idArticulo) {
        return inventarioPapeleriaService.buscarPorId(idArticulo);
    }

    @PostMapping("/InventarioPapeleria/update")
    public String actualizarProducto(@Validated @ModelAttribute("producto") InventarioPapeleria producto, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "redirect:/Biblioteca/Papeleria/InventarioPapeleria";
        }

        inventarioPapeleriaService.guardar(producto);
        attributes.addFlashAttribute("success", "Artículo actualizado con éxito");
        return "redirect:/Biblioteca/Papeleria/InventarioPapeleria";
    }

    @GetMapping("/Control")
    public String ControlPapeleria(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {

        // Obtener la lista completa de entregas
        List<EntregaPapeleria> listadoEntregas = entregaPapeleriaService.listarEntregas();

        // Calcular el rango para la página actual
        int start = page * size;
        int end = Math.min((start + size), listadoEntregas.size());

        if (start > listadoEntregas.size()) {
            start = listadoEntregas.size() - size;
            if (start < 0) {
                start = 0;
            }
        }

        // Crear una sublista para la página actual
        List<EntregaPapeleria> pagedEntregas = listadoEntregas.subList(start, end);

        // Crear el objeto Page para pasar a la vista
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<EntregaPapeleria> pageEntregas = new PageImpl<>(
                pagedEntregas,
                pageRequest,
                listadoEntregas.size()
        );

        // Obtener la lista de productos sin paginación
        List<InventarioPapeleria> listadoProductos = inventarioPapeleriaService.listarProductos();

        model.addAttribute("titulo", "Entrega de Papelería");
        model.addAttribute("entregas", pageEntregas.getContent());
        model.addAttribute("productos", listadoProductos);
        model.addAttribute("nuevaEntrega", new EntregaPapeleria());
        model.addAttribute("page", pageEntregas);

        return "/Biblioteca/controlPapeleria.html";
    }
}
