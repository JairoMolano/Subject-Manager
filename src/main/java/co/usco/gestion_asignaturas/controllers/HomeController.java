package co.usco.gestion_asignaturas.controllers;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import co.usco.gestion_asignaturas.services.AsignaturaService;
import co.usco.gestion_asignaturas.services.UserService;

@Controller
public class HomeController {

    @Autowired
    private AsignaturaService asignaturaService;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home/estudiante")
    public String homeEstudiante(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("asignaturas", asignaturaService.getAsignaturasByUser(name));
        return "estudiante/home";
    }

    @GetMapping("/home/docente")
    public String homeDocente(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("asignaturas", asignaturaService.getAsignaturasByUser(name));
        return "docente/home";
    }

    @GetMapping("home/docente/actualizar-horario/{id}")
    public String actualizarHorario(Model model, @PathVariable Long id) {
        model.addAttribute("asignatura", asignaturaService.getAsignaturaById(id).orElseThrow(() -> new RuntimeException("Asignatura not found")));
        return "docente/update-asignatura";
    }

    @PostMapping("home/docente/actualizar-horario/{id}")
    public String actualizarHorarioPost(Model model, @PathVariable Long id, @RequestParam String horario) {
        asignaturaService.updateAsignatura(id, horario);
        return "redirect:/home/docente";
    }

    @GetMapping("/home/rector")
    public String homeRector(Model model, Authentication authentication) {
        model.addAttribute("user", authentication.getUsername());
        model.addAttribute("asignatura", asignaturaService.getAllAsignaturas());
        return "rector/home";
    }

    @GetMapping("/home/rector/registrar-asignatura")
    public String registrarAsignatura(Model model) {
        model.addAttribute("user", userService.getAllUsersWithDocenteRole());
        return "rector/register-asignatura";
    }

    @PostMapping("/home/rector/registrar-asignatura")
    public String registrarAsignaturaPost(@RequestParam String name, 
                                         @RequestParam String descripcion, 
                                         @RequestParam String salon, 
                                         @RequestParam String horario, 
                                         @RequestParam String docente) {
        asignaturaService.createAsignatura(name, descripcion, salon, horario, docente);
        return "redirect:/home/rector";
    }

}
