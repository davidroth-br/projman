package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.model.Roles;
import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.montrealcollege.projman.utils.EncryptedPasswordUtils.encryptPassword;

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
    public String validateForm(@RequestParam("role") Long role,
                               @RequestParam(value = "enabled", required = false) String enabled,
                               @ModelAttribute("user") @Valid Users user,
                               BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "newUser";
        }

        user.setEncryptedPassword(encryptPassword(user.getEncryptedPassword()));
        user.setEnabled(!(enabled == null));

        service.addUser(user, role);
        model.addAttribute("userName", user.getUserName());
        return "result";
    }
}
