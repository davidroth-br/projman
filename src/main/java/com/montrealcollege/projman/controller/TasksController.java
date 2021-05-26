package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Tasks;
import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.utils.Constants;
import com.montrealcollege.projman.service.ProjectsService;
import com.montrealcollege.projman.service.TasksService;
import com.montrealcollege.projman.service.UsersService;
import com.montrealcollege.projman.utils.ProjectsConverter;
import com.montrealcollege.projman.utils.UsersConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProjectsService projectsService;

    private final Constants constants = new Constants();

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Projects.class, new ProjectsConverter(projectsService));
        binder.registerCustomEditor(Users.class, new UsersConverter(usersService));
    }

    //LIST ALL
    @GetMapping("/list")
    public String showAllTasks(@RequestParam("message") String message,
                                  Model model) {

        model.addAttribute("message", message);
        model.addAttribute("priorityList", constants.priorityList);
        model.addAttribute("stateList", constants.stateList);
        model.addAttribute("taskList", tasksService.showTasks());
        return "tasks/taskList";
    }

    //NEW
    @GetMapping("/new")
    public String showForm(Model model) {

        model.addAttribute("task", new Tasks());
        model.addAttribute("priorityList", constants.priorityList);
        model.addAttribute("stateList", constants.stateList);
        model.addAttribute("projectId", 0);
        model.addAttribute("projectList", projectsService.showProjects());
        model.addAttribute("selectedUsers", null);
        model.addAttribute("userList", usersService.showUsers());

        return "tasks/newTask";
    }

    @PostMapping("/validateNew")
    public String validateForm(@ModelAttribute("task") @Valid Tasks task,
                               BindingResult errors, Model model) {

        Long projectId = task.getProject() == null ? 0L : task.getProject().getId();
        Set<Users> selectedUsers = task.getUsers().isEmpty() ? null : task.getUsers();

        if (errors.hasErrors()) {
            System.out.println("ATTENTION: " + errors);
            model.addAttribute("priorityList", constants.priorityList);
            model.addAttribute("stateList", constants.stateList);
            model.addAttribute("projectId", projectId);
            model.addAttribute("projectList", projectsService.showProjects());
            model.addAttribute("selectedUsers", selectedUsers);
            model.addAttribute("userList", usersService.showUsers());
            return "tasks/newTask";
        }

        String message = task.getName() + " was successfully added!";
        tasksService.addTask(task);

        model.addAttribute("message", message);
        model.addAttribute("taskList", tasksService.showTasks());

        return "tasks/taskList";
    }

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
