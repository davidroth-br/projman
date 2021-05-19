package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.service.ProjectsService;
import com.montrealcollege.projman.service.UsersService;
import com.montrealcollege.projman.utils.UsersConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    @Autowired
    private ProjectsService service;

    @Autowired
    private UsersService usersService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Users.class, new UsersConverter(usersService));
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("project", new Projects());
        model.addAttribute("userList", usersService.showUsers());
        return "projects/newProject";
    }

    @GetMapping("/list")
    public String showAllProjects(Model model) {
        model.addAttribute("projectList", service.showProjects());
        return "projects/projectList";
    }

    @PostMapping("/validateNew")
    public String validateForm(@ModelAttribute("project") @Valid Projects project,
                               BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("userList", usersService.showUsers());
            return "projects/newProject";
        }

        if (project.getEndDate().compareTo(project.getStartDate()) <= 0) {
            model.addAttribute("isEndDateBeforeStartDate", true);
            return "projects/newProject";
        }

        service.addProject(project);

        model.addAttribute("newProjectName", project.getName());
        model.addAttribute("projectList", service.showProjects());

        return "projects/projectList";
    }
}
