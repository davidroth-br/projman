package com.montrealcollege.projman.service;

import com.montrealcollege.projman.dao.TasksDAO;
import com.montrealcollege.projman.model.Tasks;
import com.montrealcollege.projman.utils.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.montrealcollege.projman.utils.Helpers.capitalizeFirstLetter;

@Service
public class TasksService {

    @Autowired
    TasksDAO tasksDAO;

    public void addTask(Tasks task) {
        task.setName(capitalizeFirstLetter(task.getName()));
        tasksDAO.createTask(task);
    }

    public List<Tasks> showUserTasks() {
        return tasksDAO.listUserTasks();
    }

    public Tasks getTaskById(Long id) {
        return tasksDAO.findTaskById(id);
    }

    public void editTask(Long id, int state) {
        Tasks task = getTaskById(id);
        task.setState(state);
        task.setCompletionDate(state == 4 ? Helpers.today() : null);
        editTask(task);
    }

    public void editTask(Tasks task) {
        task.setName(capitalizeFirstLetter(task.getName()));
        tasksDAO.updateTask(task);
    }

    public void removeTask(Long id) {
        tasksDAO.deleteTask(id);
    }
}
