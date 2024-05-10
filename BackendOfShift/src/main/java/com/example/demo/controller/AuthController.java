package com.example.demo.controller;


import com.example.demo.payload.request.ReqUser;
import com.example.demo.service.authService.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@RequestBody ReqUser reqUser){
        return authService.Register(reqUser);
    }

    @PostMapping("/login")
    public HttpEntity<?> loginUser(@RequestBody ReqUser reqUser){
        return authService.Login(reqUser);
    }

    @PostMapping("/refresh")
    public HttpEntity<?> refreshToken(@RequestParam String refreshToken){
        return authService.refreshToken(refreshToken);
    }

}
