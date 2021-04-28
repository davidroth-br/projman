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

import static com.montrealcollege.projman.utils.EncryptedPasswordUtils.checkPassword;
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

    @PostMapping("/validateNew")
    public String validateNewUser(@RequestParam("role") Long role,
                                  @RequestParam("passCheck") String passCheck,
                                  @ModelAttribute("user") @Valid Users user,
                                  BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "newUser";
        }

        if (!user.getEncryptedPassword().equals(passCheck)) {
            model.addAttribute("isNotMatch", true);
            return "newUser";
        }

        user.setEncryptedPassword(encryptPassword(user.getEncryptedPassword()));

        Roles newRole = new Roles();
        newRole.setRoleId(role);
        user.getRoles().add(newRole);

        service.addUser(user);
        model.addAttribute("newUser", user.getFirstName() + " " + user.getLastName());
        model.addAttribute("newUserName", user.getUserName());
        model.addAttribute("userList", service.showUsers());
        return "userList";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id,
                           @ModelAttribute("user") Users user, Model model) {

        Users usr = service.getUserById(id);
        model.addAttribute("user", usr);

        setRoleAttributes(usr.getRoles().iterator().next().getRoleId(), model);

        if (usr.isEnabled()) {
            model.addAttribute("chk", "checked");
        }

        return "editUser";
    }

    @PostMapping("/validateEdit/{id}")
    public String validateEdit(@PathVariable Long id,
                               @RequestParam("role") Long role,
                               @RequestParam("currentPassword") String currentPassword,
                               //@RequestParam("passCheck") String passCheck,
                               @ModelAttribute("user") @Valid Users user,
                               BindingResult errors, Model model) {

        if (errors.hasErrors()) {
            setRoleAttributes(role, model);
            return "editUser";
        }

        if (!currentPassword.equals("") && !checkPassword(currentPassword, user.getEncryptedPassword())) {
            setRoleAttributes(role, model);
            model.addAttribute("isNotPassword", true);
            return "editUser";
        }
//        if (!user.getEncryptedPassword().equals(passCheck)) {
//            model.addAttribute("isNotMatch", true);
//            return "editUser";
//        }

        user.setId(id);

        Roles newRole = new Roles();
        newRole.setRoleId(role);
        user.getRoles().clear();
        user.getRoles().add(newRole);

        service.editUser(user);

        model.addAttribute("userList", service.showUsers());
        return "userList";
    }

    private void setRoleAttributes(@RequestParam("role") Long role, Model model) {
        if (role == 1) {
            model.addAttribute("roleAdm", "checked");
            model.addAttribute("roleUser", "");
        } else {
            model.addAttribute("roleAdm", "");
            model.addAttribute("roleUser", "checked");
        }
    }
}
