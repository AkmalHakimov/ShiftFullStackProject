package com.example.demo.repository;

import com.example.demo.entity.*;
import org.junit.jupiter.api.Assertions;
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
class TimeTableRepoTest {

    @Autowired
    TimeTableRepo timeTableRepo;

    @Autowired
    GroupRepo groupRepo;

    @Autowired
    MentorRepo mentorRepo;


    @Test
    void deleteAllByGroup_Id() {
        UUID groupId = UUID.randomUUID();
        UUID mentorId = UUID.randomUUID();
        UUID timeTableId = UUID.randomUUID();
        List<LocalDate> dates = new ArrayList<>(List.of(LocalDate.now()));
        List<Role> roleList = new ArrayList<>();
        Group savedGroup = getGroup(groupId);
        Mentor mentor = Mentor.builder()
                .id(mentorId)
                .roles(roleList)
                .firstName("a")
                .lastName("a")
                .birthDate(new Date(1))
                .password("a")
                .phone("a")
                .build();
        Mentor savedMentor = mentorRepo.save(mentor);
        TimeTable timeTableValue = TimeTable.builder()
                .id(timeTableId)
                .group(savedGroup)
                .status(Status.STARTED)
                .dates(dates)
                .title("timeTableValue")
                .price(100)
                .mentor(savedMentor)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
        TimeTable save = timeTableRepo.save(timeTableValue);
        List<TimeTable> timeTables = timeTableRepo.deleteAllByGroup_Id(groupId);
        assertTrue(timeTables.size()==0);
    }

//    @Test
//    void getLastTimeTable() {
//        UUID groupId = UUID.randomUUID();
////        TimeTable timeTable = SaveAndCreateTimeTable(LocalDate.now(),groupId);
//        TimeTable timeTable1 = SaveAndCreateTimeTable(LocalDate.now().plusDays(1), groupId);
//        TimeTable lastTimeTable = timeTableRepo.getLastTimeTable(groupId);
//        Assertions.assertEquals(lastTimeTable,timeTable1);
//    }

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

    @Test
    void findAllByGroup_Id() {
        UUID groupId = UUID.randomUUID();
        UUID mentorId = UUID.randomUUID();
        UUID timeTableId = UUID.randomUUID();
        List<LocalDate> dates = new ArrayList<>(List.of(LocalDate.now()));
        List<Role> roleList = new ArrayList<>();
        Group savedGroup = getGroup(groupId);
        Mentor mentor = Mentor.builder()
                .id(mentorId)
                .roles(roleList)
                .firstName("a")
                .lastName("a")
                .birthDate(new Date(1))
                .password("g")
                .phone("g")
                .build();
        Mentor savedMentor = mentorRepo.save(mentor);
        TimeTable timeTable = TimeTable.builder()
                .id(timeTableId)
                .group(savedGroup)
                .status(Status.CREATED)
                .dates(dates)
                .title("timeTableValue")
                .price(100)
                .mentor(savedMentor)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
        TimeTable save = timeTableRepo.save(timeTable);
        TimeTable timeTable1 = SaveAndCreateTimeTable(LocalDate.now().plusDays(1), groupId);
        List<TimeTable> allByGroupId = timeTableRepo.findAllByGroup_Id(savedGroup.getId());
        Assertions.assertEquals(save,allByGroupId.get(0));
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

    @Test
    public void testFindLastCompletedTimeTable() {
        UUID groupId = UUID.randomUUID();
        UUID mentorId = UUID.randomUUID();
        UUID timeTableId = UUID.randomUUID();
        List<LocalDate> dates = new ArrayList<>(List.of(LocalDate.now()));
        Group savedGroup = getGroup(groupId);
        Mentor savedMentor = getMentor(mentorId,"y","y");
        TimeTable timeTable = TimeTable.builder()
                .id(timeTableId)
                .group(savedGroup)
                .status(Status.COMPLETED)
                .dates(dates)
                .title("timeTableValue")
                .price(100)
                .mentor(savedMentor)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
        TimeTable save = timeTableRepo.save(timeTable);
        TimeTable timeTable1 = TimeTable.builder()
                .id(timeTableId)
                .group(savedGroup)
                .status(Status.COMPLETED)
                .dates(dates)
                .title("timeTableValue")
                .price(100)
                .mentor(savedMentor)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now())
                .build();
        TimeTable save1 = timeTableRepo.save(timeTable1);
        TimeTable timeTable2 = TimeTable.builder()
                .id(timeTableId)
                .group(savedGroup)
                .status(Status.CREATED)
                .dates(dates)
                .title("timeTableValue")
                .price(100)
                .mentor(savedMentor)
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now())
                .build();
        TimeTable save2 = timeTableRepo.save(timeTable2);
//
//        TimeTable timeTable1 = SaveAndCreateTimeTable(group, LocalDate.now().minusDays(2), Status.COMPLETED);
//        TimeTable timeTable2 = SaveAndCreateTimeTable(group, LocalDate.now().minusDays(1), Status.COMPLETED);
//        TimeTable timeTable = SaveAndCreateTimeTable(group, LocalDate.now(), Status.CREATED);

        TimeTable lastCompletedTimeTable = timeTableRepo.findLastCompletedTimeTable(savedGroup.getId());

        assertEquals(save, lastCompletedTimeTable);
    }
}