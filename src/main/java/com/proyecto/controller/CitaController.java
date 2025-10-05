package com.proyecto.controller;

import com.proyecto.entity.Cita;
import com.proyecto.entity.Cliente;
import com.proyecto.entity.EstadoCita;
import com.proyecto.entity.EstadoPago;
import com.proyecto.service.BarberoService;
import com.proyecto.service.CitaService;
import com.proyecto.service.ClienteService;
import com.proyecto.service.ServicioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private BarberoService barberoService;

    @GetMapping("/citas")
    public String listaCitas(Model model) {
        List<Cita> citaList = citaService.getAll();
        model.addAttribute("citaList", citaList);
        model.addAttribute("pageTitle", "Citas");
        model.addAttribute("pageSubtitle", "Agendar y ver citas");
        model.addAttribute("content", "citas/cita-list");
        
        return "layouts/main-layout";
    }

    @GetMapping("/new-cita")
    public String showNuevaCita(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("clienteList", clienteService.getAll());
        model.addAttribute("servicioList", servicioService.getAll());
        model.addAttribute("barberoList", barberoService.getAll());
        model.addAttribute("estadoList", EstadoCita.values());
        model.addAttribute("estadoPagoList", EstadoPago.values());
        model.addAttribute("type", "N");
        //return "cita";
        return "citas/cita";
    }

    @GetMapping("/edit-cita/{id}")
    public String showEditCita(@PathVariable("id") Integer id, Model model) {
        try {
            Cita cita = citaService.getCita(id);
            model.addAttribute("cita", cita);
            model.addAttribute("clienteList", clienteService.getAll());
            model.addAttribute("servicioList", servicioService.getAll());
            model.addAttribute("barberoList", barberoService.getAll());
            model.addAttribute("estadoList", EstadoCita.values());
            model.addAttribute("estadoPagoList", EstadoPago.values());
            model.addAttribute("type", "E");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return "cita";
        return "citas/cita";
    }

    @GetMapping("/view-cita/{id}")
    public String showViewCita(@PathVariable("id") Integer id, Model model) {
        try {
            Cita cita = citaService.getCita(id);
            model.addAttribute("cita", cita);
            model.addAttribute("clienteList", clienteService.getAll());
            model.addAttribute("servicioList", servicioService.getAll());
            model.addAttribute("barberoList", barberoService.getAll());
            model.addAttribute("estadoList", EstadoCita.values());
            model.addAttribute("estadoPagoList", EstadoPago.values());
            model.addAttribute("type", "V");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "citas/cita";
        //return "cita";
    }

    @GetMapping("/remove-cita/{id}")
    public String removeCita(@PathVariable("id") Integer id, RedirectAttributes redirectAttrs) {
        try {
            Cita cita = citaService.getCita(id);
            if (cita.getEstadoPago() != null && cita.getEstadoPago().toString().equals("Pagado")) {
                redirectAttrs.addFlashAttribute("errorEliminacion", "La cita no puede ser eliminada porque ya ha sido pagada.");
                //return "redirect:/cita-list";
                return "redirect:/citas/cita-list";
            }
            citaService.eliminar(id);
            redirectAttrs.addFlashAttribute("citaEliminada", true);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("errorEliminacion", "Ocurrió un error al eliminar la cita.");
        }
        //return "redirect:/cita-list";
        return "redirect:/citas/cita-list";
    }

    @PostMapping("/save-new-cita")
    public String saveNewCita(@ModelAttribute Cita cita, RedirectAttributes redirectAttrs) {
        try {
            citaService.crear(cita);
            redirectAttrs.addFlashAttribute("citaAgregada", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return "redirect:/cita-list";
        return "redirect:/citas/cita-list";
    }

    @PostMapping("/save-edit-cita")
    public String saveEditCita(@ModelAttribute Cita cita, RedirectAttributes redirectAttrs) {
        try {
            citaService.editar(cita);
            redirectAttrs.addFlashAttribute("citaEditada", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return "redirect:/cita-list";
        return "redirect:/citas/cita-list";
    }

    // Consulta historial de citas por cliente
    @GetMapping("/consulta-historial-citas")
    public String showConsultaHistorialCitas(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("clienteList", clienteService.getAll());
        model.addAttribute("citas", null);
        //return "consulta-historial-citas";
        return "consultas/consulta-historial-citas";
    }

    @PostMapping("/consulta-historial-citas")
    public String procesarConsultaHistorialCitas(@ModelAttribute Cliente cliente, Model model) {
        model.addAttribute("cliente", cliente);
        model.addAttribute("clienteList", clienteService.getAll());
        model.addAttribute("citas", citaService.listarPorCliente(cliente.getIdCliente()));
        //return "consulta-historial-citas";
        return "consultas/consulta-historial-citas";
    }
}
