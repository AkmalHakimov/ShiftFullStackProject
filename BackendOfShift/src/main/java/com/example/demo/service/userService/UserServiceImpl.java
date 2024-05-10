package com.example.demo.service.userService;

import com.example.demo.entity.Group;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.payload.request.ReqUser;
import com.example.demo.payload.response.ResUser;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.authService.AuthService;
import com.example.demo.service.security.JwtService;
import com.example.demo.service.studentTimeTableService.StudentTimeTableServiceImple;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public HttpEntity<?> getMe(User user) {
        ResUser resUser = new ResUser(
                user.getFirstName(),
                user.getLastName(),
                user.getAge(),
                user.getDescription(),
                user.getRoles()
        );
        return ResponseEntity.ok(resUser);
    }

    @Override
    public HttpEntity<?> SaveUser(ReqUser reqUser) {
        try {
            List<Role> roleUser = roleRepo.findAllByName("ROLE_USER");
            User user = new User(
                    UUID.randomUUID(),
                    reqUser.getPhone(),
                    passwordEncoder.encode(reqUser.getPassword()),
                    reqUser.getFirstName(),
                    reqUser.getLastName(),
                    reqUser.getAge(),
                    reqUser.getDescription(),
                    roleUser
            );
            User save = userRepo.save(user);
            return ResponseEntity.ok(save);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    @Override
    public HttpEntity<?> getUsers() {
        try {
            List<User> users = userRepo.getByRoleUser();
            return ResponseEntity.ok(users);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    @Override
    @Transactional
    public HttpEntity<?> DelUser(String userId) {
        try {
            userRepo.deleteByUserId(UUID.fromString(userId));
            userRepo.deleteById(UUID.fromString(userId));
            return ResponseEntity.ok("deleted✅");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }


    @Override
    public HttpEntity<?> editStudent(String userId, ReqUser reqUser) {
        try {
            User user1 = userRepo.findByPhone(reqUser.getPhone()).orElseThrow();
            User user = new User(
                    UUID.fromString(userId),
                    reqUser.getPhone(),
                    user1.getPassword(),
                    reqUser.getFirstName(),
                    reqUser.getLastName(),
                    reqUser.getAge(),
                    reqUser.getDescription(),
                    user1.getRoles()
            );
            userRepo.save(user);
            return ResponseEntity.ok("edited✅");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }
}
