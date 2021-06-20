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
    public String showAllTasks(@SessionAttribute("currentUser") Users currentUser, @RequestParam("message") String message, Model model) {

        if (!currentUser.isLeader()){
            return "403Page";
        }

        model.addAttribute("message", message);
        model.addAttribute("action", "/tasks/leader/changeState");
        model.addAttribute("taskList", tasksService.showLeaderTasks());
        addPriorityAndStateAttributes(model);
        return "tasks/leaderTaskList";
    }

    //LIST USER'S TASKS AND CHANGE TASK'S STATE
    @GetMapping("/user/list")
    public String showUserTasks(Model model) {

        model.addAttribute("action", "/tasks/user/changeState");
        model.addAttribute("taskList", tasksService.showUserTasks());
        addPriorityAndStateAttributes(model);
        return "tasks/userTaskList";
    }

    @PostMapping("/{from}/changeState")
    public Object changeState(@PathVariable("from") String from, @RequestParam("id") Long id, @RequestParam("state") int state, Model model) {
        Tasks task = tasksService.getTaskById(id);
        task.setState(state);
        task.setCompletionDate(state == 4 ? new Date() : null);

        tasksService.editTask(task);

        addPriorityAndStateAttributes(model);
        if (from.equals("user")) {
            model.addAttribute("taskList", tasksService.showUserTasks());
            return "tasks/userTaskList";
        } else {
            model.addAttribute("taskList", tasksService.showLeaderTasks());
            return "tasks/leaderTaskList";
        }
     }

    //NEW
    @GetMapping("/leader/new/{id}")
    public String showForm(@PathVariable Long id, Model model) {

        Projects project = projectsService.getProjectById(id);
        Tasks task = new Tasks();
        task.setProject(project);
        model.addAttribute("task", task);
        model.addAttribute("addOrEdit", "Add");
        model.addAttribute("action", "/tasks/leader/validateNew");
        addPriorityAndStateAttributes(model);
        return "tasks/taskForm";
    }

    @PostMapping("/leader/validateNew")
    public String validateForm(@ModelAttribute("task") @Valid Tasks task, BindingResult errors, Model model) {

        if (errors.hasErrors()) {
            System.out.println(errors);
            model.addAttribute("action", "/tasks/leader/validateNew");
            model.addAttribute("project", task.getProject());
            model.addAttribute("addOrEdit", "Add");
            addPriorityAndStateAttributes(model);
            return "tasks/taskForm";
        }
        tasksService.addTask(task);

        model.addAttribute("message", task.getName() + " was successfully added!");
        model.addAttribute("taskList", tasksService.showLeaderTasks());
        addPriorityAndStateAttributes(model);
        return "tasks/leaderTaskList";
    }

    // EDIT
    @GetMapping("/leader/edit/{id}")
    public String editProject(@PathVariable Long id, Model model) {

        model.addAttribute("task", tasksService.getTaskById(id));
        model.addAttribute("action", "/tasks/leader/validateEdit");
        model.addAttribute("addOrEdit", "Edit");
        addPriorityAndStateAttributes(model);
        return "tasks/taskForm";
    }

    @PostMapping("/leader/validateEdit")
    public Object validateEdit(@ModelAttribute("task") @Valid Tasks task, BindingResult errors, Model model) {
System.out.println("VALIDATE EDIT");
        if (errors.hasErrors()) {
            System.out.println("HAS ERRORS");
            model.addAttribute("action", "/tasks/leader/validateEdit");
            model.addAttribute("addOrEdit", "Edit");
            addPriorityAndStateAttributes(model);
            return "tasks/taskForm";
        }

        tasksService.editTask(task);

        model.addAttribute("message", task.getName() + " was successfully edited!");
        model.addAttribute("taskList", tasksService.showLeaderTasks());
        addPriorityAndStateAttributes(model);
        return "tasks/leaderTaskList";
    }

    // DELETE
    @GetMapping("/leader/remove/{id}")
    public String removeUser(@PathVariable Long id, Model model) {

        String message = tasksService.getTaskById(id).getName() + " was successfully removed!";

        tasksService.removeTask(id);

        model.addAttribute("message", message);
        model.addAttribute("taskList", tasksService.showLeaderTasks());
        addPriorityAndStateAttributes(model);
        return "tasks/leaderTaskList";
    }

    // DETAILS
    @GetMapping("/details/{id}/{from}")
    public String showTask(@PathVariable Long id, @PathVariable("from") String from, Model model) {

        Tasks task = tasksService.getTaskById(id);

        model.addAttribute("from", from);
        model.addAttribute("task", task);
        model.addAttribute("completionDate", task.getCompletionDate() == null ? "" : new SimpleDateFormat("MMM dd, yyyy").format(task.getCompletionDate()));
        addPriorityAndStateAttributes(model);
        return "tasks/taskDetails";
    }

    private void addPriorityAndStateAttributes(Model model) {
        model.addAttribute("priorityList", constants.priorityList);
        model.addAttribute("stateList", constants.stateList);
    }
}
