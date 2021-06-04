package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {

    @GetMapping(value = "/welcome")
    public String welcomePage(Model model, Principal principal) {

        // After user login successfully.
        User loggedInUser = (User) ((Authentication) principal).getPrincipal();
        model.addAttribute("userName", loggedInUser.getUsername());
        model.addAttribute("title", "Welcome");
        return "welcomePage";
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
