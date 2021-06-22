package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.dto.MemberStats;
import com.montrealcollege.projman.dto.ProjectStats;
import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Tasks;
import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.service.ProjectsService;
import com.montrealcollege.projman.service.UsersService;
import com.montrealcollege.projman.utils.Constants;
import com.montrealcollege.projman.utils.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@SessionAttributes("currentUser")
public class MainController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProjectsService projectsService;

    private final Constants constants = new Constants();

    @GetMapping(value = "/welcome")
    public String welcomePage(Model model) {

        Users currentUser = usersService.getCurrentUser();
        if (currentUser.isLeader() || currentUser.isAdmin()) {
            int projectAmount = currentUser.getProjectsLead().size();
            List<ProjectStats> projectStats = new ArrayList<>();
            for (Projects project : currentUser.isLeader() ? currentUser.getProjectsLead() : projectsService.showProjects()) {
                List<MemberStats> memberStats = new ArrayList<>();
                for (Users member : project.getUsers()) {
                    int pendingTasksOnTime = 0;
                    int pendingTasksOverdue = 0;
                    int completedTasksOnTime = 0;
                    int completedTasksLate = 0;
                    for (Tasks memberTask : member.getTasks()) {
                        if (memberTask.getProject().getId().equals(project.getId())) {
                            if (memberTask.getState() == 4) {
                                if (!memberTask.getCompletionDate().after(memberTask.getDeadline()))
                                    completedTasksOnTime++;
                                else completedTasksLate++;
                            } else {
                                if (memberTask.getDeadline().before(DateHelper.today())) pendingTasksOverdue++;
                                else pendingTasksOnTime++;
                            }
                        }
                    }
                    memberStats.add(new MemberStats(member.getFullName(), pendingTasksOnTime, pendingTasksOverdue, completedTasksOnTime, completedTasksLate));
                }
                projectStats.add(new ProjectStats(project.getName(), memberStats));
            }
            model.addAttribute("projectsMessage", currentUser.isLeader() ? "Stats of the " + projectAmount + " projects you lead:" : "All projects stats:");
            model.addAttribute("projectStats", projectStats);
        }
        Set<Tasks> tasks = currentUser.getTasks();
        int totalTasks = tasks.size();
        int completed = 0;
        int onTime = 0;
        int overdue = 0;
        for (Tasks task : tasks) {
            if (task.getState() == 4) {
                completed ++;
                if (!task.getCompletionDate().after(task.getDeadline())) onTime++;
            } else {
                if (task.getDeadline().before(DateHelper.today())) overdue++;
            }
        }
        model.addAttribute("totalTasks", totalTasks);
        model.addAttribute("completedOnTime", onTime);
        model.addAttribute("completedLate", completed - onTime);
        model.addAttribute("pendingOnTime", totalTasks - completed - overdue);
        model.addAttribute("pendingOverdue", overdue);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("stateList", constants.stateList);
        return "users/dashboard";
    }

    @GetMapping(value = {"/", "/login"})
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping(value = "/logoutSuccessful")
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @GetMapping(value = "/403")
    public String accessDenied() {
        return "403Page";
    }
}
