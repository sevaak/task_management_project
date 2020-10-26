package com.example.task_management.service;

import com.example.task_management.model.Role;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);

    List<Role> findAll();
}
