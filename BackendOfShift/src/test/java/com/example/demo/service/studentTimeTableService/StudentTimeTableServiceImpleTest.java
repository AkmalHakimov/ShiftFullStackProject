package com.example.demo.service.studentTimeTableService;

import com.example.demo.entity.TimeTable;
import com.example.demo.entity.TimeTableStudent;
import com.example.demo.entity.User;
import com.example.demo.payload.request.ReqStudentTimeTable;
import com.example.demo.repository.*;
import com.example.demo.service.timeTableService.TimeTableServiceImple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentTimeTableServiceImpleTest {

    private StudentTimeTableServiceImple underTest;

    @Mock
    private UserRepo userRepo;

    @Mock
    private StudentTimeTableRepo studentTimeTableRepo;

    @Mock
    TimeTableRepo timeTableRepo;

    @Mock
    MentorRepo mentorRepo;

    @Mock
    GroupRepo groupRepo;


    @Mock
    TimeTableDayRepo timeTableDayRepo;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new StudentTimeTableServiceImple(studentTimeTableRepo,timeTableRepo,timeTableDayRepo,userRepo);
    }
//
//    @Test
//    public void saveStudentTimeTable() {
//        UUID timeTableId = UUID.randomUUID();
//        UUID studentId = UUID.randomUUID();
//        Integer price = 10;
//        TimeTable timeTable = new TimeTable();
//        timeTable.setDates(new ArrayList<>()); // Initialize the dates list
//        User user = new User();
//        TimeTableStudent timeTableStudent = new TimeTableStudent();
//
//        // Mock the repositories and their return values
//        when(timeTableRepo.findById(timeTableId)).thenReturn(Optional.of(timeTable));
//        when(userRepo.findById(studentId)).thenReturn(Optional.of(user));
//        when(studentTimeTableRepo.save(timeTableStudent)).thenReturn(timeTableStudent);
//
//        // Create the request object
//        ReqStudentTimeTable reqStudentTimeTable = new ReqStudentTimeTable();
//        reqStudentTimeTable.setTimeTableId(timeTableId);
//        reqStudentTimeTable.setStudentId(studentId);
//        reqStudentTimeTable.setPrice(price);
//
//        // Call the method being tested
//        HttpEntity<?> response = underTest.saveStudentTimeTable(reqStudentTimeTable);
//
//        // Verify the mock interactions and assert the response
//        Mockito.verify(timeTableRepo, times(1)).findById(timeTableId);
//        Mockito.verify(userRepo, times(1)).findById(studentId);
//        Mockito.verify(studentTimeTableRepo, times(1)).save(Mockito.any(TimeTableStudent.class));
//        assertEquals("An error has occurred", response.getBody());
//    }

    @Test
    void getStudentTimeTable() {
        UUID id = UUID.randomUUID();
        TimeTableStudent student1 = new TimeTableStudent();
        TimeTableStudent student2 = new TimeTableStudent();
        List<TimeTableStudent> students = Arrays.asList(student1, student2);
        when(studentTimeTableRepo.findAllByTimeTableId(id)).thenReturn(students);
        HttpEntity<?> response = underTest.getStudentTimeTable(id);
        Mockito.verify(studentTimeTableRepo, times(1)).findAllByTimeTableId(id);
        assertEquals(students, response.getBody());
    }

    @Test
    void delStudentTimeTable() {
        UUID id = UUID.randomUUID();
            doNothing().when(studentTimeTableRepo).deleteById(id);
        HttpEntity<?> response = underTest.delStudentTimeTable(id);
        verify(studentTimeTableRepo, times(1)).deleteById(id);
        assertEquals("Deleted✅", response.getBody());
    }

    @Test
    void editStudentTimeTable() {
        UUID timeTableId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        int price = 10;
        TimeTable timeTable = new TimeTable();
        User user = new User();
        TimeTableStudent timeTableStudent = new TimeTableStudent();
        UUID id = UUID.randomUUID();

        when(timeTableRepo.findById(timeTableId)).thenReturn(Optional.of(timeTable));
        when(userRepo.findById(studentId)).thenReturn(Optional.of(user));
        when(studentTimeTableRepo.save(timeTableStudent)).thenReturn(timeTableStudent);

        ReqStudentTimeTable reqStudentTimeTable = new ReqStudentTimeTable();
        reqStudentTimeTable.setTimeTableId(timeTableId);
        reqStudentTimeTable.setStudentId(studentId);
        reqStudentTimeTable.setPrice(price);

        HttpEntity<?> response = underTest.saveStudentTimeTable(reqStudentTimeTable);

        verify(timeTableRepo, times(1)).findById(timeTableId);
        verify(userRepo, times(1)).findById(studentId);
//        verify(studentTimeTableRepo, times(1)).save();
        assertEquals("Saved✅", response.getBody());
    }
}