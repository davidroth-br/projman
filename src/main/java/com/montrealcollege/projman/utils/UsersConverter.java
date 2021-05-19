package com.montrealcollege.projman.utils;

import com.montrealcollege.projman.dao.UsersDAO;
import com.montrealcollege.projman.model.Users;

import java.beans.PropertyEditorSupport;

public class UsersConverter extends PropertyEditorSupport {

    private UsersDAO service;

    @Override
    public void setAsText(String id) {
        Users user = new Users();
        service.findUserById(Long.parseLong(id));
        this.setValue(user);
    }
}
