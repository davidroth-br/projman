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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

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
    @GetMapping("/admin/list")
    public String showAllTasks(@RequestParam("message") String message,
                                  Model model) {

        model.addAttribute("message", message);
        model.addAttribute("priorityList", constants.priorityList);
        model.addAttribute("stateList", constants.stateList);
        model.addAttribute("taskList", tasksService.showTasks());
        return "tasks/taskList";
    }

    //NEW
    @GetMapping("/admin/new")
    public String showForm(Model model) {

        model.addAttribute("task", new Tasks());
        model.addAttribute("priorityList", constants.priorityList);
        model.addAttribute("stateList", constants.stateList);
        model.addAttribute("projectList", projectsService.showProjects());
        model.addAttribute("userList", usersService.showUsers());
        model.addAttribute("action", "/tasks/validateNew");
        return "tasks/taskForm";
    }

    @PostMapping("/admin/validateNew")
    public String validateForm(@ModelAttribute("task") @Valid Tasks task,
                               BindingResult errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("priorityList", constants.priorityList);
            model.addAttribute("stateList", constants.stateList);
            model.addAttribute("projectList", projectsService.showProjects());
            model.addAttribute("userList", usersService.showUsers());
            model.addAttribute("action", "/tasks/validateNew");
            return "tasks/taskForm";
        }

        String message = task.getName() + " was successfully added!";
        tasksService.addTask(task);

        model.addAttribute("message", message);
        model.addAttribute("priorityList", constants.priorityList);
        model.addAttribute("stateList", constants.stateList);
        model.addAttribute("taskList", tasksService.showTasks());
        return "tasks/taskList";
    }

    // EDIT
    @GetMapping("/admin/edit/{id}")
    public String editProject(@PathVariable Long id, Model model) {
        Tasks task = tasksService.getTaskById(id);

        model.addAttribute("task", task);
        model.addAttribute("priorityList", constants.priorityList);
        model.addAttribute("stateList", constants.stateList);
        model.addAttribute("projectList", projectsService.showProjects());
        model.addAttribute("userList", usersService.showUsers());
        model.addAttribute("action", "/tasks/validateEdit");
        return "tasks/taskForm";
    }

    @PostMapping("/admin/validateEdit")
    public Object validateEdit(@ModelAttribute("task") @Valid Tasks task,
                               BindingResult errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("priorityList", constants.priorityList);
            model.addAttribute("stateList", constants.stateList);
            model.addAttribute("projectList", projectsService.showProjects());
            model.addAttribute("userList", usersService.showUsers());
            model.addAttribute("action", "/tasks/validateEdit");
            return "tasks/taskForm";
        }

        tasksService.editTask(task);

        String message = task.getName() + " was successfully edited!";
        model.addAttribute("message", message);
        return new ModelAndView("redirect:/tasks/list", (Map<String, ?>) model);
    }

    // DELETE
    @GetMapping("/admin/remove/{id}")
    public String removeUser(@PathVariable Long id, Model model) {

        Tasks task = tasksService.getTaskById(id);
        String message = task.getName() + " was successfully removed!";

        tasksService.removeTask(id);

        model.addAttribute("message", message);
        model.addAttribute("priorityList", constants.priorityList);
        model.addAttribute("stateList", constants.stateList);
        model.addAttribute("taskList", tasksService.showTasks());
        return "tasks/taskList";
    }

    // DETAILS
    @GetMapping("/admin/details/{id}")
    public String showTask(@PathVariable Long id, Model model) {
        Tasks task = tasksService.getTaskById(id);

        model.addAttribute("task", task);
        model.addAttribute("priorityList", constants.priorityList);
        model.addAttribute("stateList", constants.stateList);
        return "tasks/taskDetails";
    }
}
