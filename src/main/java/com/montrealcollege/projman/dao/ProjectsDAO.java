package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Projects;

import java.util.List;

public interface ProjectsDAO {

    void createProject(Projects project);

    List<Projects> listProjects();

    Projects displayProject(Long id);

    void updateProject(Projects project);

    void deleteProject(Long id);


}
