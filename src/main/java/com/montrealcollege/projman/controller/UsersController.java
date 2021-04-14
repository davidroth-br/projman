package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService service;

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("userList", service.showUsers());
        return "newUser";
    }

    @GetMapping("/list")
    public String showAllUsers(Model model) {
        model.addAttribute("userList", service.showUsers());
        return "allUsers";
    }

    @PostMapping("/validate")
    public String validateForm(@ModelAttribute("user") @Valid Users user,
                               BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "newUser";
        }
        service.addUser(user);
        model.addAttribute("userName", user.getUserName());
        return "result";
    }
}
