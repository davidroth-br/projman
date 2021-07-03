package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Tasks;
import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.service.ProjectsService;
import com.montrealcollege.projman.service.UsersService;
import com.montrealcollege.projman.utils.Constants;
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

    //LIST ALL TASKS IN ALL PROJECTS LEAD BY CURRENT USER (LEADER)
    @GetMapping("/leader/list")
    public String showAllTasks(@SessionAttribute("currentUser") Users currentUser, Model model) {

        if (!currentUser.isLeader()){
            return "403Page";
        }

        model.addAttribute("action", Constants.LEADER_CHANGE_STATE);
        model.addAttribute("projectList", projectsService.showLeaderProjects(currentUser));
        model.addAttribute("priorityList", Constants.PRIORITY_LIST);
        model.addAttribute("stateList", Constants.STATE_LIST);
        return "projects/leaderProjectList";
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

        boolean isEndDateBeforeStartDate = checkEndBeforeStart(project);
        boolean isStartDateInPast = checkStartInPast(project, true);
        boolean isEndDateInPast = checkEndInPast(project, true);

        if (errors.hasErrors() || isEndDateBeforeStartDate || isEndDateInPast || isStartDateInPast) {
            model.addAttribute("userList", usersService.showUsers());
            model.addAttribute("startDateMessage", isStartDateInPast ? Constants.START_IN_PAST : "");
            model.addAttribute("endDateMessage", isEndDateBeforeStartDate ? Constants.END_BEFORE_START : isEndDateInPast ? Constants.END_IN_PAST : "");
            model.addAttribute("addOrEdit", "New");
            model.addAttribute("action", "/projects/admin/validateNew");
            return "projects/projectForm";
        }

        projectsService.addProject(project);

        model.addAttribute("message", project.getName() + Constants.NEW_SUCCESS);
        model.addAttribute("messageColor", Constants.GREEN);
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

        boolean isEndDateBeforeStartDate = checkEndBeforeStart(project);
        boolean isStartDateInPast = checkStartInPast(project, false);
        boolean isEndDateInPast = checkEndInPast(project, false);

        if (errors.hasErrors() || isEndDateBeforeStartDate || isEndDateInPast || isStartDateInPast) {
            model.addAttribute("userList", usersService.showUsers());
            model.addAttribute("startDateMessage", isStartDateInPast ? Constants.START_IN_PAST : "");
            model.addAttribute("endDateMessage", isEndDateBeforeStartDate ? Constants.END_BEFORE_START : isEndDateInPast ? Constants.END_IN_PAST : "");
            model.addAttribute("addOrEdit", "Edit");
            model.addAttribute("action", "/projects/admin/validateEdit");
            return "projects/projectForm";
        }

        project.setUsers(projectsService.getProjectById(project.getId()).getUsers());
        project.getUsers().add(project.getLeader());

        projectsService.editProject(project);

        model.addAttribute("message", project.getName() + Constants.EDIT_SUCCESS);
        model.addAttribute("messageColor", Constants.GREEN);
        model.addAttribute("projectList", projectsService.showProjects());
        return "projects/projectList";
    }

    private boolean checkEndInPast(Projects project, boolean isNew) {
        return isNew ? project.getEndDate() != null && !project.getEndDate().after(today()) : project.getEndDate() != null &&
                !project.getEndDate().equals(projectsService.getProjectById(project.getId()).getEndDate()) &&
                !project.getEndDate().after(today());
    }

    private boolean checkStartInPast(Projects project, boolean isNew) {
        return isNew ? project.getStartDate() != null && project.getStartDate().before(today()) : project.getStartDate() != null &&
                !project.getStartDate().equals(projectsService.getProjectById(project.getId()).getStartDate()) &&
                project.getStartDate().before(today());
    }

    private boolean checkEndBeforeStart(Projects project) {
        return project.getEndDate() != null &&
                project.getStartDate() != null &&
                !project.getEndDate().after(project.getStartDate());
    }

    // DELETE
    @GetMapping("/admin/remove/{id}")
    public String removeUser(@PathVariable Long id, Model model) {

        String projectName = projectsService.getProjectById(id).getName();
        String message = projectName + Constants.DELETE_SUCCESS;

        try {
            projectsService.removeProject(id);
            model.addAttribute("messageColor", Constants.GREEN);
        } catch (DataIntegrityViolationException e) {
            message =  "Unable to delete" + projectName + ".<br>There are tasks associated to it.";
            model.addAttribute("messageColor", Constants.RED);
        }

        model.addAttribute("message", message);
        model.addAttribute("projectList", projectsService.showProjects());
        return "projects/projectList";
    }

    // MANAGE PROJECT MEMBERS
    @GetMapping("/leader/manageMembers/{from}/{id}")
    public String manageMembers(@PathVariable String from, @PathVariable Long id, Model model) {

        Projects project = projectsService.getProjectById(id);

        model.addAttribute("from", from);
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

        model.addAttribute("messageColor", Constants.RED);
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
