package com.example.task_management.controller;

import com.example.task_management.model.User;
import com.example.task_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.task_management.model.Task;
import com.example.task_management.service.TaskService;

@Controller
public class JobController {
    private UserService userService;
    private TaskService taskService;

    @Autowired
    public JobController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/giving")
    public String showTaskForm(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("freeTasks", taskService.findFreeTasks());
        return "forms/giving";
    }

    @GetMapping("/giving/{userId}")
    public String showUserTaskForm(@PathVariable Long userId, Model model) {
        model.addAttribute("selectedUser", userService.getUserById(userId));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("freeTasks", taskService.findFreeTasks());
        return "forms/giving";
    }

    @GetMapping("/giving/give/{userId}/{taskId}")
    public String giveTaskToUser(@PathVariable Long userId, @PathVariable Long taskId) {
        Task selectedTask = taskService.getTaskById(taskId);
        User selectedUser = userService.getUserById(userId);
        taskService.giveTaskToUser(selectedTask, selectedUser);
        return "redirect:/giving/" + userId;
    }

    @GetMapping("giving/take/{userId}/{taskId}")
    public String takeTaskFromUser(@PathVariable Long userId, @PathVariable Long taskId) {
        Task selectedTask = taskService.getTaskById(taskId);
        taskService.takeTask(selectedTask);
        return "redirect:/giving/" + userId;
    }

}



