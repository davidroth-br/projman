package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.dto.MemberStats;
import com.montrealcollege.projman.dto.ProjectStats;
import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.service.ProjectsService;
import com.montrealcollege.projman.service.UsersService;
import com.montrealcollege.projman.utils.Constants;
import com.montrealcollege.projman.utils.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes("currentUser")
public class MainController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProjectsService projectsService;

    @GetMapping(value = "/dashboard")
    public String welcomePage(Model model) {
        Users currentUser = usersService.getCurrentUser();
        List<ProjectStats> projectStats = Helpers.getAllStats(currentUser, currentUser.isAdmin() ? projectsService.showProjects() : currentUser.isLeader() ? currentUser.getProjectsLead() : null);
        MemberStats currentUserStats = projectStats.get(projectStats.size() - 1).getMemberStats().get(0);

        model.addAttribute("projectsMessage", currentUser.isAdmin() ? "All Projects" : "Projects You Lead");
        model.addAttribute("projectStats", projectStats);
        model.addAttribute("totalTasks", currentUserStats.getCompletedTasksOnTime() + currentUserStats.getCompletedTasksLate() + currentUserStats.getPendingTasksOnTime() + currentUserStats.getPendingTasksOverdue());
        model.addAttribute("completedOnTime", currentUserStats.getCompletedTasksOnTime());
        model.addAttribute("completedLate", currentUserStats.getCompletedTasksLate());
        model.addAttribute("pendingOnTime", currentUserStats.getPendingTasksOnTime());
        model.addAttribute("pendingOverdue", currentUserStats.getPendingTasksOverdue());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("stateList", Constants.STATE_LIST);
        return "users/dashboard";
    }

    @GetMapping(value = {"/", "/login"})
    public String loginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginPage";
        }

        return "redirect:/dashboard";
    }

    @GetMapping(value = "/logoutSuccessful")
    public String logoutSuccessfulPage() {
        return "loginPage";
    }

    @GetMapping(value = "/403")
    public String accessDenied() {
        return "403Page";
    }
}
