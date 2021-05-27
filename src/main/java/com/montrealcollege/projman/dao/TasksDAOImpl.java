package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Projects;
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
        CriteriaQuery<Tasks> criteria = criteriaBuilder.createQuery(Tasks.class);
        Root<Tasks> tasksRoot = criteria.from(Tasks.class);
        criteria.select(tasksRoot).orderBy(
                criteriaBuilder.asc(tasksRoot.get("project").get("name")),
                criteriaBuilder.asc(tasksRoot.get("name")));

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public Tasks findTaskById(Long id) {
        return entityManager.find(Tasks.class, id);
    }

    @Override
    public void updateTask(Tasks task) {
        Tasks dbTask = entityManager.find(Tasks.class, task.getId());

        dbTask.setName(task.getName());
        dbTask.setDescription(task.getDescription());
        dbTask.setDeadline(task.getDeadline());
        dbTask.setPriority(task.getPriority());
        dbTask.setState(task.getState());
        dbTask.setCompletionDate(task.getCompletionDate());
        dbTask.setProject(task.getProject());
        dbTask.setUsers(task.getUsers());

        entityManager.persist(dbTask);
        entityManager.flush();
    }

    @Override
    public void deleteTask(Long id) {
        Tasks task = entityManager.find(Tasks.class, id);
        entityManager.remove(task);
    }
}
