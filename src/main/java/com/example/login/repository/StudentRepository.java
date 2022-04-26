package com.example.login.repository;

import com.example.login.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    Student findStudentByUsername(String username);

    boolean existsByUsername(String username);

}