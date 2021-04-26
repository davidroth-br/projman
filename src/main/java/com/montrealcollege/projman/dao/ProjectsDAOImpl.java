package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProjectsDAOImpl implements ProjectsDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createProject(Projects project) {
        Session session = entityManager.unwrap(Session.class);
        session.beginTransaction();
        session.save(project);
        session.getTransaction().commit();
    }

    @Override
    public List<Projects> listProjects() {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Projects> criteriaQuery = criteriaBuilder.createQuery(Projects.class);
        Root<Projects> projectsRoot = criteriaQuery.from(Projects.class);
        criteriaQuery.select(projectsRoot);

        Query<Projects> query = session.createQuery(criteriaQuery);
        return query.list();
    }

    @Override
    public Projects displayProject(Integer id) {
        return null;
    }

    @Override
    public void updateProject(Projects project) {

    }

    @Override
    public void deleteProject(Projects project) {

    }

}
