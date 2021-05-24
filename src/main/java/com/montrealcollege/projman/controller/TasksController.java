package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.service.ProjectsService;
import com.montrealcollege.projman.service.TasksService;
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
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TasksService service;

//    @Autowired
//    private UsersService usersService;

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(Users.class, new UsersConverter(usersService));
//    }

    //LIST ALL
    @GetMapping("/list")
    public String showAllTasks(@RequestParam("message") String message,
                                  Model model) {

        model.addAttribute("message", message);
        model.addAttribute("taskList", service.showTasks());
        return "tasks/taskList";
    }
//
//    //NEW
//    @GetMapping("/new")
//    public String showForm(Model model) {
//        model.addAttribute("project", new Projects());
//        model.addAttribute("leaderId", 0);
//        model.addAttribute("userList", usersService.showUsers());
//        return "projects/newProject";
//    }
//
//    @PostMapping("/validateNew")
//    public String validateForm(@ModelAttribute("project") @Valid Projects project,
//                               BindingResult errors, Model model) {
//
//        if (errors.hasErrors()) {
//            model.addAttribute("leaderId", project.getLeader().getId());
//            model.addAttribute("userList", usersService.showUsers());
//            return "projects/newProject";
//        }
//
//        if (project.getEndDate().compareTo(project.getStartDate()) <= 0) {
//            model.addAttribute("leaderId", project.getLeader().getId());
//            model.addAttribute("isEndDateBeforeStartDate", true);
//            return "projects/newProject";
//        }
//
//        String message = project.getName() + " was successfully added!";
//        service.addProject(project);
//
//        model.addAttribute("message", message);
//        model.addAttribute("projectList", service.showProjects());
//
//        return "projects/projectList";
//    }
//
//    // EDIT
//    @GetMapping("/edit/{id}")
//    public String editProject(@PathVariable Long id, Model model) {
//        Projects project = service.getProjectById(id);
//
//        System.out.println(project);
//
//        Long leaderId = project.getLeader() != null ? project.getLeader().getId() : 0;
//
//        System.out.println(leaderId);
//
//        model.addAttribute("project", project);
//        model.addAttribute("leaderId", leaderId);
//        model.addAttribute("userList", usersService.showUsers());
//        return "projects/editProject";
//    }
//
//    @PostMapping("/validateEdit")
//    public Object validateEdit(@ModelAttribute("project") @Valid Projects project,
//                               BindingResult errors, Model model) {
//
//        if (errors.hasErrors()) {
//            model.addAttribute("leaderId", project.getLeader().getId());
//            model.addAttribute("userList", usersService.showUsers());
//            return "projects/editProject";
//        }
//
//        if (project.getEndDate().compareTo(project.getStartDate()) <= 0) {
//            model.addAttribute("leaderId", project.getLeader().getId());
//            model.addAttribute("isEndDateBeforeStartDate", true);
//            return "projects/editProject";
//        }
//
//        service.editProject(project);
//
//        String message = project.getName() + " was successfully edited!";
//        model.addAttribute("message", message);
//        return new ModelAndView("redirect:/projects/list", (Map<String, ?>) model);
////        return "projects/projectList";
//    }
//
//    // DELETE
//    @GetMapping("/remove/{id}")
//    public String removeUser(@PathVariable Long id, Model model) {
//
//        Projects project = service.getProjectById(id);
//        String message = project.getName() + " was successfully removed!";
//
//        service.removeProject(id);
//
//        model.addAttribute("message", message);
//        model.addAttribute("projectList", service.showProjects());
//        return "projects/projectList";
//    }
//
//    // DETAILS
//    @GetMapping("/details/{id}")
//    public String showProject(@PathVariable Long id, Model model) {
//        Projects project = service.getProjectById(id);
//
//        model.addAttribute("project", project);
//        return "projects/projectDetails";
//    }
}
