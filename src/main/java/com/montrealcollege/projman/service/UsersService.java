package com.montrealcollege.projman.service;

import com.montrealcollege.projman.dao.UsersDAO;
import com.montrealcollege.projman.model.Users;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.montrealcollege.projman.utils.EncryptedPasswordUtils.encryptPassword;

@Service
public class UsersService {

    @Autowired
    UsersDAO usersDAO;

    public void addUser(Users user) {
        user.setFirstName(WordUtils.capitalizeFully(user.getFirstName()));
        user.setLastName(WordUtils.capitalizeFully(user.getLastName()));
        user.setEncryptedPassword(encryptPassword(user.getEncryptedPassword()));
        usersDAO.createUser(user);
    }

    public List<Users> showUsers() {
        return usersDAO.listUsers();
    }

    public Users getUserById(Long id) {
        return usersDAO.findUserById(id);
    }

    public void editUser(Users user) {
        editUser(user, "");
    }

    public void editUser(Users user, String newPassword) {
        user.setFirstName(WordUtils.capitalizeFully(user.getFirstName()));
        user.setLastName(WordUtils.capitalizeFully(user.getLastName()));
        if (!newPassword.isEmpty()) {
            user.setRole(getUserById(user.getId()).getRole());
            user.setEncryptedPassword(encryptPassword(newPassword));
        }
        usersDAO.updateUser(user);
    }

    public void removeUser(Long id) {
        usersDAO.deleteUser(id);
    }

    public Users getCurrentUser() {
        return usersDAO.findCurrentUser();
    }
}
