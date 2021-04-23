package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Collection;

@Controller
public class MainController {

    @GetMapping(value = "/welcome")
    public String welcomePage(Model model, Principal principal) {

        // After user login successfully.

//        Collection<GrantedAuthority> grantList = loggedInUser.getAuthorities();
//        String authority;
//
//        if (grantList.toString().contains("ROLE_ADMIN")) authority = "ROLE_ADMIN";
//        else authority = "ROLE_USER";
//
//        model.addAttribute("authority", authority);

        User loggedInUser = (User) ((Authentication) principal).getPrincipal();
        model.addAttribute("userName", loggedInUser.getUsername());
        model.addAttribute("title", "Welcome");
        return "welcomePage";
    }

    @GetMapping(value = "/admin")
    public String adminPage(Model model, Principal principal) {
        model.addAttribute("title", "Admin Info");

        User loggedInUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loggedInUser);
        model.addAttribute("userInfo", userInfo);

        return "adminPage";
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

    @GetMapping(value = "/userInfo")
    public String userInfo(Model model, Principal principal) {

        model.addAttribute("title", "User Info");

        String userName = principal.getName();
        System.out.println("User Name: " + userName);

        User loggedInUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loggedInUser);
        model.addAttribute("userInfo", userInfo);

        return "userInfoPage";
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
