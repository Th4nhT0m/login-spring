package com.example.login.controller;

import com.example.login.dao.StudentDAO;
import com.example.login.map.ResponseResult;
import com.example.login.map.StudentCreate;
import com.example.login.map.StudentLogin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/student")
@RestController
public class StudentController {

    private final StudentDAO studentService;

    public StudentController(StudentDAO studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseResult> getAllStudent() {
        try {
            return new ResponseEntity<>(new ResponseResult(HttpStatus.OK, "", studentService.getAllStudent()), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("create")
    public ResponseEntity<ResponseResult> saveStudent(@RequestBody StudentCreate student) {
        try {
            return new ResponseEntity<>(new ResponseResult(HttpStatus.OK, "",studentService.create(student)), HttpStatus.OK);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity<ResponseResult> login(@RequestBody StudentLogin student) {
        try {
            return new ResponseEntity<>(new ResponseResult(HttpStatus.OK, "",studentService.login(student)), HttpStatus.OK);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
