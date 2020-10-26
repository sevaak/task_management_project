package com.example.task_management.service;

import com.example.task_management.model.User;
import com.example.task_management.model.Task;

import java.util.List;

public interface TaskService {

    void createTask(Task task);

    void updateTask(Long id, Task task);

    void deleteTask(Long id);

    List<Task> findAll();

    List<Task> findByOwnerOrderByDateDesc(User user);

    void setTaskCompleted(Long id);

    void setTaskNotCompleted(Long id);

    List<Task> findFreeTasks();

    Task getTaskById(Long taskId);

    void giveTaskToUser(Task task, User user);

    void takeTask(Task task);
}
