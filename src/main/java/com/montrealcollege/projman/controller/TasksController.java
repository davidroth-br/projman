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

import javax.validation.Valid;

import static com.montrealcollege.projman.utils.Helpers.today;

@Controller
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProjectsService projectsService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Projects.class, new ProjectsConverter(projectsService));
        binder.registerCustomEditor(Users.class, new UsersConverter(usersService));
    }

    //LIST USER'S TASKS
    @GetMapping("/user/list")
    public String showUserTasks(Model model) {

        model.addAttribute("action", Constants.USER_CHANGE_STATE);
        model.addAttribute("taskList", tasksService.showUserTasks());
        addPriorityAndStateAttributes(model);
        return "tasks/userTaskList";
    }

    //CHANGE TASK'S STATE
    @PostMapping("/{from}/changeState/{id}")
    public Object changeState(@SessionAttribute("currentUser") Users currentUser,
                              @PathVariable("from") String from,
                              @PathVariable("id") Long id,
                              @RequestParam("state") int state, Model model) {

        tasksService.editTask(id, state);

        model.addAttribute("messageColor", Constants.GREEN);
        model.addAttribute("message", tasksService.getTaskById(id).getName() + "'s state" + Constants.EDIT_SUCCESS);
        model.addAttribute("action", "/tasks/" + from + "/changeState" );
        addPriorityAndStateAttributes(model);
        if (from.equals("user")) {
            model.addAttribute("taskList", tasksService.showUserTasks());
            return "tasks/userTaskList";
        } else {
            model.addAttribute("projectList", projectsService.showLeaderProjects(currentUser));
            return "projects/leaderProjectList";
        }
     }

    //NEW
    @GetMapping("/leader/new/{id}")
    public String showForm(@PathVariable Long id, Model model) {

        Projects project = projectsService.getProjectById(id);
        Tasks task = new Tasks();
        task.setProject(project);
        model.addAttribute("task", task);
        model.addAttribute("addOrEdit", "New");
        model.addAttribute("action", Constants.VALIDATE_NEW_TASK);
        addPriorityAndStateAttributes(model);
        return "tasks/taskForm";
    }

    @PostMapping("/leader/validateNew")
    public String validateForm(@SessionAttribute("currentUser") Users currentUser,
                               @ModelAttribute("task") @Valid Tasks task,
                               BindingResult errors, Model model) {

        boolean isDeadlineInPast = task.getDeadline() != null && !task.getDeadline().after(today());

        if (errors.hasErrors() || isDeadlineInPast) {
            model.addAttribute("deadlineMessage", isDeadlineInPast ? Constants.IN_FUTURE : "");
            model.addAttribute("action", Constants.VALIDATE_NEW_TASK);
            model.addAttribute("project", task.getProject());
            model.addAttribute("addOrEdit", "New");
            addPriorityAndStateAttributes(model);
            return "tasks/taskForm";
        }

        tasksService.addTask(task);

        model.addAttribute("messageColor", Constants.GREEN);
        model.addAttribute("message", task.getName() + Constants.NEW_SUCCESS);
        model.addAttribute("action", Constants.LEADER_CHANGE_STATE);
        model.addAttribute("projectList", projectsService.showLeaderProjects(currentUser));
        addPriorityAndStateAttributes(model);
        return "projects/leaderProjectList";
    }

    // EDIT
    @GetMapping("/leader/edit/{id}")
    public String editProject(@PathVariable Long id, Model model) {

        model.addAttribute("task", tasksService.getTaskById(id));
        model.addAttribute("action", Constants.VALIDATE_EDIT_TASK);
        model.addAttribute("addOrEdit", "Edit");
        addPriorityAndStateAttributes(model);
        return "tasks/taskForm";
    }

    @PostMapping("/leader/validateEdit")
    public Object validateEdit(@SessionAttribute("currentUser") Users currentUser,
                               @ModelAttribute("task") @Valid Tasks task,
                               BindingResult errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("action", Constants.VALIDATE_EDIT_TASK);
            model.addAttribute("addOrEdit", "Edit");
            addPriorityAndStateAttributes(model);
            return "tasks/taskForm";
        }

        tasksService.editTask(task);

        model.addAttribute("messageColor", Constants.GREEN);
        model.addAttribute("message", task.getName() + Constants.EDIT_SUCCESS);
        model.addAttribute("action", Constants.LEADER_CHANGE_STATE);
        model.addAttribute("projectList", projectsService.showLeaderProjects(currentUser));
        addPriorityAndStateAttributes(model);
        return "projects/leaderProjectList";
    }

    // DELETE
    @GetMapping("/leader/remove/{id}")
    public String removeUser(@SessionAttribute("currentUser") Users currentUser, @PathVariable Long id, Model model) {

        String message = tasksService.getTaskById(id).getName() + Constants.DELETE_SUCCESS;

        tasksService.removeTask(id);

        model.addAttribute("messageColor", Constants.GREEN);
        model.addAttribute("message", message);
        model.addAttribute("action", Constants.LEADER_CHANGE_STATE);
        model.addAttribute("projectList", projectsService.showLeaderProjects(currentUser));
        addPriorityAndStateAttributes(model);
        return "projects/leaderProjectList";
    }

    private void addPriorityAndStateAttributes(Model model) {
        model.addAttribute("priorityList", Constants.PRIORITY_LIST);
        model.addAttribute("stateList", Constants.STATE_LIST);
    }
}
