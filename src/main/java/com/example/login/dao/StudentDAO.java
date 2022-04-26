package com.example.login.dao;

import com.example.login.entities.Student;
import com.example.login.exception.RoleNotFoundException;
import com.example.login.exception.UserNotFoundException;
import com.example.login.map.JwtResponse;
import com.example.login.map.StudentCreate;
import com.example.login.map.StudentLogin;
import com.example.login.map.StudentShow;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentDAO {
    Student create(StudentCreate student);

    List<StudentShow> getAllStudent();

     void addRoleToStudent(String username, String roleName) throws RoleNotFoundException, UserNotFoundException;

    JwtResponse authenticate(StudentLogin studentLogin);

     Authentication getAuthentication() ;

     Student getCurrentAccount() ;
}
