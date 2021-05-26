package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Tasks;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class TasksDAOImpl implements TasksDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createTask(Tasks task) {
        entityManager.persist(task);
    }

    @Override
    public List<Tasks> listTasks() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tasks> criteriaQuery = criteriaBuilder.createQuery(Tasks.class);
        Root<Tasks> root = criteriaQuery.from(Tasks.class);
        criteriaQuery.select(root);

        TypedQuery<Tasks> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
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
