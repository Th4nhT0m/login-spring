package com.example.login.dao;
import com.example.login.entities.Role;
import org.springframework.stereotype.Component;

@Component
public interface RoleDAO {

    Role createRole(String name);

    Role getRoleByName(String name);

}
