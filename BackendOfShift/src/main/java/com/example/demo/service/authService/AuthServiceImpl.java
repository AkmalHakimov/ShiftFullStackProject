package com.example.demo.service.authService;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.payload.request.ReqUser;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public HttpEntity<?> Register(ReqUser reqUser) {
        try {
            List<Role> roleUser = roleRepo.findAllByName("ROLE_USER");
            User user = User.builder()
                    .id(UUID.randomUUID())
                    .phone(reqUser.getPhone())
                    .password(passwordEncoder.encode(reqUser.getPassword()))
                    .firstName(reqUser.getFirstName())
                    .lastName(reqUser.getLastName())
                    .age(reqUser.getAge())
                    .description("")
                    .roles(roleUser)
                    .build();
            userRepo.save(user);
            return ResponseEntity.ok("registered✅");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    @Override
    public HttpEntity<?> Login(ReqUser reqUser) {
        try {
            authenticate(reqUser);
            User newUser = userRepo.findByPhone(reqUser.getPhone()).orElseThrow();
            String jwt = jwtService.generateJwtToken(newUser);
            String jwtRefresh = jwtService.generateJwtRefreshToken(newUser);
            Map<String,Object> map = new HashMap<>();
            map.put("refresh_token",jwtRefresh);
            map.put("access_token",jwt);
            map.put("role",newUser.getRoles());
            return ResponseEntity.ok(map);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("Xato Login yoki Parol kiritdingiz");
        }
    }

    private void authenticate(ReqUser reqUser) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        reqUser.getPhone(),
                        reqUser.getPassword()
                )
        );
    }

    @Override
    public HttpEntity<?> refreshToken(String refreshToken) {
        String string = jwtService.extractSubjectFromJwt(refreshToken);
        User user = userRepo.findById(UUID.fromString(string)).orElseThrow();
        String access_Token = jwtService.generateJwtToken(user);
        return ResponseEntity.ok(access_Token);
    }
}
