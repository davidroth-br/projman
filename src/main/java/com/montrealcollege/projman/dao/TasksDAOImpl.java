package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Tasks;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class TasksDAOImpl implements TasksDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createTask(Tasks task) {

    }

    @Override
    public List<Tasks> listTasks() {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tasks> criteriaQuery = criteriaBuilder.createQuery(Tasks.class);
        Root<Tasks> root = criteriaQuery.from(Tasks.class);
        criteriaQuery.select(root);

        Query<Tasks> query = session.createQuery(criteriaQuery);
        return query.list();
    }

    @Override
    public Tasks findTaskById(Long id) {
        return entityManager.find(Tasks.class, id);
    }

    @Override
    public void updateTask(Tasks task) {

    }

    @Override
    public void deleteTask(Long id) {

    }
}
