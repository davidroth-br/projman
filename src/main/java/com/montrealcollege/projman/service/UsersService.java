package com.montrealcollege.projman.service;

import com.montrealcollege.projman.dao.UsersDAO;
import com.montrealcollege.projman.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    UsersDAO usersDAO;

    public void addUser(Users user) {
        usersDAO.createUser(user);
    }

    public List<Users> showUsers() {
        return usersDAO.listUsers();
    }

    public Users getUserById(Long id) {
        return usersDAO.displayUser(id);
    }

    public void editUser(Users user) {
        usersDAO.updateUser(user);
    }

    public void removeUser(Long id) {

        usersDAO.deleteUser(id);
    }
}
