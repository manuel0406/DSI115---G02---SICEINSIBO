package com.dsi.insibo.sice.Expediente_docente.Docentes;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dsi.insibo.sice.entity.Docente;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    public List<DocenteDTO> listarDocentes() {
        List<Docente> docentes = (List<Docente>) docenteRepository.findAll();
        return docentes.stream()
                .map(DocenteDTO::new)
                .collect(Collectors.toList());
    }

    public void guardarDocente(Docente docente) {
        docenteRepository.save(docente);
    }

    public Docente buscarPorIdAlumno(String duiDocente) {
        return docenteRepository.findById(duiDocente).orElse(null);
    }

    public void eliminar(String duiDocente) {
        docenteRepository.deleteById(duiDocente);
    }
}
