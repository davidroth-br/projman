package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Projects;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class ProjectsDAOImpl implements ProjectsDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createProject(Projects project) {
        entityManager.persist(project);
    }

    @Override
    public List<Projects> listProjects() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Projects> criteria = builder.createQuery(Projects.class);
        Root<Projects> projectsRoot = criteria.from(Projects.class);
        criteria.select(projectsRoot).orderBy(builder.asc(projectsRoot.get("name")));

        TypedQuery<Projects> query = entityManager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public Projects findProjectById(Long id) {
        return entityManager.find(Projects.class, id);
    }

    @Override
    public void updateProject(Projects project) {
        Projects dbProject = entityManager.find(Projects.class, project.getId());

        dbProject.setName(project.getName());
        dbProject.setDescription(project.getDescription());
        dbProject.setStartDate(project.getStartDate());
        dbProject.setEndDate(project.getEndDate());
        dbProject.setLeader(project.getLeader());

        entityManager.persist(project);
    }

    @Override
    public void deleteProject(Long id) {
        Projects projects = entityManager.find(Projects.class, id);
        entityManager.remove(projects);
    }

}
