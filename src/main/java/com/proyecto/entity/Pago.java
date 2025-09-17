package com.proyecto.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Pago {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPago;

    private Double monto;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaPago;

    @ManyToOne
    @JoinColumn(name = "idCita")
    private Cita cita;
    
    public Pago() {
        super();
    }

	public Integer getIdPago() {
		return idPago;
	}

	public void setIdPago(Integer idPago) {
		this.idPago = idPago;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public MetodoPago getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

	public LocalDateTime getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDateTime fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Cita getCita() {
		return cita;
	}

	public void setCita(Cita cita) {
		this.cita = cita;
	}
    
	@Override
    public String toString() {
        return "Pago [idPago=" + idPago + ", monto=" + monto +
               ", metodoPago=" + metodoPago + ", fechaPago=" + fechaPago +
               ", cita=" + (cita != null ? cita.getIdCita() : null) + "]";
    }
    
}
