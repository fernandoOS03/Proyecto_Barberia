package com.proyecto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Barbero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idBarbero;
	private String nombreBarbero;
	private Integer edadBarbero;
	private String emailBarbero;
	private String usuarioBarbero;
	private String contrasenaBarbero;
	
	public Barbero() {
		super();
	}
	

	public Integer getIdBarbero() {
		return idBarbero;
	}

	public void setIdBarbero(Integer idBarbero) {
		this.idBarbero = idBarbero;
	}

	public String getNombreBarbero() {
		return nombreBarbero;
	}

	public void setNombreBarbero(String nombreBarbero) {
		this.nombreBarbero = nombreBarbero;
	}

	public Integer getEdadBarbero() {
		return edadBarbero;
	}

	public void setEdadBarbero(Integer edadBarbero) {
		this.edadBarbero = edadBarbero;
	}

	public String getEmailBarbero() {
		return emailBarbero;
	}

	public void setEmailBarbero(String emailBarbero) {
		this.emailBarbero = emailBarbero;
	}

	public String getUsuarioBarbero() {
		return usuarioBarbero;
	}

	public void setUsuarioBarbero(String usuarioBarbero) {
		this.usuarioBarbero = usuarioBarbero;
	}

	public String getContrasenaBarbero() {
		return contrasenaBarbero;
	}

	public void setContrasenaBarbero(String contrasenaBarbero) {
		this.contrasenaBarbero = contrasenaBarbero;
	}
	
	@Override
    public String toString() {
        return "Barbero [idBarbero=" + idBarbero + ", nombreBarbero=" + nombreBarbero +
               ", edadBarbero=" + edadBarbero + ", emailBarbero=" + emailBarbero +
               ", usuarioBarbero=" + usuarioBarbero + ", contrasenaBarbero=" + contrasenaBarbero + "]";
    }
}
