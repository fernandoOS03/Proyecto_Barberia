package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.entity.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
	
	// Consultar servicios por nombre parcial para reporte de servicio más solicitados
    List<Servicio> findByNombreServicioContainingIgnoreCase(String nombre);
}
