package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.entity.EstadoPago;
import com.proyecto.entity.Pago;
import com.proyecto.repository.PagoRepository;

@Service
public class PagoService {
	
	@Autowired
	private PagoRepository pagoRepository;
	
	@Autowired
	private CitaService citaService;
	
	public Pago crear(Pago pago) throws Exception {
        if (pago.getCita() == null || pago.getCita().getIdCita() == null) {
            throw new Exception("Debe seleccionar una cita asociada al pago");
        }

        if (pago.getCita().getEstadoPago() == EstadoPago.Pagado) {
            throw new Exception("Esta cita ya ha sido pagada");
        }
        
        if (pago.getMonto() == null || pago.getMonto().doubleValue() <= 0) {
            throw new Exception("Debe ingresar un monto válido");
        }

        if (pago.getMetodoPago() == null) {
            throw new Exception("Debe seleccionar un método de pago");
        }
        
        // Cargar la cita desde la base de datos
        pago.setCita(citaService.getCita(pago.getCita().getIdCita()));
        pago.getCita().setEstadoPago(EstadoPago.Pagado);
        
        return pagoRepository.save(pago);
    }
	
	public Pago editar(Pago pago) throws Exception {
	    if (pago.getCita() == null || pago.getCita().getIdCita() == null) {
	        throw new Exception("Debe seleccionar una cita válida");
	    }

	    if (pago.getMonto() == null || pago.getMonto().doubleValue() <= 0) {
	        throw new Exception("Debe ingresar un monto válido");
	    }

	    if (pago.getMetodoPago() == null) {
	        throw new Exception("Debe seleccionar el método de pago");
	    }
	    
	    pago.setCita(citaService.getCita(pago.getCita().getIdCita()));
	    
	    Pago pagoEdit = getPago(pago.getIdPago());
	    pagoEdit.setCita(pago.getCita());
	    pagoEdit.setMonto(pago.getMonto());
	    pagoEdit.setMetodoPago(pago.getMetodoPago());
	    pagoEdit.setFechaPago(pago.getFechaPago());

	    return pagoRepository.save(pagoEdit);
	}
	
	public Pago getPago(Integer idPago) throws Exception {
        Optional<Pago> optPago = pagoRepository.findById(idPago);
        if (optPago.isEmpty()) {
            throw new Exception("No se encontró el pago con ID: " + idPago);
        }
        return optPago.get();
    }
	
	public void eliminar(Integer idPago) {
        pagoRepository.deleteById(idPago);
    }
	
	public List<Pago> getAll() {
        return pagoRepository.findAll();
    }
	
	public List<Pago> listarPorMetodo(String metodo) {
        return pagoRepository.findByMetodoPago(com.proyecto.entity.MetodoPago.valueOf(metodo));
    }
}
