package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.proyecto.entity.Barbero;
import com.proyecto.repository.BarberoRepository;

@Service
public class BarberoService {
	
	@Autowired
	private BarberoRepository barberoRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Barbero crear(Barbero barbero) throws Exception {
        if (barbero.getNombreBarbero() == null || barbero.getNombreBarbero().isEmpty()) {
            throw new Exception("Debe ingresar el nombre del barbero");
        }
        
        if (barbero.getEdadBarbero() == null || barbero.getEdadBarbero() <= 0) {
            throw new Exception("Debe ingresar una edad válida");
        }

        if (barbero.getEmailBarbero() == null || barbero.getEmailBarbero().isEmpty()) {
            throw new Exception("Debe ingresar el correo del barbero");
        }
        
        if (barbero.getUsuarioBarbero() == null || barbero.getUsuarioBarbero().isEmpty()) {
            throw new Exception("Debe ingresar un usuario");
        }

        if (barbero.getContrasenaBarbero() == null || barbero.getContrasenaBarbero().isEmpty()) {
            throw new Exception("Debe ingresar una contraseña");
        }

        return barberoRepository.save(barbero);
    }
	
	
	public Barbero editar(Barbero barbero) throws Exception {
		if (barbero.getNombreBarbero() == null || barbero.getNombreBarbero().isEmpty()) {
            throw new Exception("Debe ingresar el nombre del barbero");
        }
        
        if (barbero.getEdadBarbero() == null || barbero.getEdadBarbero() <= 0) {
            throw new Exception("Debe ingresar una edad válida");
        }

        if (barbero.getEmailBarbero() == null || barbero.getEmailBarbero().isEmpty()) {
            throw new Exception("Debe ingresar el correo del barbero");
        }
        
        if (barbero.getUsuarioBarbero() == null || barbero.getUsuarioBarbero().isEmpty()) {
            throw new Exception("Debe ingresar un usuario");
        }

        if (barbero.getContrasenaBarbero() == null || barbero.getContrasenaBarbero().isEmpty()) {
            throw new Exception("Debe ingresar una contraseña");
        }
		
        Barbero barberoEdit = getBarbero(barbero.getIdBarbero());

        barberoEdit.setNombreBarbero(barbero.getNombreBarbero());
        barberoEdit.setEdadBarbero(barbero.getEdadBarbero());
        barberoEdit.setEmailBarbero(barbero.getEmailBarbero());
        barberoEdit.setUsuarioBarbero(barbero.getUsuarioBarbero());
        barberoEdit.setContrasenaBarbero(barbero.getContrasenaBarbero());

        return barberoRepository.save(barberoEdit);
    }
	
	
	public boolean eliminar(Integer idBarbero) {
	    if (tieneCitasAsignadas(idBarbero)) {
	        return false;
	    }
	    barberoRepository.deleteById(idBarbero);
	    return true;
	}

	private boolean tieneCitasAsignadas(Integer idBarbero) {
	    String sql = "SELECT COUNT(*) FROM cita WHERE id_barbero = ?";
	    Integer total = jdbcTemplate.queryForObject(sql, Integer.class, idBarbero); // con minúscula
	    return total != null && total > 0;
	}
	
	public Barbero getBarbero(Integer idBarbero) throws Exception {
        Optional<Barbero> optBarbero = barberoRepository.findById(idBarbero);
        if (optBarbero.isEmpty()) {
            throw new Exception("No se encontró el barbero con ID: " + idBarbero);
        }
        return optBarbero.get();
    }
	
	public List<Barbero> getAll() {
        return barberoRepository.findAll();
    }
	
	public Barbero login(String usuario, String clave) throws Exception {
        Barbero barbero = barberoRepository.findByUsuarioBarberoAndContrasenaBarbero(usuario, clave);
        if (barbero == null) {
            throw new Exception("Usuario o contraseña incorrectos");
        }
        return barbero;
    }
}
