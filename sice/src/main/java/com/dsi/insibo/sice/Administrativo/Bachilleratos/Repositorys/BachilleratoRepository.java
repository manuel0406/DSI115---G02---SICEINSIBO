package com.dsi.insibo.sice.Administrativo.Bachilleratos.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsi.insibo.sice.entity.Bachillerato;
@Repository
public interface BachilleratoRepository extends JpaRepository<Bachillerato, Integer> {    

}
