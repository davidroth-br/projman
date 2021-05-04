package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.service.ProjectsService;
import com.montrealcollege.projman.service.UsersService;
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

    @PostMapping("/validateNew")
    public String validateForm(@ModelAttribute("project") @Valid Projects project, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "newProject";
        }

        service.addProject(project);
        UsersService usersService = new UsersService();
        model.addAttribute("newProjectName", project.getName());
        model.addAttribute("projectList", service.showProjects());
        model.addAttribute("userList", usersService.showUsers());
        return "projectList";
    }
}
