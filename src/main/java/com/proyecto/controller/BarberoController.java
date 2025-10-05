package com.proyecto.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.entity.Barbero;
import com.proyecto.service.BarberoService;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class BarberoController {

    @Autowired
    private BarberoService barberoService;
   
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/barberos")
    public String listaBarberos(Model model) {
        List<Barbero> barberoList = barberoService.getAll();
        model.addAttribute("barberoList", barberoList);
        model.addAttribute("pageTitle", "Barberos");
        model.addAttribute("pageSubtitle", "Gestión de personal");
        model.addAttribute("content", "barberos/barbero-list");
        
        return "layouts/main-layout";
    }
    
    @GetMapping("/new-barbero")
    public String showNuevoBarbero(Model model) {
        model.addAttribute("barbero", new Barbero());
        model.addAttribute("type", "N");
        //return "barbero";
        return "barberos/barbero";
    }

    @GetMapping("/edit-barbero/{id}")
    public String showEditBarbero(@PathVariable("id") Integer id, Model model) {
        try {
            Barbero barbero = barberoService.getBarbero(id);
            model.addAttribute("barbero", barbero);
            model.addAttribute("type", "E");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return "barbero";
        return "barberos/barbero";
    }

    @GetMapping("/view-barbero/{id}")
    public String showViewBarbero(@PathVariable("id") Integer id, Model model) {
        try {
            Barbero barbero = barberoService.getBarbero(id);
            model.addAttribute("barbero", barbero);
            model.addAttribute("type", "V");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return "barbero";
        return "barberos/barbero";
      
    }

    @GetMapping("/remove-barbero/{id}")
    public String removeBarbero(@PathVariable("id") Integer id, RedirectAttributes redirectAttrs) {
        boolean eliminado = barberoService.eliminar(id);
        if (!eliminado) {
            redirectAttrs.addFlashAttribute("errorEliminacion", true);
            //return "redirect:/barbero-list";
            return "redirect:/barberos/barbero-list";
        }
        //return "redirect:/barbero-list";
        return "redirect:/barberos/barbero-list";
    }

    /*@PostMapping("/save-new-barbero")
    public String saveNewBarbero(@ModelAttribute Barbero barbero) {
        try {
            barberoService.crear(barbero);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/barbero-list";
    }*/
    
    @PostMapping("/save-new-barbero")
    public String saveNewBarbero(@ModelAttribute Barbero barbero, RedirectAttributes redirectAttrs) {
        try {
            Barbero nuevo = barberoService.crear(barbero);
            redirectAttrs.addFlashAttribute("barberoAgregado", true);
            redirectAttrs.addFlashAttribute("nombre", nuevo.getNombreBarbero());
            redirectAttrs.addFlashAttribute("codigo", nuevo.getIdBarbero());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return "redirect:/barbero-list";
        return "redirect:/barberos/barbero-list";
    }

    /*@PostMapping("/save-edit-barbero")
    public String saveEditBarbero(@ModelAttribute Barbero barbero) {
        try {
            barberoService.editar(barbero);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/barbero-list";
    }*/
    
    @PostMapping("/save-edit-barbero")
    public String saveEditBarbero(@ModelAttribute Barbero barbero, RedirectAttributes redirectAttrs) {
        try {
            barberoService.editar(barbero);
            redirectAttrs.addFlashAttribute("barberoEditado", true);
            redirectAttrs.addFlashAttribute("nombre", barbero.getNombreBarbero());
            redirectAttrs.addFlashAttribute("codigo", barbero.getIdBarbero());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return "redirect:/barbero-list";
        return "redirect:/barberos/barbero-list";
    }
    @GetMapping("/barbero-report")
    @ResponseBody
    public void barberoReport(HttpServletResponse response) throws SQLException, JRException, IOException {
        Connection cnx = jdbcTemplate.getDataSource().getConnection();
        
        InputStream jasperStream = this.getClass().getResourceAsStream("/reports/ListadoBarberos.jasper");
        Map<String, Object> params = new HashMap<>();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,cnx);
        
        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", "inline; filename=ListadoBarberos_report.pdf");
        
        final OutputStream ouputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, ouputStream);
        cnx.close();
    }
    
    
}
