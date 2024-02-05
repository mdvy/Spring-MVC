package ru.javarush.medov.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javarush.medov.dao.TaskDao;
import ru.javarush.medov.domain.Status;
import ru.javarush.medov.domain.Task;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class TaskService {
    private TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public List<Task> getAll(int offset, int limit){
        return taskDao.getAll(offset, limit);
    }

    public int getAllCount(){
        return taskDao.getAllCount();
    }

    @Transactional
    public Task edit(int id, String description, Status status){
        Task task = taskDao.getById(id);
        if (isNull(task))
            throw new RuntimeException("Task Not Found");

        task.setDescription(description);
        task.setStatus(status);
        taskDao.saveOrUpdate(task);
        return task;
    }

    public Task create(String description, Status status){
        Task task = new Task(null, description, status);
        taskDao.saveOrUpdate(task);
        return task;
    }


    public void delete(Integer id){
        Task task = taskDao.getById(id);
        if (isNull(task))
            throw new RuntimeException("Task Not Found");

        taskDao.delete(id);
    }

    public List<String> getStatusList(){
        return taskDao.getStatusList();
    }
}
