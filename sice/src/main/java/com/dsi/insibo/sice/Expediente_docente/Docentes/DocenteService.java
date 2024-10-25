package com.dsi.insibo.sice.Expediente_docente.Docentes;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dsi.insibo.sice.entity.Docente;
import com.dsi.insibo.sice.entity.UsuarioRoleEnum;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    public List<DocenteDTO> listarDocentes() {
        List<Docente> docentes = (List<Docente>) docenteRepository.findAll();
        return docentes.stream()
                .sorted(Comparator.comparing(Docente::getNombreDocente)
                        .thenComparing(Docente::getApellidoDocente)) 
                .map(DocenteDTO::new)
                .collect(Collectors.toList());
    }

    public boolean guardarDocente(Docente docente) {
        // Verificar si el docente ya existe en la base de datos
        boolean existe = docenteRepository.existsById(docente.getDuiDocente());
    
        // Guardar el docente
        docenteRepository.save(docente);
    
        // Devolver true si se creó un nuevo expediente, false si se actualizó uno existente
        return !existe;
    }
    

    public Docente buscarPorIdDocente(String duiDocente) {
        return docenteRepository.findById(duiDocente).orElse(null);
    }

    public void eliminar(String duiDocente) {
        docenteRepository.deleteById(duiDocente);
    }

    // retorna una lista de docentes
    public List<Docente> listarDocenteAsignacion(){
        return docenteRepository.obtenerDocentesPorRol(UsuarioRoleEnum.DOCENTE);
    }

    public List<Docente> docentes(){
        return (List<Docente>) docenteRepository.findAll();
    }

    public List<Docente> buscarPorNombre(String nombre) {
        return docenteRepository.findByNombreDocenteContainingIgnoreCase(nombre);
    }

}
