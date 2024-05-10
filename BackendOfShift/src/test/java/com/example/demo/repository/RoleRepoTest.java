package com.example.demo.repository;

import com.example.demo.entity.Role;
import com.example.demo.entity.Room;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepoTest {

    @Autowired
   RoleRepo roleRepo;

    @Test
    void findAllByName() {
        Role role = Role.builder()
                .id(5)
                .name("dwfv")
                .build();
        roleRepo.save(role);
        List<Role> roleList = roleRepo.findAllByName("dwfv");
        assertTrue(roleList.size() > 0);
    }
}