package com.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.proyecto.entity.Barbero;
import com.proyecto.entity.Horario;
import com.proyecto.service.BarberoService;
import com.proyecto.service.HorarioService;

@Controller
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private BarberoService barberoService;

    @GetMapping("/horarios")
    public String listaHorarios(Model model) {
        List<Horario> horarioList = horarioService.getAll();
        model.addAttribute("horarioList", horarioList);
        model.addAttribute("pageTitle", "Horarios");
        model.addAttribute("pageSubtitle", "Definir disponibilidad");
        model.addAttribute("content", "horarios/horario-list");
        
        return "layouts/main-layout";
    }

    @GetMapping("/new-horario")
    public String showNuevoHorario(Model model) {
        model.addAttribute("horario", new Horario());
        model.addAttribute("barberoList", barberoService.getAll());
        model.addAttribute("type", "N");
        
        model.addAttribute("pageTitle", "Horarios");
        model.addAttribute("pageSubtitle", "Definir Nuevo Horario");
        model.addAttribute("content", "horarios/horario");
        
        return "layouts/main-layout";
        
 
    }

    @GetMapping("/edit-horario/{id}")
    public String showEditHorario(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("horario", horarioService.getHorario(id));
            model.addAttribute("barberoList", barberoService.getAll());
            model.addAttribute("type", "E");
            model.addAttribute("pageTitle", "Horarios");
            model.addAttribute("pageSubtitle", "Editar Horario");
            model.addAttribute("content", "horarios/horario");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "layouts/main-layout";
    }

    @GetMapping("/view-horario/{id}")
    public String showViewHorario(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("horario", horarioService.getHorario(id));
            model.addAttribute("barberoList", barberoService.getAll());
            model.addAttribute("type", "V");
            model.addAttribute("pageTitle", "Horarios");
            model.addAttribute("pageSubtitle", "Ver Horario");
            model.addAttribute("content", "horarios/horario");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "layouts/main-layout";
    }

    @GetMapping("/remove-horario/{id}")
    public String removeHorario(@PathVariable("id") Integer id) {
        horarioService.eliminar(id);
        return "redirect:/horarios";
    }

    @PostMapping("/save-new-horario")
    public String saveNewHorario(@ModelAttribute Horario horario) {
        try {
            horarioService.crear(horario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/horarios";
    }

    @PostMapping("/save-edit-horario")
    public String saveEditHorario(@ModelAttribute Horario horario) {
        try {
            horarioService.editar(horario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/horarios";
    }

    @GetMapping("/horarios-barberos")
    public String showConsultaHorarios(Model model) {
        model.addAttribute("barbero", new Barbero());
        model.addAttribute("barberoList", barberoService.getAll());
        model.addAttribute("horarios", null);
        model.addAttribute("pageTitle", "Consulta Horarios");
        model.addAttribute("pageSubtitle", "Ver horarios de barberos");
        model.addAttribute("content", "consultas/consulta-horarios-barbero");
        return "layouts/main-layout";
    }


    @PostMapping("/consulta-horarios-barbero")
    public String procesarConsultaHorarios(@ModelAttribute Barbero barbero, Model model) {
        model.addAttribute("barbero", barbero);
        model.addAttribute("barberoList", barberoService.getAll());
        model.addAttribute("horarios", horarioService.listarPorBarbero(barbero.getIdBarbero()));
        model.addAttribute("pageTitle", "Consulta Horarios");
        model.addAttribute("pageSubtitle", "Ver horarios de barberos");
        model.addAttribute("content", "consultas/consulta-horarios-barbero"); 
        
        return "layouts/main-layout"; 
    }
    
    
    

}
