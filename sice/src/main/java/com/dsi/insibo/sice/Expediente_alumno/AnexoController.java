package com.dsi.insibo.sice.Expediente_alumno;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsi.insibo.sice.entity.AnexoAlumno;



@Controller
public class AnexoController {
    
    @Autowired
    private AnexoService anexoService;
    @Autowired
    private AnexoRepository anexoRepository;

    @GetMapping("/subir/{clave}")
    public String index(@PathVariable("clave") String clave, Model model) {
      
        if (clave.equals("dui")) {
           
            model.addAttribute("titulo", "dui");

            return "redirect:/ExpedienteAlumno/ver";
        }

        return "Expediente_alumno/upload";
    }
    @GetMapping("/uploadStatus")
    public String uploadStatus(Model model) {
        return "Expediente_alumno/uploadStatus";
    }

    @PostMapping("/upload/{clave}")
    public String cargandoArchivo(@PathVariable("clave") String clave,@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
       
        if (clave.equals("dui")) {


            return "redirect:/ExpedienteAlumno/ver";
        }
       
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Por favor, seleccione un archivo para subir.");
            return "redirect:uploadStatus";
        }

        try {
            // Leer los bytes del archivo
            byte[] bytes = file.getBytes();

            // Guardar la referencia en la base de datos
            AnexoAlumno fileEntity = new AnexoAlumno();
            fileEntity.setNombreArchivo(file.getOriginalFilename());
            fileEntity.setDatos(bytes);
            fileEntity.setUploDate(new Date());
            anexoService.guardaAnexo(fileEntity);
            

            redirectAttributes.addFlashAttribute("message", "Has subido correctamente '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";

    }


    @GetMapping("/files/{id}")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> serveFile(@PathVariable int id) {
        // Busca el archivo en el repositorio por su ID
        AnexoAlumno fileEntity = anexoRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
        
        // Crea un recurso de bytes a partir de los datos del archivo
        ByteArrayResource resource = new ByteArrayResource(fileEntity.getDatos());
    
        // Devuelve la respuesta con el contenido del archivo
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileEntity.getNombreArchivo() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
