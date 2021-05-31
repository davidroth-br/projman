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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private UsersService usersService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Users.class, new UsersConverter(usersService));
    }

    //LIST ALL
    @GetMapping("/list")
    public String showAllProjects(@RequestParam("message") String message,
                                  Model model) {

        model.addAttribute("message", message);
        model.addAttribute("projectList", projectsService.showProjects());
        return "projects/projectList";
    }

    //NEW
    @GetMapping("/new")
    public String showForm(Model model) {

        model.addAttribute("project", new Projects());
        model.addAttribute("leaderId", 0);
        model.addAttribute("userList", usersService.showUsers());
        return "projects/newProject";
    }

    @PostMapping("/validateNew")
    public String validateForm(@ModelAttribute("project") @Valid Projects project,
                               BindingResult errors, Model model) {

        boolean isEndDateBeforeStartDate = project.getEndDate() != null && project.getStartDate() != null && project.getEndDate().compareTo(project.getStartDate()) <= 0;

        if (errors.hasErrors() || isEndDateBeforeStartDate) {
            model.addAttribute("leaderId", project.getLeader() == null ? 0L : project.getLeader().getId());
            model.addAttribute("userList", usersService.showUsers());
            model.addAttribute("isEndDateBeforeStartDate", isEndDateBeforeStartDate);
            return "projects/newProject";
        }

        String message = project.getName() + " was successfully added!";
        projectsService.addProject(project);

        model.addAttribute("message", message);
        model.addAttribute("projectList", projectsService.showProjects());
        return "projects/projectList";
    }

    // EDIT
    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable Long id, Model model) {
        Projects project = projectsService.getProjectById(id);

        model.addAttribute("project", project);
        model.addAttribute("leaderId", project.getLeader() == null ? 0L : project.getLeader().getId());
        model.addAttribute("userList", usersService.showUsers());
        return "projects/editProject";
    }

    @PostMapping("/validateEdit")
    public Object validateEdit(@ModelAttribute("project") @Valid Projects project,
                               BindingResult errors, Model model) {

        boolean isEndDateBeforeStartDate = project.getEndDate() != null && project.getStartDate() != null && project.getEndDate().compareTo(project.getStartDate()) <= 0;

        if (errors.hasErrors() || isEndDateBeforeStartDate) {
            model.addAttribute("leaderId", project.getLeader() == null ? 0L : project.getLeader().getId());
            model.addAttribute("userList", usersService.showUsers());
            model.addAttribute("isEndDateBeforeStartDate", isEndDateBeforeStartDate);
            return "projects/editProject";
        }

        projectsService.editProject(project);

        String message = project.getName() + " was successfully edited!";
        model.addAttribute("message", message);
        return new ModelAndView("redirect:/projects/list", (Map<String, ?>) model);
    }

    // DELETE
    @GetMapping("/remove/{id}")
    public String removeUser(@PathVariable Long id, Model model) {

        Projects project = projectsService.getProjectById(id);
        String message = project.getName() + " was successfully removed!";

        projectsService.removeProject(id);

        model.addAttribute("message", message);
        model.addAttribute("projectList", projectsService.showProjects());
        return "projects/projectList";
    }

    // DETAILS
    @GetMapping("/details/{id}")
    public String showProject(@PathVariable Long id, Model model) {
        Projects project = projectsService.getProjectById(id);

        model.addAttribute("project", project);
        return "projects/projectDetails";
    }
}
