package com.proyecto.controller;

import java.util.List;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.proyecto.entity.Cliente;
import com.proyecto.service.ClienteService;

@Controller
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping("/clientes")
	public String listaClientes(Model model) {
	    List<Cliente> clienteList = clienteService.getAll();
	    model.addAttribute("clienteList", clienteList);
	    model.addAttribute("pageTitle", "Clientes");
	    model.addAttribute("pageSubtitle", "Administración de clientes");
	    model.addAttribute("content", "clientes/cliente-list"); // ← CORRECTO
	    
	    return "layouts/main-layout";
	}
	
	@GetMapping("/new-cliente")
	public String showNuevoCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		model.addAttribute("type", "N");

		return "clientes/cliente";
	}

	@GetMapping("/edit-cliente/{id}")
	public String showEditCliente(@PathVariable("id") Integer id, Model model) {
		try {
			model.addAttribute("cliente", clienteService.getCliente(id));
			model.addAttribute("type", "E");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "clientes/cliente";
	}

	@GetMapping("/view-cliente/{id}")
	public String showViewCliente(@PathVariable("id") Integer id, Model model) {
		try {
			model.addAttribute("cliente", clienteService.getCliente(id));
			model.addAttribute("type", "V");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return "cliente";
		return "clientes/cliente";
	}

	@GetMapping("/remove-cliente/{id}")
	public String removeCliente(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		boolean eliminado = clienteService.eliminar(id);
		if (!eliminado) {
			redirectAttributes.addFlashAttribute("errorEliminacion", true);
		}
		return "redirect:/clientes/cliente-list";
		// return "redirect:/cliente-list";
	}

	@PostMapping("/save-new-cliente")
	public String saveNewCliente(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
		try {
			clienteService.crear(cliente);
			redirectAttributes.addFlashAttribute("clienteAgregado", true);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorCliente", e.getMessage());
		}
		return "redirect:/clientes/cliente-list";
		// return "redirect:/cliente-list";
	}

	@PostMapping("/save-edit-cliente")
	public String saveEditCliente(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
		try {
			clienteService.editar(cliente);
			redirectAttributes.addFlashAttribute("clienteEditado", true);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorCliente", e.getMessage());
		}
		// return "redirect:/cliente-list";
		return "redirect:/clientes/cliente-list";
	}
}
