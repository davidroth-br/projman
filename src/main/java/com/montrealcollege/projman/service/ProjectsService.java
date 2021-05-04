package com.montrealcollege.projman.service;

import com.montrealcollege.projman.dao.ProjectsDAO;
import com.montrealcollege.projman.model.Projects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectsService {

        @Autowired
        ProjectsDAO projectsDAO;

        public void addProject(Projects project) {
            projectsDAO.createProject(project);
        }

        public List<Projects> showProjects() {
            return projectsDAO.listProjects();
        }

    public Projects getUserById(Long id) {
        return projectsDAO.displayProject(id);
    }

    public void editUser(Projects project) {
        projectsDAO.updateProject(project);
    }

    public void removeUser(Long id) {

        projectsDAO.deleteProject(id);
    }
}
