package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.payload.request.ReqUser;
import com.example.demo.service.security.CurrentUser;
import com.example.demo.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public HttpEntity<?> postStudents(@RequestBody ReqUser reqUser){
       return userService.SaveUser(reqUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public HttpEntity<?> getStudents(){
        return userService.getUsers();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public HttpEntity<?> delStudent(@RequestParam String userId){
        return userService.DelUser(userId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public HttpEntity<?> editStudent(@RequestParam String userId,@RequestBody ReqUser reqUser){
        return userService.editStudent(userId,reqUser);
    }

    @GetMapping("/me")
    public HttpEntity<?> getMe(@CurrentUser User user){
        return userService.getMe(user);
    }
}
