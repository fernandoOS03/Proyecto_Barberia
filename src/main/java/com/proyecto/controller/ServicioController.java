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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.entity.Servicio;
import com.proyecto.service.ServicioService;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class ServicioController {

    @Autowired
    private ServicioService servicioService;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/servicios")
    public String listaServicios(Model model) {
        List<Servicio> servicioList = servicioService.getAll();
        model.addAttribute("servicioList", servicioList);
        model.addAttribute("pageTitle", "Servicios");
        model.addAttribute("pageSubtitle", "Catálogo de servicios");
        model.addAttribute("content", "servicios/servicio-list");
        
        return "layouts/main-layout";
    }

    @GetMapping("/new-servicio")
    public String nuevoServicio(Model model) {
        Servicio servicio = new Servicio();
        // Solución: Generar un ID temporal o usar un valor por defecto
        servicio.setIdServicio(0); // O puedes usar un ID generado automáticamente
        model.addAttribute("servicio", servicio);
        model.addAttribute("type", "N");
        return "servicio";
    }

    @GetMapping("/edit-servicio/{id}")
    public String showEditServicio(@PathVariable("id") Integer id, Model model) {
        try {
            Servicio servicio = servicioService.getServicio(id);
            model.addAttribute("servicio", servicio);
            model.addAttribute("type", "E");
        } catch (Exception e) {
            e.printStackTrace();
            // Mejor manejo de errores
            model.addAttribute("errorServicio", "No se pudo cargar el servicio");
            return "redirect:/servicio-list";
        }
        return "servicio";
    }

    @GetMapping("/view-servicio/{id}")
    public String showViewServicio(@PathVariable("id") Integer id, Model model) {
        try {
            Servicio servicio = servicioService.getServicio(id);
            model.addAttribute("servicio", servicio);
            model.addAttribute("type", "V");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "servicio";
    }

    @GetMapping("/remove-servicio/{id}")
    public String removeServicio(@PathVariable("id") Integer id, RedirectAttributes redirectAttrs) {
        try {
            servicioService.eliminar(id);
            redirectAttrs.addFlashAttribute("servicioEliminado", true); // ✅ Se muestra modal de éxito
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("citas registradas")) {
                redirectAttrs.addFlashAttribute("errorEliminacionServicio", true); // ⚠️ Modal específico
            } else {
                redirectAttrs.addFlashAttribute("errorServicio", "No se pudo eliminar el servicio.");
            }
        }
        return "redirect:/servicio-list";
    }

    @PostMapping("/save-new-servicio")
    public String saveNewServicio(@ModelAttribute Servicio servicio, RedirectAttributes redirectAttrs) {
        try {
            // Al crear un nuevo servicio, no necesitas el ID
            servicio.setIdServicio(null); // Dejar que la base de datos genere el ID
            servicioService.crear(servicio);
            redirectAttrs.addFlashAttribute("servicioAgregado", true);
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorServicio", e.getMessage());
        }
        return "redirect:/servicio-list";
    }

    @PostMapping("/save-edit-servicio")
    public String saveEditServicio(@ModelAttribute Servicio servicio, RedirectAttributes redirectAttrs) {
        try {
            servicioService.editar(servicio);
            redirectAttrs.addFlashAttribute("servicioEditado", true);
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorServicio", e.getMessage());
        }
        return "redirect:/servicio-list";
    }
    @GetMapping("/servicio-report")
    @ResponseBody
    public void servicioReport(HttpServletResponse response) throws SQLException, JRException, IOException {
        Connection cnx = jdbcTemplate.getDataSource().getConnection();
        
        InputStream jasperStream = this.getClass().getResourceAsStream("/reports/Blank_A4.jasper");
        Map<String, Object> params = new HashMap<>();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,cnx);
        
        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", "inline; filename=ListadoServicios_report.pdf");
        
        final OutputStream ouputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, ouputStream);
        cnx.close();
    }
}
