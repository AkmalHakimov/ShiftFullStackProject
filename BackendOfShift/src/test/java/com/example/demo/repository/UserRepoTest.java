package com.example.demo.repository;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepoTest {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Test
    void findByPhone() {
        List<Role> roleList = new ArrayList<>(List.of(new Role(1,"ROLE_TEACHER")));
        User user = new User(
                UUID.randomUUID(),
                "123",
                "123",
                "a",
                "a",
                12,
                "1",
                roleList
        );
        userRepo.save(user);
        User user1 = userRepo.findByPhone("123").orElseThrow();
        Assertions.assertEquals(user1.getPhone(),"123");
    }

    @Test
    void getByRoleUser() {
        Role role = roleRepo.findById(2).orElseThrow();
        List<Role> roleList = new ArrayList<>(List.of(role));
        User user = new User(
                UUID.randomUUID(),
                "123",
                "123",
                "a",
                "a",
                12,
                "1",
                roleList
        );
        User save = userRepo.save(user);
        List<User> byRoleUser = userRepo.getByRoleUser();
        Assertions.assertEquals(byRoleUser.get(0).getRoles().get(0).getId(),save.getRoles().get(0).getId());
    }
}