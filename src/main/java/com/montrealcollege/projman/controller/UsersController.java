package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Tasks;
import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.service.ProjectsService;
import com.montrealcollege.projman.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.montrealcollege.projman.utils.EncryptedPasswordUtils.checkPassword;
import static com.montrealcollege.projman.utils.EncryptedPasswordUtils.encryptPassword;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProjectsService projectsService;

    // LIST ALL
    @GetMapping("/admin/list")
    public String showAllUsers(@RequestParam("message") String message,
                               Model model) {

        model.addAttribute("message", message);
        model.addAttribute("userList", usersService.showUsers());
        return "users/userList";
    }

    // NEW
    @GetMapping("/admin/new")
    public String showForm(Model model) {
        model.addAttribute("user", new Users());
        return "users/newUser";
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
        usersService.addUser(user);

        String message = user.getFirstName() + " " + user.getLastName() + " was successfully added as " + user.getUserName() + "!";
        model.addAttribute("message", message);
        model.addAttribute("userList", usersService.showUsers());
        return "users/userList";
    }

    // EDIT
    @GetMapping("/admin/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", usersService.getUserById(id));
        return "users/editUser";
    }

    @PostMapping("/admin/validateEdit")
    public Object validateEdit(@ModelAttribute("user") @Valid Users user,
                               BindingResult errors, Model model) {

        if (errors.hasErrors()) {
            return "users/editUser";
        }

        usersService.editUser(user);

        String message = user.getFirstName() + " " + user.getLastName() + " was successfully edited!";
        model.addAttribute("message", message);
        return new ModelAndView("redirect:/users/admin/list", (Map<String, ?>) model);
    }

    // CHANGE PASSWORD
    @GetMapping("/admin/newPass/{id}")
    public String editPassword(@PathVariable Long id, Model model) {
        model.addAttribute("user", usersService.getUserById(id));
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

        user.setRole(usersService.getUserById(user.getId()).getRole());
        user.setEncryptedPassword(encryptPassword(newPassword));
        usersService.editUser(user);

        String message = user.getFirstName() + " " + user.getLastName() + "'s password was successfully changed!";
        model.addAttribute("message", message);
        model.addAttribute("userList", usersService.showUsers());
        return "users/userList";
    }

    // DELETE
    @GetMapping("/admin/remove/{id}")
    public String removeUser(@PathVariable Long id, Model model) {

        Users user = usersService.getUserById(id);
        String message = user.getFirstName() + " " + user.getLastName() + " was successfully removed!";

        usersService.removeUser(id);

        model.addAttribute("message", message);
        model.addAttribute("userList", usersService.showUsers());
        return "users/userList";
    }

    // DETAILS
    @GetMapping("/details/{id}")
    public String showUser(@PathVariable Long id, Model model) {

        model.addAttribute("user", usersService.getUserById(id));
        return "users/userDetails";
    }
}
