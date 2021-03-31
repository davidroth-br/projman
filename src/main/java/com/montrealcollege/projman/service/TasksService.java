package com.montrealcollege.projman.service;

import com.montrealcollege.projman.dao.TasksDAO;
import com.montrealcollege.projman.model.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksService {

        @Autowired
        TasksDAO tasksDAO;

        public void addTask(Tasks task) {
            tasksDAO.createTask(task);
        }

        public List<Tasks> showTasks() {
            return tasksDAO.listTasks();
        }
    
}
