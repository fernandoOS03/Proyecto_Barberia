package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.entity.Horario;
import com.proyecto.repository.HorarioRepository;

@Service
public class HorarioService {
	
	@Autowired
	private HorarioRepository horarioRepository;
	
	public Horario crear(Horario horario) throws Exception {
        if (horario.getBarbero() == null || horario.getBarbero().getIdBarbero() == null) {
            throw new Exception("Debe seleccionar un barbero");
        }

        if (horario.getDiaSemana() == null) {
            throw new Exception("Debe especificar el día de la semana");
        }

        if (horario.getHoraInicio() == null) {
            throw new Exception("Debe ingresar la hora de inicio");
        }

        if (horario.getHoraFin() == null) {
            throw new Exception("Debe ingresar la hora de fin");
        }

        return horarioRepository.save(horario);
    }
	
	public Horario editar(Horario horario) throws Exception {
        if (horario.getBarbero() == null || horario.getBarbero().getIdBarbero() == null) {
            throw new Exception("Debe seleccionar un barbero");
        }

        if (horario.getDiaSemana() == null) {
            throw new Exception("Debe especificar el día de la semana");
        }

        if (horario.getHoraInicio() == null) {
            throw new Exception("Debe ingresar la hora de inicio");
        }

        if (horario.getHoraFin() == null) {
            throw new Exception("Debe ingresar la hora de fin");
        }

        Horario horarioEdit = getHorario(horario.getIdHorario());

        horarioEdit.setBarbero(horario.getBarbero());
        horarioEdit.setDiaSemana(horario.getDiaSemana());
        horarioEdit.setHoraInicio(horario.getHoraInicio());
        horarioEdit.setHoraFin(horario.getHoraFin());

        return horarioRepository.save(horarioEdit);
    }
	
	public void eliminar(Integer idHorario) {
        horarioRepository.deleteById(idHorario);
    }
	
	 public Horario getHorario(Integer idHorario) throws Exception {
	        Optional<Horario> optHorario = horarioRepository.findById(idHorario);
	        if (optHorario.isEmpty()) {
	            throw new Exception("No se encontró el horario con ID: " + idHorario);
	        }
	        return optHorario.get();
	 }
	 
	 public List<Horario> getAll() {
	        return horarioRepository.findAll();
	 }
	 
	 public List<Horario> listarPorBarbero(Integer idBarbero) {
	        return horarioRepository.findByBarberoIdBarbero(idBarbero);
	 }
	 
	 
	 public List<Horario> buscarPorBarbero(Integer idBarbero) {
		    return horarioRepository.findByBarberoIdBarbero(idBarbero);
		}
	
}
