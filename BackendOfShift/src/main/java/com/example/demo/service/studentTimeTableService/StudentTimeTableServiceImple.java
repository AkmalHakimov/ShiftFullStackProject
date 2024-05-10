package com.example.demo.service.studentTimeTableService;


import com.example.demo.entity.*;
import com.example.demo.payload.request.ReqStudentTimeTable;
import com.example.demo.repository.StudentTimeTableRepo;
import com.example.demo.repository.TimeTableDayRepo;
import com.example.demo.repository.TimeTableRepo;
import com.example.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentTimeTableServiceImple implements StudentTimeTableService {

    private final StudentTimeTableRepo studentTimeTableRepo;
    private final TimeTableRepo timeTableRepo;
    private final TimeTableDayRepo timeTableDayRepo;
    private final UserRepo userRepo;

    @Override
    public HttpEntity<?> saveStudentTimeTable(ReqStudentTimeTable reqStudentTimeTable) {
        try {
            TimeTable timeTable = timeTableRepo.findById(reqStudentTimeTable.getTimeTableId()).orElseThrow();
            if(timeTable.getDates().size()==0){
                return ResponseEntity.status(500).body("An error has occurred");
            }
            User user = userRepo.findById(reqStudentTimeTable.getStudentId()).orElseThrow();
            TimeTableStudent timeTableStudent = new TimeTableStudent(
                    UUID.randomUUID(),
                    reqStudentTimeTable.getPrice(),
                    null,
                    null,
                    timeTable,
                    user
            );
            TimeTableStudent save = studentTimeTableRepo.save(timeTableStudent);
            SaveAllTimeTableDay(save);
            return ResponseEntity.ok("Saved✅");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    public void SaveAllTimeTableDay(TimeTableStudent save) {
        List<TimeTableDay> timeTableDays = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
           TimeTableDay timeTableDay = new TimeTableDay(
                   null,
                   false,
                   null,
                   save
           );
           timeTableDays.add(timeTableDay);
        }
        timeTableDayRepo.saveAll(timeTableDays);
    }

    @Override
    public HttpEntity<?> getStudentTimeTable(UUID id) {
        try {
            List<TimeTableStudent> students = studentTimeTableRepo.findAllByTimeTableId(id);
            return ResponseEntity.ok(students);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    @Override
    public HttpEntity<?> delStudentTimeTable(UUID id) {
        try {
            studentTimeTableRepo.deleteById(id);
            return ResponseEntity.ok("Deleted✅");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    @Override
    public HttpEntity<?> editStudentTimeTable(UUID id, ReqStudentTimeTable reqStudentTimeTable) {
        try {
//            TimeTableStudent timeTableStudent = studentTimeTableRepo.findById(id).orElseThrow();
            TimeTable timeTable = timeTableRepo.findById(reqStudentTimeTable.getTimeTableId()).orElseThrow();
            User user = userRepo.findById(reqStudentTimeTable.getStudentId()).orElseThrow();
            TimeTableStudent timeTableStudent1 = new TimeTableStudent(
                    id,
                    reqStudentTimeTable.getPrice(),
                    null,
                    null,
                    timeTable,
                    user
            );
            studentTimeTableRepo.save(timeTableStudent1);
            return ResponseEntity.ok("edited✅");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }
}
