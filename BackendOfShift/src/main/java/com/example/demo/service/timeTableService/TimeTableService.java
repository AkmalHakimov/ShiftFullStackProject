package com.example.demo.service.timeTableService;

import com.example.demo.payload.request.ReqTimeTable;
import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface TimeTableService {
    HttpEntity<?> SaveTimeTable(ReqTimeTable reqTimeTable);

    HttpEntity<?> SaveAllTimeTable(ReqTimeTable reqTimeTable);

    HttpEntity<?> GetTimeTable(UUID groupId);
    HttpEntity<?> GetTimeTableById(UUID id);

    HttpEntity<?> delTimeTable(UUID id);

    HttpEntity<?> editTimeTable(UUID id, ReqTimeTable reqTimeTable);

    HttpEntity<?> GetLastTimeTable(UUID groupId);

    HttpEntity<?> startTimeTable(UUID id, ReqTimeTable reqTimeTable);

    HttpEntity<?> completeTable(UUID id);
}
