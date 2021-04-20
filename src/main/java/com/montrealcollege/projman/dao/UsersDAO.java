package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Users;

import java.util.List;

public interface UsersDAO {

    Users findUserAccount(String userName);

    void createUser(Users user, Long role);

    List<Users> listUsers();


    Users displayUser(Integer id);

    void updateUser(Users user);

    void deleteUser(Users user);


}
