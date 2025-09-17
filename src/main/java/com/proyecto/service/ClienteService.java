package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.proyecto.entity.Cliente;
import com.proyecto.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Cliente crear(Cliente cliente) throws Exception {
        if (cliente.getNombreCliente() == null || cliente.getNombreCliente().isEmpty()) {
            throw new Exception("Debe ingresar el nombre del cliente");
        }

        if (cliente.getTelefonoCliente() == null || cliente.getTelefonoCliente().isEmpty()) {
            throw new Exception("Debe ingresar el número de teléfono del cliente");
        }

        if (cliente.getEmailCliente() == null || cliente.getEmailCliente().isEmpty()) {
            throw new Exception("Debe ingresar el correo electrónico del cliente");
        }

        return clienteRepository.save(cliente);
    }
	
	public Cliente editar(Cliente cliente) throws Exception {
        if (cliente.getNombreCliente() == null || cliente.getNombreCliente().isEmpty()) {
            throw new Exception("Debe ingresar el nombre del cliente");
        }

        if (cliente.getTelefonoCliente() == null || cliente.getTelefonoCliente().isEmpty()) {
            throw new Exception("Debe ingresar el número de teléfono del cliente");
        }

        if (cliente.getEmailCliente() == null || cliente.getEmailCliente().isEmpty()) {
            throw new Exception("Debe ingresar el correo electrónico del cliente");
        }

        Cliente clienteEdit = getCliente(cliente.getIdCliente());

        clienteEdit.setNombreCliente(cliente.getNombreCliente());
        clienteEdit.setTelefonoCliente(cliente.getTelefonoCliente());
        clienteEdit.setEmailCliente(cliente.getEmailCliente());

        return clienteRepository.save(clienteEdit);
    }
	
	public boolean eliminar(Integer idCliente) {
		if (tieneCitasAsignadas(idCliente)) {
	        return false;
	    }
        clienteRepository.deleteById(idCliente);
        return true;
    }
	
	private boolean tieneCitasAsignadas(Integer idCliente) {
	    String sql = "SELECT COUNT(*) FROM cita WHERE id_Cliente = ?";
	    Integer total = jdbcTemplate.queryForObject(sql, Integer.class, idCliente); // con minúscula
	    return total != null && total > 0;
	}
	
	public Cliente getCliente(Integer idCliente) throws Exception {
        Optional<Cliente> optCliente = clienteRepository.findById(idCliente);
        if (optCliente.isEmpty()) {
            throw new Exception("No se encontró el cliente con ID: " + idCliente);
        }
        return optCliente.get();
    }
	
	public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }
	
}
