package com.proyecto.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.proyecto.entity.Pago;
import com.proyecto.service.CitaService;
import com.proyecto.service.PagoService;

@Controller
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private CitaService citaService;

    @GetMapping("/pagos")
    public String listaPagos(Model model) {
        List<Pago> pagoList = pagoService.getAll();
        model.addAttribute("pagoList", pagoList);
        model.addAttribute("pageTitle", "Pagos");
        model.addAttribute("pageSubtitle", "Registrar y ver pagos");
        model.addAttribute("content", "pagos/pago-list");
        
        return "layouts/main-layout";
    }

    @GetMapping("/new-pago")
    public String showNuevoPago(Model model) {
        model.addAttribute("pago", new Pago());
        model.addAttribute("citaList", citaService.obtenerCitasPendientesPago());
        model.addAttribute("type", "N");
        return "pagos/pago";
    }

    @GetMapping("/edit-pago/{id}")
    public String showEditPago(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("pago", pagoService.getPago(id));
            model.addAttribute("citaList", citaService.getAll());
            model.addAttribute("type", "E");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pagos/pago";
    }

    @GetMapping("/view-pago/{id}")
    public String showViewPago(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("pago", pagoService.getPago(id));
            model.addAttribute("citaList", citaService.getAll());
            model.addAttribute("type", "V");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pagos/pago";
    }

    @GetMapping("/remove-pago/{id}")
    public String removePago(@PathVariable("id") Integer id) {
        pagoService.eliminar(id);
        return "redirect:/pagos/pago-list";
    }

    @PostMapping("/save-new-pago")
    public String saveNewPago(@ModelAttribute Pago pago) {
        try {
            if (pago.getFechaPago() == null) {
                pago.setFechaPago(LocalDateTime.now()); 
            }
            pagoService.crear(pago);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/pagos/pago-list";
    }

    @PostMapping("/save-edit-pago")
    public String saveEditPago(@ModelAttribute Pago pago) {
        try {
            System.out.println("FechaPago recibida: " + pago.getFechaPago());
            pagoService.editar(pago);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/pagos/pago-list";
    }
}
