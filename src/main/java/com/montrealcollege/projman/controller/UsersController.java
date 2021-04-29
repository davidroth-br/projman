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

        String message = user.getFirstName() + " " + user.getLastName() +
                " was successfully added as " + user.getUserName() + "!";
        model.addAttribute("message", message);
        model.addAttribute("userList", service.showUsers());
        return "userList";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {

        Users user = service.getUserById(id);
        model.addAttribute("user", user);
        setRoleAttributes(user.getRoles().iterator().next().getRoleId(), model);

        return "editUser";
    }

    @PostMapping("/validateEdit")
    public String validateEdit(@RequestParam("role") Long role,
                               @ModelAttribute("user") @Valid Users user,
                               BindingResult errors, Model model) {

        if (errors.hasErrors()) {
            setRoleAttributes(role, model);
            return "editUser";
        }

        Roles newRole = new Roles();
        newRole.setRoleId(role);
        user.getRoles().clear();
        user.getRoles().add(newRole);

        service.editUser(user);

        String message = user.getFirstName() + " " + user.getLastName() +
                " was successfully edited!";
        model.addAttribute("message", message);
        model.addAttribute("userList", service.showUsers());
        return "userList";
    }

    private void setRoleAttributes(Long role, Model model) {
        if (role == 1) {
            model.addAttribute("roleAdm", "checked");
            model.addAttribute("roleUser", "");
        } else {
            model.addAttribute("roleAdm", "");
            model.addAttribute("roleUser", "checked");
        }
    }

    @GetMapping("/newPass/{id}")
    public String editPassword(@PathVariable Long id, Model model) {

        Users user = service.getUserById(id);
        model.addAttribute("user", user);
        setRoleAttributes(user.getRoles().iterator().next().getRoleId(), model);

        return "changePassword";
    }

    @PostMapping("/validateNewPass")
    public String validatePassword(@RequestParam("currentPassword") String currentPassword,
                                   @RequestParam("newPassword") String newPassword,
                                   @RequestParam("passCheck") String passCheck,
                                   @ModelAttribute("user") Users user, Model model) {

        if (!currentPassword.equals("") && !checkPassword(currentPassword, user.getEncryptedPassword())) {
            model.addAttribute("isNotPassword", true);
            return "changePassword";
        }
        if (!newPassword.equals(passCheck)) {
            model.addAttribute("isNotMatch", true);
            return "changePassword";
        }
        Users usr = service.getUserById(user.getId());
        user.setRoles(usr.getRoles());
        user.setEncryptedPassword(encryptPassword(newPassword));

        service.editUser(user);

        String message = user.getFirstName() + " " + user.getLastName() +
                "'s password was successfully changed!";
        model.addAttribute("message", message);
        model.addAttribute("userList", service.showUsers());
        return "userList";
    }

    @GetMapping("/remove/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {

        Users user = service.getUserById(id);
        service.removeUser(id);
        String message = user.getFirstName() + " " + user.getLastName() +
                " was successfully removed!";
        model.addAttribute("message", message);
        model.addAttribute("userList", service.showUsers());
        return "userList";
    }
}
