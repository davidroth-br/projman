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
        return "newUser";
    }

    @GetMapping("/list")
    public String showAllUsers(Model model) {
        model.addAttribute("userList", service.showUsers());
        return "userList";
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

        Roles roles = new Roles();
        roles.setRoleId(role);

        service.addUser(user, role);
//        service.addUser(user);
        model.addAttribute("newUser", user.getFirstName() + " "+ user.getLastName());
        model.addAttribute("newUserName", user.getUserName());
        model.addAttribute("userList", service.showUsers());
        return "userList";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id,
                           @ModelAttribute("user") Users user, Model model) {
        model.addAttribute("user", service.getUserById(id));

        return "editUser";
    }

}
