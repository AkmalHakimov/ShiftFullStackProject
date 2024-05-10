package com.example.demo.service.userService;

import com.example.demo.entity.User;
import com.example.demo.payload.request.ReqUser;
import org.springframework.http.HttpEntity;

public interface UserService {
    HttpEntity<?> SaveUser(ReqUser reqUser);

    HttpEntity<?> getUsers();

    HttpEntity<?> DelUser(String userId);

    HttpEntity<?> editStudent(String userId, ReqUser reqUser);

    HttpEntity<?> getMe(User user);
}
