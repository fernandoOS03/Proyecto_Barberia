package com.proyecto.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.entity.MetodoPago;
import com.proyecto.entity.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer>{
	
	// Para reporte de ingresos
    List<Pago> findByFechaPagoBetween(LocalDateTime desde, LocalDateTime hasta);

    // Para obtener pagos de una cita específica
    List<Pago> findByCitaIdCita(Integer idCita);
    
    //Método de pago
    List<Pago> findByMetodoPago(MetodoPago metodoPago);
}
