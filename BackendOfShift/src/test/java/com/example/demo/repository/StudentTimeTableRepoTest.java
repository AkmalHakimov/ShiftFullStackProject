package com.example.demo.repository;

import com.example.demo.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentTimeTableRepoTest {

    @Autowired
    StudentTimeTableRepo studentTimeTableRepo;

    @Autowired
    TimeTableRepo timeTableRepo;

    @Autowired
    MentorRepo mentorRepo;

    @Autowired
    GroupRepo groupRepo;

    @Autowired
    UserRepo userRepo;


    @Test
    void findAllByTimeTableId() {
        UUID groupId = UUID.randomUUID();
        TimeTable timeTable = SaveAndCreateTimeTable(LocalDate.now(), groupId);
        User user = getUser();
        TimeTableStudent timeTableStudent = new TimeTableStudent(
                UUID.randomUUID(),
                100,
                true,
                null,
                timeTable,
                user
        );
        TimeTableStudent save = studentTimeTableRepo.save(timeTableStudent);
        List<TimeTableStudent> allByTimeTableId = studentTimeTableRepo.findAllByTimeTableId(timeTable.getId());
        assertEquals(allByTimeTableId.get(0),save);
    }

    public TimeTable SaveAndCreateTimeTable(LocalDate localDate, UUID groupId){
        UUID mentorId = UUID.randomUUID();
        UUID timeTableId = UUID.randomUUID();
        List<LocalDate> dates = new ArrayList<>(List.of(LocalDate.now()));
        Group savedGroup = getGroup(groupId);
        Mentor mentor = getMentor(mentorId,"t","t");
        TimeTable timeTableValue = TimeTable.builder()
                .id(timeTableId)
                .group(savedGroup)
                .status(Status.CREATED)
                .dates(dates)
                .title("timeTableValue")
                .price(100)
                .mentor(mentor)
                .startDate(localDate)
                .endDate(LocalDate.now())
                .build();
        return timeTableRepo.save(timeTableValue);
    }

    private Mentor getMentor(UUID mentorId,String password,String phone) {
        List<Role> roleList = new ArrayList<>();
        Mentor mentor = Mentor.builder()
                .id(mentorId)
                .roles(roleList)
                .firstName("a")
                .lastName("a")
                .birthDate(new Date(1))
                .password(password)
                .phone(phone)
                .build();
        Mentor savedMentor = mentorRepo.save(mentor);
        return savedMentor;
    }

    private Group getGroup(UUID groupId) {
        Group group = Group.builder()
                .id(groupId)
                .name("groupValue")
                .dayType("EVEN")
                .room(new Room(1, "Security"))
                .startTime(LocalTime.now())
                .endTime(LocalTime.now())
                .build();
        Group savedGroup = groupRepo.save(group);
        return savedGroup;
    }

    private User getUser() {
        List<Role> roleList = new ArrayList<>();
        User user = new User(
                UUID.randomUUID(),
                "123",
                "123",
                "",
                "",
                10,
                "",
                roleList
        );
        User save = userRepo.save(user);
        return save;
    }
}