package com.example.task_management.dataloader;

import com.example.task_management.model.User;
import com.example.task_management.service.TaskService;
import com.example.task_management.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import com.example.task_management.model.Role;
import com.example.task_management.model.Task;
import com.example.task_management.service.RoleService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private TaskService taskService;
    private RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(InitialDataLoader.class);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Value("${default.admin.mail}")
    private String defaultAdminMail;
    @Value("${default.admin.name}")
    private String defaultAdminName;
    @Value("${default.admin.password}")
    private String defaultAdminPassword;

    @Autowired
    public InitialDataLoader(UserService userService, TaskService taskService, RoleService roleService) {
        this.userService = userService;
        this.taskService = taskService;
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        //ROLES --------------------------------------------------------------------------------------------------------
        roleService.createRole(new Role("ADMIN"));
        roleService.createRole(new Role("USER"));
        roleService.findAll().stream().map(role -> "saved role: " + role.getRole()).forEach(logger::info);

        User admin = new User(
                defaultAdminMail,
                defaultAdminName,
                defaultAdminPassword);
        userService.createUser(admin);
        userService.changeRoleToAdmin(admin);


        User manager = new User(
                "sevak17@mail.com",
                "sevak",
                "112233");
        userService.createUser(manager);
        userService.changeRoleToAdmin(manager);


        userService.createUser(new User(
                "karen@mail.com",
                "Karen",
                "112233"));


        LocalDate today = LocalDate.now();



        taskService.createTask(new Task(
                "Send the finished site to the client",
                "Send the finished site to the client and get feedback. Fix and change any requests by client. Give access to client to all accounts created on their behalf. Send updates to client and wait for client sign-off.",
                today.plusDays(18),
                false,
                userService.getUserByEmail("sevak17@gmail.com").getName()
        ));

        taskService.findAll().stream().map(t -> "saved task: '" + t.getName()
                + "' for owner: " + getOwnerNameOrNoOwner(t)).forEach(logger::info);
    }

    private String getOwnerNameOrNoOwner(Task task) {
        return task.getOwner() == null ? "no owner" : task.getOwner().getName();
    }
}
