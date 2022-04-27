package com.example.login.service;


import com.example.login.configuration.SecurityConfig;
import com.example.login.dao.JwtDAO;
import com.example.login.dao.RoleDAO;
import com.example.login.dao.StudentDAO;
import com.example.login.entities.Role;
import com.example.login.entities.Student;
import com.example.login.exception.RoleNotFoundException;
import com.example.login.exception.UserNotFoundException;
import com.example.login.map.JwtResponse;
import com.example.login.map.StudentCreate;
import com.example.login.map.StudentLogin;
import com.example.login.map.StudentShow;
import com.example.login.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service("studentService")
@Slf4j
public class StudentService implements StudentDAO, UserDetailsService {
    private final StudentRepository repository;

    private final JwtDAO jwtDAO;
    private final PasswordEncoder passwordEncoder;
    private final RoleDAO roleDAO;

    private final ModelMapper modelMapper;

    public StudentService(StudentRepository repository, JwtDAO jwtDAO, PasswordEncoder passwordEncoder, RoleDAO roleDAO, ModelMapper modelMapper) {
        this.repository = repository;
        this.jwtDAO = jwtDAO;
        this.passwordEncoder = passwordEncoder;
        this.roleDAO = roleDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public Student create(StudentCreate student) {
        Student newStudent = new Student(student);
        newStudent.setPassword(passwordEncoder.encode(student.getPassword()));
        Role role = roleDAO.getRoleByName("STUDENT");
        if (role == null) role = roleDAO.createRole("STUDENT");
        newStudent.setRole(role);
        return repository.save(newStudent);
    }

    @Override
    public List<StudentShow> getAllStudent() {
        List<Student> list = repository.findAll();
        List<StudentShow> listShow = new ArrayList<>();
        for (Student student : list) {
            listShow.add(modelMapper.map(student, StudentShow.class));
        }
        return listShow;
    }

    @Override
    public void addRoleToStudent(String username, String roleName) throws RoleNotFoundException, UserNotFoundException {
        Role role = roleDAO.getRoleByName(roleName);
        Student student = repository.findStudentByUsername(username);
        if (role == null) throw new RoleNotFoundException();
        if (student == null) throw new UserNotFoundException();
        student.setRole(role);
    }

    @Override
    public JwtResponse authenticate(StudentLogin studentLogin) {
        UserDetails userDetails;
        try {
            userDetails = loadUserByUsername(studentLogin.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
        if (passwordEncoder.matches(studentLogin.getPassword(), userDetails.getPassword())) {
            Student student = repository.findStudentByUsername(userDetails.getUsername());
            Map<String, String> claims = new HashMap<>();
            claims.put("username", userDetails.getUsername());

            String authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
            claims.put(SecurityConfig.AUTHORITIES_CLAIM_NAME, authorities);
            claims.put("userId", student.getId().toString());

            JwtResponse jwt = jwtDAO.createJwtForClaims(userDetails.getUsername(), claims);
//            String refreshToken = jwtService.refreshToken(jwt);
            return new JwtResponse(jwt);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public Student getCurrentAccount() {
        Authentication authentication = getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return repository.findStudentByUsername(currentUserName);
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = repository.findStudentByUsername(username);
        if (student == null) {
            throw new UsernameNotFoundException(username);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Role roles = student.getRole();
        grantedAuthorities.add(new SimpleGrantedAuthority(roles.getName()));

        return new org.springframework.security.core.userdetails.User(
                student.getUsername(), student.getPassword(), grantedAuthorities);
    }


    public Student getCurrentUserInfo() {
        Student student = getCurrentAccount();
        if (student == null) return null;
        return student;
    }
}
