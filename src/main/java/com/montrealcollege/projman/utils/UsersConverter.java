package com.montrealcollege.projman.utils;

import com.montrealcollege.projman.service.UsersService;

import java.beans.PropertyEditorSupport;

public class UsersConverter extends PropertyEditorSupport {

    private final UsersService _service;

    public UsersConverter(UsersService service) {
        _service = service;
    }

    @Override
    public void setAsText(String id) {
        this.setValue(_service.getUserById(Long.parseLong(id)));
    }
}
