package com.dsi.insibo.sice.Expediente_alumno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsi.insibo.sice.entity.Bachillerato;

@Service
public class BachilleratoService  {

    @Autowired
    private BachilleratoRepository bachilleratoRepository;

    public List<Bachillerato> listaBachilleratos(){
        return (List<Bachillerato>) bachilleratoRepository.findAll();
    }
}
