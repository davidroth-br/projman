package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Tasks;
import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.service.ProjectsService;
import com.montrealcollege.projman.service.UsersService;
import com.montrealcollege.projman.utils.UsersConverter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static com.montrealcollege.projman.utils.Helpers.today;

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
    public String showAllProjects(Model model) {

        model.addAttribute("projectList", projectsService.showProjects());
        return "projects/projectList";
    }

    //NEW
    @GetMapping("/admin/new")
    public String showForm(Model model) {

        model.addAttribute("project", new Projects());
        model.addAttribute("userList", usersService.showUsers());
        model.addAttribute("addOrEdit", "New");
        model.addAttribute("action", "/projects/admin/validateNew");
        return "projects/projectForm";
    }

    @PostMapping("/admin/validateNew")
    public String validateForm(@ModelAttribute("project") @Valid Projects project, BindingResult errors, Model model) {

        project.getUsers().add(project.getLeader());

        boolean isEndDateBeforeStartDate = project.getEndDate() != null &&
                project.getStartDate() != null &&
                !project.getEndDate().after(project.getStartDate());
        boolean isStartDateInPast = project.getStartDate() != null && project.getStartDate().before(today());
        boolean isEndDateInPast = project.getEndDate() != null && !project.getEndDate().after(today());

        if (errors.hasErrors() || isEndDateBeforeStartDate || isEndDateInPast || isStartDateInPast) {
            model.addAttribute("userList", usersService.showUsers());
            model.addAttribute("startDateMessage", isStartDateInPast ? "Start date must not be in the past." : "");
            model.addAttribute("endDateMessage", isEndDateBeforeStartDate ? "End date must be after start date." : isEndDateInPast ? "End date must be in the future." : "");
            model.addAttribute("addOrEdit", "New");
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

        boolean isEndDateBeforeStartDate = project.getEndDate() != null &&
                project.getStartDate() != null &&
                !project.getEndDate().after(project.getStartDate());
        boolean isStartDateInPast = project.getStartDate() != null &&
                !project.getStartDate().equals(projectsService.getProjectById(project.getId()).getStartDate()) &&
                project.getStartDate().before(today());
        boolean isEndDateInPast = project.getEndDate() != null &&
                !project.getEndDate().equals(projectsService.getProjectById(project.getId()).getEndDate()) &&
                !project.getEndDate().after(today());

        if (errors.hasErrors() || isEndDateBeforeStartDate || isEndDateInPast || isStartDateInPast) {
            model.addAttribute("userList", usersService.showUsers());
            model.addAttribute("startDateMessage", isStartDateInPast ? "Start date must not be in the past." : "");
            model.addAttribute("endDateMessage", isEndDateBeforeStartDate ? "End date must be after start date." : isEndDateInPast ? "End date must be in the future." : "");
            model.addAttribute("addOrEdit", "Edit");
            model.addAttribute("action", "/projects/admin/validateEdit");
            return "projects/projectForm";
        }

        project.setUsers(projectsService.getProjectById(project.getId()).getUsers());
        project.getUsers().add(project.getLeader());

        projectsService.editProject(project);

        model.addAttribute("message", project.getName() + " was successfully edited!");
        model.addAttribute("projectList", projectsService.showProjects());
        return "projects/projectList";
    }

    // DELETE
    @GetMapping("/admin/remove/{id}")
    public String removeUser(@PathVariable Long id, Model model) {

        String message = projectsService.getProjectById(id).getName() + " was successfully deleted!";

        try {
            projectsService.removeProject(id);
        } catch (DataIntegrityViolationException e) {
            message = "Unable to delete.<br>" + projectsService.getProjectById(id).getName() + " has tasks associated to it.";
        }


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

    @PostMapping("/leader/addMember")
    public Object addMember(@RequestParam("projectId") Long id, @RequestParam("availableUsers") Long newMemberId, Model model) {
        Projects project = projectsService.getProjectById(id);
        project.getUsers().add(usersService.getUserById(newMemberId));

        projectsService.editProject(project);

        model.addAttribute("availableUserList", getAvailableUsers(project));
        model.addAttribute("project", project);
        return "projects/manageMembers";
    }

    @GetMapping("/leader/removeMember/{userId}/{projectId}")
    public String removeMember(@PathVariable Long userId, @PathVariable Long projectId, Model model) {
        Projects project = projectsService.getProjectById(projectId);
        Users member = usersService.getUserById(userId);

        boolean canRemove = true;
        for (Tasks task : project.getTasks()) {
            System.out.println(task.toString());
            if (task.getUsers().contains(member)) {
                canRemove = false;
                model.addAttribute("message", "Unable to remove " + member.getFullName() + ".<br>There are tasks associated to this member.");
                break;
            }
        }

        if (project.getLeader().getId().equals(userId)) {
            canRemove = false;
            model.addAttribute("message", member.getFullName() + " is the project leader and cannot be removed.");
        }

        if(canRemove) {
            project.getUsers().remove(member);
            projectsService.editProject(project);
        }

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
