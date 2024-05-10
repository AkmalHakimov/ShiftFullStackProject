package com.example.demo.service.authService;

import com.example.demo.payload.request.ReqUser;
import org.springframework.http.HttpEntity;

public interface AuthService {
    HttpEntity<?> Register(ReqUser reqUser);

    HttpEntity<?> Login(ReqUser reqUser);

    HttpEntity<?> refreshToken(String refreshToken);
}
