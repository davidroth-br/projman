package com.montrealcollege.projman.controller;

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

    @GetMapping("/admin/new")
    public String showForm(Model model) {
        model.addAttribute("user", new Users());
        return "users/newUser";
    }

    @GetMapping("/admin/list")
    public String showAllUsers(Model model) {
        model.addAttribute("userList", service.showUsers());
        return "users/userList";
    }

    @PostMapping("/admin/validateNew")
    public String validateNewUser(@RequestParam("passCheck") String passCheck,
                                  @ModelAttribute("user") @Valid Users user,
                                  BindingResult errors, Model model) {
        if (errors.hasErrors())
            return "users/newUser";

        if (!user.getEncryptedPassword().equals(passCheck)) {
            model.addAttribute("isNotMatch", true);
            return "users/newUser";
        }

        user.setEncryptedPassword(encryptPassword(user.getEncryptedPassword()));
        service.addUser(user);

        String message = user.getFirstName() + " " + user.getLastName() + " was successfully added as " + user.getUserName() + "!";
        model.addAttribute("message", message);
        model.addAttribute("userList", service.showUsers());
        return "users/userList";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", service.getUserById(id));
        return "users/editUser";
    }

    @PostMapping("/admin/validateEdit")
    public String validateEdit(@ModelAttribute("user") @Valid Users user,
                               BindingResult errors, Model model) {

        if (errors.hasErrors()) {
            return "users/editUser";
        }

        service.editUser(user);

        String message = user.getFirstName() + " " + user.getLastName() + " was successfully edited!";
        model.addAttribute("message", message);
        model.addAttribute("userList", service.showUsers());
        return "users/userList";
    }

    @GetMapping("/admin/newPass/{id}")
    public String editPassword(@PathVariable Long id, Model model) {
        model.addAttribute("user", service.getUserById(id));
        return "users/changePassword";
    }

    @PostMapping("/admin/validateNewPass")
    public String validatePassword(@RequestParam("currentPassword") String currentPassword,
                                   @RequestParam("newPassword") String newPassword,
                                   @RequestParam("passCheck") String passCheck,
                                   @ModelAttribute("user") Users user, Model model) {

        if (!currentPassword.equals("") && !checkPassword(currentPassword, user.getEncryptedPassword())) {
            model.addAttribute("isNotPassword", true);
            return "users/changePassword";
        }
        if (!newPassword.equals(passCheck)) {
            model.addAttribute("isNotMatch", true);
            return "users/changePassword";
        }

        user.setRole(service.getUserById(user.getId()).getRole());
        user.setEncryptedPassword(encryptPassword(newPassword));
        service.editUser(user);

        String message = user.getFirstName() + " " + user.getLastName() + "'s password was successfully changed!";
        model.addAttribute("message", message);
        model.addAttribute("userList", service.showUsers());
        return "users/userList";
    }

    @GetMapping("/admin/remove/{id}")
    public String removeUser(@PathVariable Long id, Model model) {

        Users user = service.getUserById(id);
        String message = user.getFirstName() + " " + user.getLastName() + " was successfully removed!";

        service.removeUser(id);

        model.addAttribute("message", message);
        model.addAttribute("userList", service.showUsers());
        return "users/userList";
    }
}
