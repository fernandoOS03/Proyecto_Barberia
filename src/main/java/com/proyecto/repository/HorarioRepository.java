package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.entity.DiaSemana;
import com.proyecto.entity.Horario;

public interface HorarioRepository extends JpaRepository<Horario, Integer>{
	
	// Consultar todos los horarios del barbero
    List<Horario> findByBarberoIdBarbero(Integer idBarbero);

    // Consultar por día específico
    List<Horario> findByBarberoIdBarberoAndDiaSemana(Integer idBarbero, DiaSemana dia);
    

}
