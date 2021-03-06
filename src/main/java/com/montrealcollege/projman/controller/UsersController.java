package com.montrealcollege.projman.controller;

import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.service.UsersService;
import com.montrealcollege.projman.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.montrealcollege.projman.utils.EncryptedPasswordUtils.checkPassword;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    // LIST ALL
    @GetMapping("/admin/list")
    public String showAllUsers(Model model) {

        model.addAttribute("userList", usersService.showUsers());
        return "users/userList";
    }

    // PROFILE
    @GetMapping("/profile/show")
    public String showProfile() {
        return "users/profile";
    }

    @GetMapping("/profile/edit")
    public String editUser(@SessionAttribute("currentUser") Users currentUser, Model model) {

        model.addAttribute("from", "profile");
        model.addAttribute("user", usersService.getUserById(currentUser.getId()));
        return "users/editUser";
    }

    @PostMapping("/profile/validateEdit")
    public Object validateEditProfile(@SessionAttribute("currentUser") Users currentUser,
                                      @ModelAttribute("user") @Valid Users user,
                                      BindingResult errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("from", "profile");
            return "users/editUser";
        }

        try {
            usersService.editUser(user);
            if (user.getId().equals(currentUser.getId())) {
                currentUser.setUserName(user.getUserName());
                currentUser.setFirstName(user.getFirstName());
                currentUser.setLastName(user.getLastName());
                currentUser.setEmail(user.getEmail());
                currentUser.setPhone(user.getPhone());
            }
            model.addAttribute("messageColor", Constants.GREEN);
            model.addAttribute("message", user.getFullName() + Constants.EDIT_SUCCESS);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("messageColor", Constants.RED);
            model.addAttribute("message", Constants.EDIT_ERROR + user.getUserName() + Constants.BEING_USED);
        }

        return "users/profile";
    }

    // CHANGE PASSWORD
    @GetMapping("/profile/changePassword")
    public String editProfilePassword(@SessionAttribute("currentUser") Users currentUser, Model model) {

        model.addAttribute("user", usersService.getUserById(currentUser.getId()));
        return "users/changePassword";
    }

    @PostMapping("/profile/validateNewPass")
    public String validateProfilePassword(@RequestParam("currentPassword") String currentPassword,
                                          @RequestParam("newPassword") String newPassword,
                                          @RequestParam("passCheck") String passCheck,
                                          @ModelAttribute("user") Users user, Model model) {

        if (isNotValidPassword(currentPassword, newPassword, passCheck, user, model)) {
            return "users/changePassword";
        }

        usersService.editUser(user, newPassword);

        model.addAttribute("messageColor", Constants.GREEN);
        model.addAttribute("message", "Your" + Constants.CHANGE_PASSWORD_SUCCESS);
        return "users/profile";
    }

    // NEW
    @GetMapping("/admin/new")
    public String showForm(Model model) {
        Users user = new Users();
        user.setEnabled(true);
        model.addAttribute("user", user);
        return "users/newUser";
    }

    @PostMapping("/admin/validateNew")
    public String validateNewUser(@RequestParam("passCheck") String passCheck,
                                  @ModelAttribute("user") @Valid Users user,
                                  BindingResult errors, Model model) {
        boolean isNotMatch = !user.getEncryptedPassword().equals(passCheck);

        if (errors.hasErrors() || isNotMatch) {
            model.addAttribute("repeatMessage", isNotMatch ? Constants.PASSWORD_MISMATCH : "");
            return "users/newUser";
        }

        try {
            usersService.addUser(user);
            model.addAttribute("messageColor", Constants.GREEN);
            model.addAttribute("message", user.getFullName() + Constants.NEW_SUCCESS);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("messageColor", Constants.RED);
            model.addAttribute("message", Constants.ADD_ERROR + user.getUserName() + Constants.BEING_USED);
        }

        model.addAttribute("userList", usersService.showUsers());
        return "users/userList";
    }

    // EDIT
    @GetMapping("/admin/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {

        model.addAttribute("from", "admin");
        model.addAttribute("user", usersService.getUserById(id));
        return "users/editUser";
    }

    @PostMapping("/admin/validateEdit")
    public Object validateEdit(@ModelAttribute("user") @Valid Users user, BindingResult errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("from", "admin");
            return "users/editUser";
        }

        try {
            usersService.editUser(user);
            model.addAttribute("messageColor", Constants.GREEN);
            model.addAttribute("message", user.getFullName() + Constants.EDIT_SUCCESS);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("messageColor", Constants.RED);
            model.addAttribute("message", Constants.EDIT_ERROR + user.getUserName() + Constants.BEING_USED);
        }

        model.addAttribute("userList", usersService.showUsers());
        return "users/userList";
    }

    private boolean isNotValidPassword(String currentPassword, String newPassword, String passCheck, Users user, Model model) {
        boolean isNotPassword = !checkPassword(currentPassword, user.getEncryptedPassword());
        boolean isBlank = newPassword.isEmpty();
        boolean isSamePassword = !isBlank && currentPassword.equals(newPassword);
        boolean isNotMatch = !newPassword.equals(passCheck);

        if (isNotPassword || isBlank || isSamePassword || isNotMatch) {
            model.addAttribute("currentPasswordMessage", isNotPassword ? "Incorrect password." : "");
            model.addAttribute("newPasswordMessage", isBlank ? Constants.REQUIRED : isSamePassword ? "New password can't be the same as old one." : "");
            model.addAttribute("repeatMessage", isNotMatch ? Constants.PASSWORD_MISMATCH : isBlank ? Constants.REQUIRED : "");
            return true;
        }
        return false;
    }

    // DELETE
    @GetMapping("/admin/remove/{id}")
    public String removeUser(@SessionAttribute("currentUser") Users currentUser,
                             @PathVariable Long id, Model model) {
        Users user = usersService.getUserById(id);

        if (user != null) {
            if (id.equals(currentUser.getId())) {
                model.addAttribute("messageColor", Constants.RED);
                model.addAttribute("message", Constants.DELETE_ERROR + "You cannot delete yourself.");
            } else {
                try {
                    usersService.removeUser(id);
                    model.addAttribute("messageColor", Constants.GREEN);
                    model.addAttribute("message", user.getFullName() + Constants.DELETE_SUCCESS);
                } catch (DataIntegrityViolationException e) {
                    model.addAttribute("messageColor", Constants.RED);
                    model.addAttribute("message", Constants.DELETE_ERROR + user.getFullName() + " is associated to projects.");
                }
            }
        }

        model.addAttribute("userList", usersService.showUsers());
        return "users/userList";
    }

}
