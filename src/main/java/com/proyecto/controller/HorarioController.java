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

    @GetMapping("/horario-list")
    public String listaHorarios(Model model) {
        List<Horario> horarioList = horarioService.getAll();
        model.addAttribute("horarioList", horarioList);
        return "horario-list";
    }

    @GetMapping("/new-horario")
    public String showNuevoHorario(Model model) {
        model.addAttribute("horario", new Horario());
        model.addAttribute("barberoList", barberoService.getAll());
        model.addAttribute("type", "N");
        return "horario";
    }

    @GetMapping("/edit-horario/{id}")
    public String showEditHorario(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("horario", horarioService.getHorario(id));
            model.addAttribute("barberoList", barberoService.getAll());
            model.addAttribute("type", "E");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "horario";
    }

    @GetMapping("/view-horario/{id}")
    public String showViewHorario(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("horario", horarioService.getHorario(id));
            model.addAttribute("barberoList", barberoService.getAll());
            model.addAttribute("type", "V");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "horario";
    }

    @GetMapping("/remove-horario/{id}")
    public String removeHorario(@PathVariable("id") Integer id) {
        horarioService.eliminar(id);
        return "redirect:/horario-list";
    }

    @PostMapping("/save-new-horario")
    public String saveNewHorario(@ModelAttribute Horario horario) {
        try {
            horarioService.crear(horario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/horario-list";
    }

    @PostMapping("/save-edit-horario")
    public String saveEditHorario(@ModelAttribute Horario horario) {
        try {
            horarioService.editar(horario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/horario-list";
    }

    @GetMapping("/consulta-horarios-barbero")
    public String showConsultaHorarios(Model model) {
        model.addAttribute("barbero", new Barbero());
        model.addAttribute("barberoList", barberoService.getAll());
        model.addAttribute("horarios", null);
        return "consulta-horarios-barbero";
    }

    @PostMapping("/consulta-horarios-barbero")
    public String procesarConsultaHorarios(@ModelAttribute Barbero barbero, Model model) {
        model.addAttribute("barbero", barbero);
        model.addAttribute("barberoList", barberoService.getAll());
        model.addAttribute("horarios", horarioService.listarPorBarbero(barbero.getIdBarbero()));
        return "consulta-horarios-barbero";
    }

}
