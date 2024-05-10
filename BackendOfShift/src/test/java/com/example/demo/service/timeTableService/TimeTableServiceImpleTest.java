package com.example.demo.service.timeTableService;

import com.example.demo.entity.Group;
import com.example.demo.entity.Mentor;
import com.example.demo.entity.Status;
import com.example.demo.entity.TimeTable;
import com.example.demo.payload.request.ReqTimeTable;
import com.example.demo.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.method;


class TimeTableServiceImpleTest {

    private TimeTableServiceImple underTest;

    @Mock
    TimeTableRepo timeTableRepo;

    @Mock
    MentorRepo mentorRepo;

    @Mock
    GroupRepo groupRepo;

    @Mock
    StudentTimeTableRepo studentTimeTableRepo;

    @Mock
    TimeTableDayRepo timeTableDayRepo;

    @Captor
    private ArgumentCaptor<TimeTable> timeTableCaptor;
    @Captor
    private ArgumentCaptor<UUID> idCaptor;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new TimeTableServiceImple(mentorRepo,groupRepo,timeTableRepo,studentTimeTableRepo,timeTableDayRepo);
    }

    @Test
    void completeTable() {
        UUID id = UUID.randomUUID();
        TimeTable timeTable = new TimeTable(

        );
        timeTable.setId(id);
        when(timeTableRepo.findById(id)).thenReturn(Optional.of(timeTable));
        underTest.completeTable(id);
    }

    @Test
    void saveTimeTable() {
        UUID groupId = UUID.randomUUID();
        UUID mentorId = UUID.randomUUID();
        ReqTimeTable reqTimeTable = new ReqTimeTable();
        reqTimeTable.setGroupId(groupId);
        reqTimeTable.setMentorId(mentorId);
        reqTimeTable.setTitle("Time Table Title");
        reqTimeTable.setPrice(9);
        Group group = new Group();
        group.setId(groupId);
        Mentor mentor = new Mentor();
        mentor.setId(mentorId);
        UUID timeTableId = UUID.randomUUID();
        TimeTable savedTimeTable = new TimeTable(
                timeTableId,
                reqTimeTable.getTitle(),
                reqTimeTable.getPrice(),
                Status.CREATED,
                group,
                mentor
        );
        when(groupRepo.findById(groupId)).thenReturn(Optional.of(group));
        when(mentorRepo.findById(mentorId)).thenReturn(Optional.of(mentor));
        when(timeTableRepo.save(timeTableCaptor.capture())).thenReturn(savedTimeTable);
        HttpEntity<?> result = underTest.SaveTimeTable(reqTimeTable);
        TimeTable capturedTimeTable = timeTableCaptor.getValue();
        Assertions.assertEquals(reqTimeTable.getTitle(), capturedTimeTable.getTitle());
        Assertions.assertEquals(reqTimeTable.getPrice(), capturedTimeTable.getPrice());
        Assertions.assertEquals(Status.CREATED, capturedTimeTable.getStatus());
        Assertions.assertEquals(group, capturedTimeTable.getGroup());
        Assertions.assertEquals(mentor, capturedTimeTable.getMentor());
        assertEquals(result.getBody(),savedTimeTable);
    }

    @Test
    void saveAllTimeTable() {
        List<LocalDate> dates = List.of(LocalDate.now());
        UUID groupId = UUID.randomUUID();
        UUID mentorId = UUID.randomUUID();
        ReqTimeTable reqTimeTable = new ReqTimeTable();
        reqTimeTable.setGroupId(groupId);
        reqTimeTable.setMentorId(mentorId);
        reqTimeTable.setTitle("Time Table Title");
        reqTimeTable.setPrice(9);
        Group group = new Group();
        group.setId(groupId);
        Mentor mentor = new Mentor();
        mentor.setId(mentorId);
        UUID timeTableId = UUID.randomUUID();
        TimeTable savedTimeTable = new TimeTable(
                timeTableId,
                reqTimeTable.getTitle(),
                reqTimeTable.getPrice(),
                LocalDate.now(),
                dates.get(0),
                Status.CREATED,
                group,
                mentor,
                dates
        );
        when(groupRepo.findById(groupId)).thenReturn(Optional.of(group));
        when(mentorRepo.findById(mentorId)).thenReturn(Optional.of(mentor));
        when(timeTableRepo.save(timeTableCaptor.capture())).thenReturn(savedTimeTable);
        HttpEntity<?> result = underTest.SaveTimeTable(reqTimeTable);
        TimeTable capturedTimeTable = timeTableCaptor.getValue();
        Assertions.assertEquals(reqTimeTable.getTitle(), capturedTimeTable.getTitle());
        Assertions.assertEquals(reqTimeTable.getPrice(), capturedTimeTable.getPrice());
        Assertions.assertEquals(Status.CREATED, capturedTimeTable.getStatus());
        Assertions.assertEquals(group, capturedTimeTable.getGroup());
        Assertions.assertEquals(mentor, capturedTimeTable.getMentor());
        assertEquals(result.getBody(),savedTimeTable);
    }

    @Test
    void getTimeTable() {
        UUID groupId = UUID.randomUUID();
        TimeTable timeTable1 = new TimeTable();
        TimeTable timeTable2 = new TimeTable();
        List<TimeTable> timeTables = Arrays.asList(timeTable1, timeTable2);
        when(timeTableRepo.findAllByGroup_Id(groupId)).thenReturn(timeTables);
        HttpEntity<?> response = underTest.GetTimeTable(groupId);
        verify(timeTableRepo, times(1)).findAllByGroup_Id(groupId);
        assertEquals(timeTables, response.getBody());
    }

    @Test
    void getLastTimeTable() {
        UUID groupId = UUID.randomUUID();
        TimeTable lastTimeTable = new TimeTable();

        when(timeTableRepo.getLastTimeTable(groupId)).thenReturn(lastTimeTable);

        HttpEntity<?> response = underTest.GetLastTimeTable(groupId);

        verify(timeTableRepo, times(1)).getLastTimeTable(groupId);
        assertEquals(lastTimeTable, response.getBody());
    }

    @Test
    void getTimeTableById() {
        UUID id = UUID.randomUUID();
        TimeTable timeTable = new TimeTable();

        when(timeTableRepo.findById(id)).thenReturn(Optional.of(timeTable));

        HttpEntity<?> response = underTest.GetTimeTableById(id);

        verify(timeTableRepo, times(1)).findById(id);
        assertEquals(timeTable, response.getBody());
    }

    @Test
    void delTimeTable() {
        UUID id = UUID.randomUUID();
        doNothing().when(timeTableRepo).deleteById(id);
        HttpEntity<?> response = underTest.delTimeTable(id);
        Mockito.verify(timeTableRepo, times(1)).deleteById(id);
        assertEquals("Deleted✅", response.getBody());
    }

    @Test
    void editTimeTable() {
        UUID id = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        UUID mentorId = UUID.randomUUID();
        ReqTimeTable reqTimeTable = new ReqTimeTable();
        reqTimeTable.setGroupId(groupId);
        reqTimeTable.setMentorId(mentorId);
        reqTimeTable.setTitle("New Time Table Title");
        reqTimeTable.setPrice(10);
        reqTimeTable.setStatus(Status.COMPLETED);
        Group group = new Group();
        group.setId(groupId);
        Mentor mentor = new Mentor();
        mentor.setId(mentorId);
        TimeTable savedTimeTable = new TimeTable();
        savedTimeTable.setId(id);
        savedTimeTable.setTitle("New Time Table Title");
        savedTimeTable.setPrice(10);
        savedTimeTable.setStatus(Status.COMPLETED);
        savedTimeTable.setGroup(group);
        savedTimeTable.setMentor(mentor);
        when(groupRepo.findById(groupId)).thenReturn(Optional.of(group));
        when(mentorRepo.findById(mentorId)).thenReturn(Optional.of(mentor));
        when(timeTableRepo.save(savedTimeTable)).thenReturn(savedTimeTable);
        HttpEntity<?> result = underTest.editTimeTable(id, reqTimeTable);
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("edited✅", responseEntity.getBody());
        Mockito.verify(timeTableRepo).save(savedTimeTable);
    }

    @Test
    void startTimeTable() {

    }
}