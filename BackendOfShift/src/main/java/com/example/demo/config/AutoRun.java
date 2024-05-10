package com.example.demo.config;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class AutoRun implements CommandLineRunner {

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        List<Role> all = roleRepo.findAll();
        if(all.size()==0){
            List<Role> tempRoles = getRoles();
            List<Role> roles = roleRepo.saveAll(tempRoles);
            User user = User.builder().
                    id(UUID.randomUUID())
                    .password(passwordEncoder.encode("123"))
                    .phone("123")
                    .firstName("Akmal")
                    .lastName("Hakimov")
                    .age(21)
                    .description("")
                    .roles(roles)
                    .build();
            userRepo.save(user);
        }
    }

    private static List<Role> getRoles() {
        List<Role> tempRoles = new ArrayList<>(List.of(
                new Role(1, "ROLE_ADMIN"),
                new Role(2, "ROLE_USER"),
                new Role(3, "ROLE_MENTOR")
        ));
        return tempRoles;
    }
}
