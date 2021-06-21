package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.service.ProjectsService;
import com.montrealcollege.projman.service.UsersService;
import com.montrealcollege.projman.utils.UsersConverter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

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
    @GetMapping("/admin/list")
    public String showAllProjects(@RequestParam("message") String message, Model model) {

        model.addAttribute("message", message);
        model.addAttribute("projectList", projectsService.showProjects());
        return "projects/projectList";
    }

    //NEW
    @GetMapping("/admin/new")
    public String showForm(Model model) {

        model.addAttribute("project", new Projects());
        model.addAttribute("userList", usersService.showUsers());
        model.addAttribute("addOrEdit", "Add");
        model.addAttribute("action", "/projects/admin/validateNew");
        return "projects/projectForm";
    }

    @PostMapping("/admin/validateNew")
    public String validateForm(@ModelAttribute("project") @Valid Projects project, BindingResult errors, Model model) {

        if (leaderNotSelected(project)) project.getUsers().add(project.getLeader());

        boolean isEndDateBeforeStartDate = project.getEndDate() != null && project.getStartDate() != null && project.getEndDate().compareTo(project.getStartDate()) <= 0;

        if (errors.hasErrors() || isEndDateBeforeStartDate) {
            model.addAttribute("userList", usersService.showUsers());
            model.addAttribute("isEndDateBeforeStartDate", isEndDateBeforeStartDate);
            model.addAttribute("addOrEdit", "Add");
            model.addAttribute("action", "/projects/admin/validateNew");
            return "projects/projectForm";
        }

        projectsService.addProject(project);

        model.addAttribute("message", project.getName() + " was successfully added!");
        model.addAttribute("projectList", projectsService.showProjects());
        return "projects/projectList";
    }

    // EDIT
    @GetMapping("/admin/edit/{id}")
    public String editProject(@PathVariable Long id, Model model) {

        model.addAttribute("project", projectsService.getProjectById(id));
        model.addAttribute("userList", usersService.showUsers());
        model.addAttribute("addOrEdit", "Edit");
        model.addAttribute("action", "/projects/admin/validateEdit");
        return "projects/projectForm";
    }

    @PostMapping("/admin/validateEdit")
    public Object validateEdit(@ModelAttribute("project") @Valid Projects project, BindingResult errors, Model model) {

        if (leaderNotSelected(project)) project.getUsers().add(project.getLeader());

        boolean isEndDateBeforeStartDate = project.getEndDate() != null && project.getStartDate() != null && project.getEndDate().compareTo(project.getStartDate()) <= 0;

        if (errors.hasErrors() || isEndDateBeforeStartDate) {
            model.addAttribute("userList", usersService.showUsers());
            model.addAttribute("isEndDateBeforeStartDate", isEndDateBeforeStartDate);
            model.addAttribute("addOrEdit", "Edit");
            model.addAttribute("action", "/projects/admin/validateEdit");
            return "projects/projectForm";
        }

        projectsService.editProject(project);

        model.addAttribute("message", project.getName() + " was successfully edited!");
        return new ModelAndView("redirect:/projects/admin/list", (Map<String, ?>) model);
    }

    private boolean leaderNotSelected(Projects project) {
        boolean notSelected = true;
        for (Users selectedUser : project.getUsers()) {
            if (selectedUser.equals(project.getLeader())) {
                notSelected = false;
                break;
            }
        }
        return notSelected;
    }

    // DELETE
    @GetMapping("/admin/remove/{id}")
    public String removeUser(@PathVariable Long id, Model model) {

        String message = projectsService.getProjectById(id).getName() + " was successfully removed!";

        projectsService.removeProject(id);

        model.addAttribute("message", message);
        model.addAttribute("projectList", projectsService.showProjects());
        return "projects/projectList";
    }

    // DETAILS
    @GetMapping("/admin/details/{id}")
    public String showProject(@PathVariable Long id, Model model) {

        model.addAttribute("project", projectsService.getProjectById(id));
        return "projects/projectDetails";
    }

    // SHOW PROJECT MEMBERS
    @GetMapping("/user/members/{id}")
    public String showMembers(@PathVariable Long id, Model model) {

        model.addAttribute("project", projectsService.getProjectById(id));
        return "projects/projectMembers";
    }

    // MANAGE PROJECT MEMBERS
    @GetMapping("/leader/manageMembers/{id}")
    public String manageMembers(@PathVariable Long id, Model model) {

        Projects project = projectsService.getProjectById(id);

        model.addAttribute("availableUserList", getAvailableUsers(project));
        model.addAttribute("project", project);
        return "projects/manageMembers";
    }

    @PostMapping("/leader/addProjectMember")
    public Object changeState(@RequestParam("projectId") Long id, @RequestParam("availableUsers") Long newMemberId, Model model) {
        Projects project = projectsService.getProjectById(id);
        project.getUsers().add(usersService.getUserById(newMemberId));

        projectsService.editProject(project);

        model.addAttribute("availableUserList", getAvailableUsers(project));
        model.addAttribute("project", project);
        return "projects/manageMembers";
    }

    @NotNull
    private Map<Long, String> getAvailableUsers(Projects project) {
        Map<Long, String> availableUsers = new LinkedHashMap<>();
        for (Users user : usersService.showUsers()) {
            if (!project.getUsers().contains(user)) {
                availableUsers.put(user.getId(), user.getFullName());
            }
        }
        return availableUsers;
    }
}
