package com.example.demo.repository;

import com.example.demo.entity.DayType;
import com.example.demo.entity.Group;
import com.example.demo.entity.Mentor;
import com.example.demo.entity.Room;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupRepoTest {

    @Autowired
    GroupRepo groupRepo;
    @Autowired
    MentorRepo mentorRepo;

    @Autowired
    RoomRepo roomRepo;

    @Test
    void findAllGroupByDayTypeEven() {

        Room room1 = new Room(
                2,
                "Google"
        );

        Group group = Group.builder()
                .id(UUID.randomUUID())
                .endTime(LocalTime.parse("09:00:00"))
                .startTime(LocalTime.parse("10:00:00"))
                .dayType("EVEN")
                .room(room1)
                .name("jjj")
                .build();

        groupRepo.save(group);

//        List<Group> even = groupRepo.findAllGroupByDayTypeEven();

//        assertTrue(even.size() > 0);
    }

    @Test
    void findAllGroupByDayTypeOdd() {
        Room room1 = new Room(
                2,
                "Google"
        );

        Group group = Group.builder()
                .id(UUID.fromString("80c219ef-04e8-48e4-b5e6-83160e014ef9"))
                .endTime(LocalTime.parse("09:00:00"))
                .startTime(LocalTime.parse("10:00:00"))
            .dayType(DayType.ODD)
                .room(room1)
                .name("jjj")
                .build();

        groupRepo.save(group);

//        List<Group> even = groupRepo.findAllGroupByDayTypeOdd();

//        assertTrue(even.size() > 0);
    }

    @Test
    void findAllGroupByDayTypeAll() {
        Room room1 = new Room(
                2,
                "Google"
        );

        Group group = Group.builder()
                .id(UUID.randomUUID())
                .endTime(LocalTime.parse("09:00:00"))
                .startTime(LocalTime.parse("10:00:00"))
                .dayType(DayType.ALL)
                .room(room1)
                .name("jjj")
                .build();
        groupRepo.save(group);
//        List<Group> even = groupRepo.findAllGroupByDayTypeAll();
//        assertTrue(even.size() > 0);
    }
}
