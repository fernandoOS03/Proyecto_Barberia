package com.proyecto.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.entity.Cita;
import com.proyecto.entity.EstadoPago;
import com.proyecto.repository.CitaRepository;

@Service
public class CitaService {
	
	@Autowired
	private CitaRepository citaRepository;
	
	public Cita crear(Cita cita) throws Exception {
        if (cita.getCliente() == null || cita.getCliente().getIdCliente() == null) {
            throw new Exception("Debe seleccionar un cliente");
        }

        if (cita.getServicio() == null || cita.getServicio().getIdServicio() == null) {
            throw new Exception("Debe seleccionar un servicio");
        }

        if (cita.getBarbero() == null || cita.getBarbero().getIdBarbero() == null) {
            throw new Exception("Debe seleccionar un barbero");
        }

        if (cita.getFecha() == null) {
            throw new Exception("Debe ingresar la fecha de la cita");
        }

        if (cita.getHora() == null) {
            throw new Exception("Debe ingresar la hora de la cita");
        }

        return citaRepository.save(cita);
    }
	
	public Cita editar(Cita cita) throws Exception {
        if (cita.getCliente() == null || cita.getCliente().getIdCliente() == null) {
            throw new Exception("Debe seleccionar un cliente");
        }

        if (cita.getServicio() == null || cita.getServicio().getIdServicio() == null) {
            throw new Exception("Debe seleccionar un servicio");
        }

        if (cita.getBarbero() == null || cita.getBarbero().getIdBarbero() == null) {
            throw new Exception("Debe seleccionar un barbero");
        }

        if (cita.getFecha() == null) {
            throw new Exception("Debe ingresar la fecha de la cita");
        }

        if (cita.getHora() == null) {
            throw new Exception("Debe ingresar la hora de la cita");
        }

        Cita citaEdit = getCita(cita.getIdCita());

        citaEdit.setCliente(cita.getCliente());
        citaEdit.setServicio(cita.getServicio());
        citaEdit.setBarbero(cita.getBarbero());
        citaEdit.setFecha(cita.getFecha());
        citaEdit.setHora(cita.getHora());
        citaEdit.setEstado(cita.getEstado());
        citaEdit.setEstadoPago(cita.getEstadoPago());

        return citaRepository.save(citaEdit);
    }
	
	public void eliminar(Integer idCita) {
        citaRepository.deleteById(idCita);
    }
	
	public Cita getCita(Integer idCita) throws Exception {
        Optional<Cita> optCita = citaRepository.findById(idCita);
        if (optCita.isEmpty()) {
            throw new Exception("No se encontró la cita con ID: " + idCita);
        }
        return optCita.get();
    }
	
	public List<Cita> getAll() {
        return citaRepository.findAll();
    }
	
	// Historial de citas por cliente
    public List<Cita> obtenerCitasPorCliente(Integer idCliente) {
        return citaRepository.findByClienteIdClienteOrderByFechaDesc(idCliente);
    }
      
 // Citas por barbero y fecha (para verificar disponibilidad)
    public List<Cita> obtenerCitasPorBarberoYFecha(Integer idBarbero, String fecha) {
    	LocalDate fechaParseada = LocalDate.parse(fecha);
        return citaRepository.findByBarberoIdBarberoAndFechaOrderByHoraAsc(idBarbero, fechaParseada);
    }
    
    public List<Cita> listarPorCliente(Integer idCliente) {
        return citaRepository.findByClienteIdCliente(idCliente);
    }
    
    public List<Cita> obtenerCitasPendientesPago() {
        return citaRepository.findByEstadoPago(EstadoPago.Pendiente);
    }
}
