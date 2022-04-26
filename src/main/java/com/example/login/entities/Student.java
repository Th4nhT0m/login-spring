package com.example.login.entities;

import com.example.login.map.StudentCreate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student")
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private String username;

    @Column(name = "name", nullable = false, updatable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "class_name",nullable = false)
    private String className;
    @Column(length = 10, columnDefinition = "VARCHAR(10) DEFAULT 'male'")
    private String gender;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Student(StudentCreate student) {
        this.className=student.getClassName();
        this.gender=student.getGender();
        this.name=student.getName();
        this.username=student.getUsername();
        this.password=student.getPassword();
    }


}