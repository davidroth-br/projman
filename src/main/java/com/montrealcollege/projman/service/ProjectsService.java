package com.montrealcollege.projman.service;

import com.montrealcollege.projman.dao.ProjectsDAO;
import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.montrealcollege.projman.utils.Helpers.capitalizeFirstLetter;

@Service
public class ProjectsService {

    @Autowired
    ProjectsDAO projectsDAO;

    public void addProject(Projects project) {
        project.setName(capitalizeFirstLetter(project.getName()));
        projectsDAO.createProject(project);
    }

    public List<Projects> showProjects() {
        return projectsDAO.listProjects();
    }

    public Projects getProjectById(Long id) {
        return projectsDAO.findProjectById(id);
    }

    public void editProject(Projects project) {
        project.setName(capitalizeFirstLetter(project.getName()));
        projectsDAO.updateProject(project);
    }

    public void removeProject(Long id) {
        projectsDAO.deleteProject(id);
    }

    public List<Projects> showLeaderProjects(Users user) {
        return projectsDAO.listLeaderProjects(user);
    }
}
