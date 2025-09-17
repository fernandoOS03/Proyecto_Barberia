package com.proyecto.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Cita {
	
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Integer idCita;

	 private LocalDate fecha;
	 private LocalTime hora;

	 @Enumerated(EnumType.STRING)
	 private EstadoCita estado = EstadoCita.Programada;
	 
	 @Enumerated(EnumType.STRING)
	 private EstadoPago estadoPago = EstadoPago.Pendiente;
	 	
	 @ManyToOne
	 @JoinColumn(name = "idCliente")
	 private Cliente cliente;

	 @ManyToOne
	 @JoinColumn(name = "idServicio")
	 private Servicio servicio;

	 @ManyToOne
	 @JoinColumn(name = "idBarbero")
	 private Barbero barbero;
	 
	 public Cita() {
		 super();
	 }

	public Integer getIdCita() {
		return idCita;
	}

	public void setIdCita(Integer idCita) {
		this.idCita = idCita;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public EstadoCita getEstado() {
		return estado;
	}

	public void setEstado(EstadoCita estado) {
		this.estado = estado;
	}

	public EstadoPago getEstadoPago() {
		return estadoPago;
	}

	public void setEstadoPago(EstadoPago estadoPago) {
		this.estadoPago = estadoPago;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Barbero getBarbero() {
		return barbero;
	}

	public void setBarbero(Barbero barbero) {
		this.barbero = barbero;
	}

	@Override
	public String toString() {
		return "Cita [idCita=" + idCita + ", fecha=" + fecha + ", hora=" + hora + ", estado=" + estado + ", estadoPago="
				+ estadoPago + ", cliente=" + cliente + ", servicio=" + servicio + ", barbero=" + barbero + "]";
	}

	 
}
