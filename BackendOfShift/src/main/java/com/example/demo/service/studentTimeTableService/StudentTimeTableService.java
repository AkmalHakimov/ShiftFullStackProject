package com.example.demo.service.studentTimeTableService;

import com.example.demo.payload.request.ReqStudentTimeTable;
import com.example.demo.payload.request.ReqTimeTable;
import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface StudentTimeTableService {

    HttpEntity<?> saveStudentTimeTable(ReqStudentTimeTable reqStudentTimeTable);

    HttpEntity<?> getStudentTimeTable(UUID id);

    HttpEntity<?> delStudentTimeTable(UUID id);

    HttpEntity<?> editStudentTimeTable(UUID id, ReqStudentTimeTable reqStudentTimeTable);
}
