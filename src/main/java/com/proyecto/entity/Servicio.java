package com.proyecto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Servicio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idServicio;
	private String nombreServicio;
	private Double precioServicio;
	private Integer duracionServicio;
	
	public Servicio() {
		super();
	}

	public Integer getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public Double getPrecioServicio() {
		return precioServicio;
	}

	public void setPrecioServicio(Double precioServicio) {
		this.precioServicio = precioServicio;
	}

	public Integer getDuracionServicio() {
		return duracionServicio;
	}

	public void setDuracionServicio(Integer duracionServicio) {
		this.duracionServicio = duracionServicio;
	}
	
	@Override
    public String toString() {
        return "Servicio [idServicio=" + idServicio + ", nombreServicio=" + nombreServicio +
               ", precioServicio=" + precioServicio + ", duracionServicio=" + duracionServicio + "]";
    }
	
	
}
