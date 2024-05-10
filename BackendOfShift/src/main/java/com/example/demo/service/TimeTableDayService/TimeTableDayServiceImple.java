package com.example.demo.service.TimeTableDayService;

import com.example.demo.entity.*;
import com.example.demo.payload.request.ReqTimeTable;
import com.example.demo.payload.request.ReqTimeTableDay;
import com.example.demo.payload.response.ResTimeTable;
import com.example.demo.repository.GroupRepo;
import com.example.demo.repository.MentorRepo;
import com.example.demo.repository.TimeTableDayRepo;
import com.example.demo.repository.TimeTableRepo;
import com.example.demo.service.timeTableService.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TimeTableDayServiceImple implements TimeTableDayService {

    private final TimeTableDayRepo timeTableDayRepo;

    @Override
    public HttpEntity<?> EditAbsent(Integer id, ReqTimeTableDay reqTimeTableDay) {
        try {
            TimeTableDay oldTimeTableDay = timeTableDayRepo.findById(id).orElseThrow();
            oldTimeTableDay.setAbsent(reqTimeTableDay.getAbsent());
            timeTableDayRepo.save(oldTimeTableDay);
            return ResponseEntity.ok("edited");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }

    }

    @Override
    public HttpEntity<?> GetStudentTimeTableDay() {
        try {
            List<TimeTableDay> timeTableDays = timeTableDayRepo.findAllOrderByIdAsc();
            return ResponseEntity.ok(timeTableDays);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }
}
