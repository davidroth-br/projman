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
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public String showAllTasks(@RequestParam("message") String message, Model model) {

        model.addAttribute("message", message);
        setModelAttributes(model, "priorityList", "stateList", "taskList");
        return "tasks/taskList";
    }

    //LIST USER'S TASKS AND CHANGE TASK'S STATE
    @GetMapping("/user/list")
    public String showUserTasks(Model model) {

        model.addAttribute("action", "/tasks/user/changeState");
        setModelAttributes(model, "priorityList", "stateList", "userTaskList");
        return "tasks/userTaskList";
    }

    @PostMapping("/user/changeState")
    public Object changeState(@RequestParam("id") Long id, @RequestParam("state") int state) {
        Tasks task = tasksService.getTaskById(id);
        task.setState(state);
        task.setCompletionDate(state == 4 ? new Date() : null);

        tasksService.editTask(task);

        return new ModelAndView("redirect:/tasks/user/list");
    }
    //NEW
    @GetMapping("/admin/new")
    public String showForm(Model model) {

        model.addAttribute("task", new Tasks());
        setModelAttributes(model, "priorityList", "stateList", "projectList", "userList", "Add");
        model.addAttribute("action", "/tasks/admin/validateNew");
        return "tasks/taskForm";
    }

    @PostMapping("/admin/validateNew")
    public String validateForm(@ModelAttribute("task") @Valid Tasks task, BindingResult errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("action", "/tasks/admin/validateNew");
            setModelAttributes(model, "priorityList", "stateList", "projectList", "userList", "Add");
            return "tasks/taskForm";
        }

        tasksService.addTask(task);

        model.addAttribute("message", task.getName() + " was successfully added!");
        setModelAttributes(model, "priorityList", "stateList", "taskList");
        return "tasks/taskList";
    }

    // EDIT
    @GetMapping("/admin/edit/{id}")
    public String editProject(@PathVariable Long id, Model model) {

        model.addAttribute("task", tasksService.getTaskById(id));
        model.addAttribute("action", "/tasks/admin/validateEdit");
        setModelAttributes(model, "priorityList", "stateList", "projectList", "userList", "Edit");
        return "tasks/taskForm";
    }

    @PostMapping("/admin/validateEdit")
    public Object validateEdit(@ModelAttribute("task") @Valid Tasks task, BindingResult errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("action", "/tasks/admin/validateEdit");
            setModelAttributes(model, "priorityList", "stateList", "projectList", "userList", "Edit");
            return "tasks/taskForm";
        }

        tasksService.editTask(task);

        model.addAttribute("message", task.getName() + " was successfully edited!");
        return new ModelAndView("redirect:/tasks/admin/list", (Map<String, ?>) model);
    }

    // DELETE
    @GetMapping("/admin/remove/{id}")
    public String removeUser(@PathVariable Long id, Model model) {

        String message = tasksService.getTaskById(id).getName() + " was successfully removed!";

        tasksService.removeTask(id);

        model.addAttribute("message", message);
        setModelAttributes(model, "priorityList", "stateList", "taskList");
        return "tasks/taskList";
    }

    // DETAILS
    @GetMapping("/details/{id}/{from}")
    public String showTask(@PathVariable Long id, @PathVariable("from") String from, Model model) {

        Tasks task = tasksService.getTaskById(id);

        model.addAttribute("from", from);
        model.addAttribute("task", task);
        model.addAttribute("completionDate", task.getCompletionDate() == null ? "" : new SimpleDateFormat("MMM dd, yyyy").format(task.getCompletionDate()));
        setModelAttributes(model, "priorityList", "stateList");
        return "tasks/taskDetails";
    }

    private void setModelAttributes(Model model, String... attributes) {
        for (String attribute : attributes) {
            switch (attribute) {
                case "priorityList":
                    model.addAttribute("priorityList", constants.priorityList);
                    break;
                case "stateList":
                    model.addAttribute("stateList", constants.stateList);
                    break;
                case "projectList":
                    model.addAttribute("projectList", projectsService.showProjects());
                    break;
                case "userList":
                    model.addAttribute("userList", usersService.showUsers());
                    break;
                case "taskList":
                    model.addAttribute("taskList", tasksService.showTasks());
                    break;
                case "userTaskList":
                    model.addAttribute("taskList", tasksService.showUserTasks());
                    break;
                case "Add":
                    model.addAttribute("addOrEdit", "Add");
                    break;
                case "Edit":
                    model.addAttribute("addOrEdit", "Edit");
                    break;
            }
        }
    }
}
