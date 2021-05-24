package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Tasks;

import java.util.List;

public interface TasksDAO {

    void createTask(Tasks task);

    List<Tasks> listTasks();

    Tasks findTaskById(Long id);

    void updateTask(Tasks task);

    void deleteTask(Long id);


}
