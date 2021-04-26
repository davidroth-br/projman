package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    @Autowired
    private ProjectsService service;

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("project", new Projects());
        return "newProject";
    }

    @GetMapping("/list")
    public String showAllProjects(Model model) {
        model.addAttribute("projectList", service.showProjects());
        return "projectList";
    }

    @PostMapping("/validate")
    public String validateForm(@ModelAttribute("user") @Valid Projects project, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "newProject";
        }

        service.addProject(project);
        model.addAttribute("newProjectName", project.getName());
        model.addAttribute("projectList", service.showProjects());
        return "projectList";
    }
}
