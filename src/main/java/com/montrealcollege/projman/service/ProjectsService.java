package com.montrealcollege.projman.service;

import com.montrealcollege.projman.dao.ProjectsDAO;
import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Users;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectsService {

    @Autowired
    ProjectsDAO projectsDAO;

    public void addProject(Projects project) {
        project.setName(WordUtils.capitalizeFully(project.getName()));
        projectsDAO.createProject(project);
    }

    public List<Projects> showProjects() {
        return projectsDAO.listProjects();
    }

    public Projects getProjectById(Long id) {
        return projectsDAO.findProjectById(id);
    }

    public void editProject(Projects project) {
        project.setName(WordUtils.capitalizeFully(project.getName()));
        projectsDAO.updateProject(project);
    }

    public void removeProject(Long id) {
        projectsDAO.deleteProject(id);
    }

    public List<Projects> showLeaderProjects(Users user) {
        return projectsDAO.listLeaderProjects(user);
    }
}
