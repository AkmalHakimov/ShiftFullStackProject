package com.example.demo.controller;

import com.example.demo.payload.request.ReqTimeTableDay;
import com.example.demo.service.TimeTableDayService.TimeTableDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timetableday")
@RequiredArgsConstructor
public class TimeTableDayController {

    private final TimeTableDayService timeTableDayService;

    @GetMapping
    public HttpEntity<?> getTimeTableDay(){
        return timeTableDayService.GetStudentTimeTableDay();
    }

    @PutMapping("/absent")
    public HttpEntity<?> editAbsent(@RequestParam Integer id, @RequestBody ReqTimeTableDay reqTimeTableDay){
        return timeTableDayService.EditAbsent(id,reqTimeTableDay);
    }
}
