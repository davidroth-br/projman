package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.model.Tasks;
import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.service.UsersService;
import com.montrealcollege.projman.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    private UsersService usersService;

    @GetMapping(value = "/welcome")
    public String welcomePage(Model model, Principal principal) {

        Users user = usersService.getCurrentUser();
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
        model.addAttribute("tasks", totalTasks);
        model.addAttribute("completed", completed);
        model.addAttribute("onTime", onTime);
        model.addAttribute("late", completed - onTime);
        model.addAttribute("pending", totalTasks - completed);
        model.addAttribute("overdue", overdue);
        return user.getRole().getRoleId() == 2 ? "users/userDashboard" : "users/adminDashboard";
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
