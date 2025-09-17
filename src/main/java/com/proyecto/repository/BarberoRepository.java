package com.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.entity.Barbero;

public interface BarberoRepository extends JpaRepository<Barbero, Integer> {
	
	// Para login básico 
    Barbero findByUsuarioBarberoAndContrasenaBarbero(String usuarioBarbero, String contrasenaBarbero);
    
}
