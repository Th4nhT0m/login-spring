package com.example.login.service;

import com.example.login.dao.RoleDAO;
import com.example.login.entities.Role;
import com.example.login.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleService implements RoleDAO {
    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role createRole(String name) {
        Role role = new Role(name);
        return repository.save(role);
    }

    @Override
    public Role getRoleByName(String name) {
        return repository.findRoleByName(name);
    }


}
