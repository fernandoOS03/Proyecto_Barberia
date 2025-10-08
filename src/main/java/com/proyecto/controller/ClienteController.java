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
        model.addAttribute("content", "clientes/cliente-list");
        
        return "layouts/main-layout";
    }
    
    @GetMapping("/clientes/nuevo")
    public String showNuevoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("type", "N");
        model.addAttribute("pageTitle", "Clientes");
        model.addAttribute("pageSubtitle", "Agregar un nuevo Cliente"); // Corregido: era pageTitle duplicado
        model.addAttribute("content", "clientes/cliente");
        return "layouts/main-layout";
    }

    @GetMapping("/clientes/editar/{id}") // CORREGIDO: consistencia en rutas
    public String showEditCliente(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("cliente", clienteService.getCliente(id));
            model.addAttribute("type", "E");
            model.addAttribute("pageTitle", "Clientes");
            model.addAttribute("pageSubtitle", "Editar Cliente");
            model.addAttribute("content", "clientes/cliente");
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/clientes";
        }
        return "layouts/main-layout"; // CORREGIDO: retornar el layout completo
    }
    

    @GetMapping("/clientes/ver/{id}") // CORREGIDO: consistencia en rutas
    public String showViewCliente(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("cliente", clienteService.getCliente(id));
            model.addAttribute("type", "V");
            model.addAttribute("pageTitle", "Clientes");
            model.addAttribute("pageSubtitle", "Detalle del Cliente");
            model.addAttribute("content", "clientes/cliente");
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/clientes";
        }
        return "layouts/main-layout"; // CORREGIDO: retornar el layout completo
    }

    @GetMapping("/clientes/eliminar/{id}") // CORREGIDO: consistencia en rutas
    public String removeCliente(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean eliminado = clienteService.eliminar(id);
        if (!eliminado) {
            redirectAttributes.addFlashAttribute("errorEliminacion", true);
        }
        return "redirect:/clientes"; // CORREGIDO: redirigir a la URL correcta
    }

    @PostMapping("/clientes/guardar-nuevo")
    public String saveNewCliente(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        try {
            clienteService.crear(cliente);
            redirectAttributes.addFlashAttribute("clienteAgregado", true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorCliente", e.getMessage());
        }
        return "redirect:/clientes"; // CORREGIDO: redirigir a la URL correcta
    }

    @PostMapping("/clientes/guardar-editar") // CORREGIDO: consistencia en nombres
    public String saveEditCliente(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        try {
            clienteService.editar(cliente);
            redirectAttributes.addFlashAttribute("clienteEditado", true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorCliente", e.getMessage());
        }
        return "redirect:/clientes"; // CORREGIDO: redirigir a la URL correcta
    }
}