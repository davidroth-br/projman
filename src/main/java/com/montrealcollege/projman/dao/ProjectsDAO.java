package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Users;

import java.util.List;

public interface ProjectsDAO {

    void createProject(Projects project);

    List<Projects> listProjects();

    List<Projects> listLeaderProjects(Users user);

    Projects findProjectById(Long id);

    void updateProject(Projects project);

    void deleteProject(Long id);


}
