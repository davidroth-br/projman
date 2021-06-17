package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.dto.MemberStats;
import com.montrealcollege.projman.dto.ProjectStats;
import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Tasks;
import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.service.UsersService;
import com.montrealcollege.projman.utils.Constants;
import com.montrealcollege.projman.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    private UsersService usersService;

    private final Constants constants = new Constants();

    @GetMapping(value = "/welcome")
    public String welcomePage(Model model, Principal principal) {

        Users user = usersService.getCurrentUser();
        if (isLeader(user)) {
            int projectAmount = user.getProjectsLead().size();
            List<ProjectStats> projectStats = new ArrayList<>();
            for (Projects project : user.getProjectsLead()) {
                List<MemberStats> memberStats = new ArrayList<>();
                for (Users member : project.getUsers()) {
                    int pendingTasksOnTime = 0;
                    int pendingTasksOverdue = 0;
                    int completedTasksOnTime = 0;
                    int completedTasksLate = 0;
                    for (Tasks memberTask : member.getTasks()) {
                        if (memberTask.getProject().getId().equals(project.getId())) {
                            if (memberTask.getState() == 4) {
                                if (memberTask.getCompletionDate().compareTo(memberTask.getDeadline()) <= 0)
                                    completedTasksOnTime++;
                                else completedTasksLate++;
                            } else {
                                if (memberTask.getDeadline().compareTo(new Date()) < 0) pendingTasksOverdue++;
                                else pendingTasksOnTime++;
                            }
                        }
                    }
                    memberStats.add(new MemberStats(member.getFullName(), pendingTasksOnTime, pendingTasksOverdue, completedTasksOnTime, completedTasksLate));
                }
                projectStats.add(new ProjectStats(project.getName(), memberStats));
            }
            model.addAttribute("projectAmount", projectAmount);
            model.addAttribute("projectStats", projectStats);
        }
        Set<Tasks> tasks = user.getTasks();
        int totalTasks = tasks.size();
        int completed = 0;
        int onTime = 0;
        int overdue = 0;
        for (Tasks task : tasks) {
            if (task.getState() == 4) {
                completed ++;
                if (task.getCompletionDate().compareTo(task.getDeadline()) <= 0) onTime++;
            }
            if (task.getDeadline().compareTo(new Date()) < 0) overdue++;
        }
        model.addAttribute("user", user);
        model.addAttribute("totalTasks", totalTasks);
        model.addAttribute("completedOnTime", onTime);
        model.addAttribute("completedLate", completed - onTime);
        model.addAttribute("pendingOnTime", totalTasks - completed - overdue);
        model.addAttribute("pendingOverdue", overdue);
        model.addAttribute("stateList", constants.stateList);
        return user.getRole().getRoleId() == 2 ? "users/userDashboard" : "users/adminDashboard";
    }

    private boolean isLeader(Users user) {
        return user.getProjectsLead().size() != 0;
    }

    @GetMapping(value = {"/", "/login"})
    public String loginPage(Model model) {
        return "loginPage";
    }

    @GetMapping(value = "/logoutSuccessful")
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @GetMapping(value = "/403")
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            model.addAttribute("title", "Access Denied");

            User loggedInUser = (User) ((Authentication) principal).getPrincipal();
            String userInfo = WebUtils.toString(loggedInUser);
            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
        }
        return "403Page";
    }
}
