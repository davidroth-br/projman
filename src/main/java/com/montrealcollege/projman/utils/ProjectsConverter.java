package com.montrealcollege.projman.utils;

import com.montrealcollege.projman.service.ProjectsService;

import java.beans.PropertyEditorSupport;

public class ProjectsConverter extends PropertyEditorSupport {

    private final ProjectsService _service;

    public ProjectsConverter(ProjectsService service) {
        _service = service;
    }

    @Override
    public void setAsText(String id) {
        this.setValue(_service.getProjectById(Long.parseLong(id)));
    }
}
