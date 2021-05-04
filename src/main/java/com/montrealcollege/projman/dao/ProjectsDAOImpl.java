package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Projects;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProjectsDAOImpl implements ProjectsDAO{

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void createProject(Projects project) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(project);
        entityManager.getTransaction().commit();

    }

    @Override
    public List<Projects> listProjects() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Projects> criteria = builder.createQuery(Projects.class);
        Root<Projects> projectsRoot = criteria.from(Projects.class);
        criteria.select(projectsRoot).orderBy(builder.asc(projectsRoot.get("name")));

        TypedQuery<Projects> query = entityManager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public Projects displayProject(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Projects> criteria = builder.createQuery(Projects.class);
        Root<Projects> projectsRoot = criteria.from(Projects.class);
        criteria.select(projectsRoot).where(builder.equal(projectsRoot.get("id"), id));

        TypedQuery<Projects> query = entityManager.createQuery(criteria);
        return query.getSingleResult();
    }

    @Override
    public void updateProject(Projects project) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.merge(project);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteProject(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Projects projects = entityManager.find(Projects.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(projects);
        entityManager.getTransaction().commit();
    }

}
