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

import com.dsi.insibo.sice.entity.Alumno;
import com.dsi.insibo.sice.entity.AnexoAlumno;

@Controller
public class AnexoController {

    @Autowired
    private AnexoService anexoService;
    @Autowired
    private AnexoRepository anexoRepository;
    @Autowired
    private AlumnoService alumnoService;

    @GetMapping("/Subir/{clave}/{nie}")
    public String index(@PathVariable("clave") String clave, @PathVariable("nie") int nie, Model model) {

        if (clave.equals("dui")) {

            model.addAttribute("clave", clave);
            model.addAttribute("nie", nie);
            System.out.println("NIE: " + nie);

            return "Expediente_alumno/upload";

        } else if (clave.equals("PartidaNacimiento")) {
            model.addAttribute("clave", clave);
            model.addAttribute("nie", nie);
            System.out.println("NIE: " + nie);
        }

        return "Expediente_alumno/upload";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus(Model model) {
        return "Expediente_alumno/uploadStatus";
    }

    @PostMapping("/upload/{clave}/{nie}")
    public String cargandoArchivo(@PathVariable("clave") String clave, @PathVariable("nie") int nie,
            @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        Alumno alumno = alumnoService.buscarPorIdAlumno(nie);
        AnexoAlumno archivo = alumnoService.obtenerAnexoPorAlumno(nie);

        if (archivo == null) {
            archivo = new AnexoAlumno();
        }

        if (clave.equals("dui")) {

            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Por favor, seleccione un archivo para subir.");
                return "redirect:uploadStatus";
            }
            try {
                // Leer los bytes del archivo
                byte[] bytes = file.getBytes();

                // Guardar la referencia en la base de datos

                archivo.setNombreDui(file.getOriginalFilename());
                archivo.setDatosDui(bytes);
                archivo.setFechaDui(new Date());
                archivo.setAlumno(alumno);
                anexoService.guardaAnexo(archivo);

                redirectAttributes.addFlashAttribute("message",
                        "Has subido correctamente '" + file.getOriginalFilename() + "'");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "redirect:/ExpedienteAlumno/Documentos/" + nie;
        } else if (clave.equals("PartidaNacimiento")) {

            alumno = alumnoService.buscarPorIdAlumno(nie);
            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Por favor, seleccione un archivo para subir.");
                return "redirect:uploadStatus";
            }
            try {
                // Leer los bytes del archivo
                byte[] bytes = file.getBytes();

                // Guardar la referencia en la base de datos

                archivo.setNombrePartidaNacimiento(file.getOriginalFilename());
                archivo.setDatosPartidaNacimiento(bytes);
                archivo.setFechaPartidaNacimiento(new Date());
                archivo.setAlumno(alumno);
                anexoService.guardaAnexo(archivo);

                redirectAttributes.addFlashAttribute("message",
                        "Has subido correctamente '" + file.getOriginalFilename() + "'");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "redirect:/ExpedienteAlumno/Documentos/" + nie;
        }
        return "redirect:/ExpedienteAlumno/ver";

    }

    @GetMapping("/ExpedienteAlumno/files/{clave}/{id}")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> serveFile(@PathVariable int id, @PathVariable("clave") String clave) {
        // Busca el archivo en el repositorio por su ID
        AnexoAlumno fileEntity = anexoRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));

        if (clave.equals("dui")) {
            // Crea un recurso de bytes a partir de los datos del archivo
            ByteArrayResource resource = new ByteArrayResource(fileEntity.getDatosDui());

            // Devuelve la respuesta con el contenido del archivo
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileEntity.getNombreDui() + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } else if (clave.equals("PartidaNacimiento")) {

            // Crea un recurso de bytes a partir de los datos del archivo
            ByteArrayResource resource = new ByteArrayResource(fileEntity.getDatosPartidaNacimiento());

            // Devuelve la respuesta con el contenido del archivo
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileEntity.getNombrePartidaNacimiento() + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        }
        return null;
    }
}
