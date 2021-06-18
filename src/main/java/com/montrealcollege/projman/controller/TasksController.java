package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Tasks;
import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.service.ProjectsService;
import com.montrealcollege.projman.service.TasksService;
import com.montrealcollege.projman.service.UsersService;
import com.montrealcollege.projman.utils.Constants;
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

    //LIST ALL TASKS IN ALL PROJECTS LEAD BY CURRENT USER
    @GetMapping("/leader/list")
    public String showAllTasks(@RequestParam("message") String message, Model model) {

        Users currentUser = usersService.getCurrentUser();
        if (!usersService.isLeader(currentUser)){
            model.addAttribute("userFullName", currentUser.getFullName());
            return "403Page";
        }

        model.addAttribute("message", message);
        setModelAttributes(model, currentUser, "priorityList", "stateList", "leaderTaskList");
        return "tasks/leaderTaskList";
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
    @GetMapping("/leader/new")
    public String showForm(Model model) {

        model.addAttribute("task", new Tasks());
        setModelAttributes(model, usersService.getCurrentUser(), "priorityList", "stateList", "projectList", "userList", "Add");
        model.addAttribute("action", "/tasks/leader/validateNew");
        return "tasks/taskForm";
    }

    @PostMapping("/leader/validateNew")
    public String validateForm(@ModelAttribute("task") @Valid Tasks task, BindingResult errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("action", "/tasks/leader/validateNew");
            setModelAttributes(model, usersService.getCurrentUser(), "priorityList", "stateList", "projectList", "userList", "Add");
            return "tasks/taskForm";
        }

        tasksService.addTask(task);

        model.addAttribute("message", task.getName() + " was successfully added!");
        setModelAttributes(model, "priorityList", "stateList", "taskList");
        return "tasks/leaderTaskList";
    }

    // EDIT
    @GetMapping("/leader/edit/{id}")
    public String editProject(@PathVariable Long id, Model model) {

        model.addAttribute("task", tasksService.getTaskById(id));
        model.addAttribute("action", "/tasks/leader/validateEdit");
        setModelAttributes(model, usersService.getCurrentUser(), "priorityList", "stateList", "projectList", "userList", "Edit");
        return "tasks/taskForm";
    }

    @PostMapping("/leader/validateEdit")
    public Object validateEdit(@ModelAttribute("task") @Valid Tasks task, BindingResult errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("action", "/tasks/leader/validateEdit");
            setModelAttributes(model, usersService.getCurrentUser(), "priorityList", "stateList", "projectList", "userList", "Edit");
            return "tasks/taskForm";
        }

        tasksService.editTask(task);

        model.addAttribute("message", task.getName() + " was successfully edited!");
        return new ModelAndView("redirect:/tasks/leader/list", (Map<String, ?>) model);
    }

    // DELETE
    @GetMapping("/leader/remove/{id}")
    public String removeUser(@PathVariable Long id, Model model) {

        String message = tasksService.getTaskById(id).getName() + " was successfully removed!";

        tasksService.removeTask(id);

        model.addAttribute("message", message);
        setModelAttributes(model, "priorityList", "stateList", "taskList");
        return "leaderTaskList";
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
        setModelAttributes(model, null, attributes);
    }

    private void setModelAttributes(Model model, Users user, String... attributes) {
        for (String attribute : attributes) {
            switch (attribute) {
                case "priorityList":
                    model.addAttribute("priorityList", constants.priorityList);
                    break;
                case "stateList":
                    model.addAttribute("stateList", constants.stateList);
                    break;
                case "projectList":
                    model.addAttribute("projectList", projectsService.showLeaderProjects(user));
                    break;
                case "userList":
                    model.addAttribute("userList", usersService.showUsers());
                    break;
                case "leaderTaskList":
                    model.addAttribute("taskList", tasksService.showLeaderTasks());
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
