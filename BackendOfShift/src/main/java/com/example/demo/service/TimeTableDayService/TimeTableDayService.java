package com.example.demo.service.TimeTableDayService;

import com.example.demo.payload.request.ReqTimeTable;
import com.example.demo.payload.request.ReqTimeTableDay;
import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface TimeTableDayService {

    HttpEntity<?> GetStudentTimeTableDay();

    HttpEntity<?> EditAbsent(Integer id, ReqTimeTableDay reqTimeTableDay);
}
