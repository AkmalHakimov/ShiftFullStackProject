package com.example.demo.controller;

import com.example.demo.payload.request.ReqStudentTimeTable;
import com.example.demo.service.studentTimeTableService.StudentTimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/studenttimetable")
@RequiredArgsConstructor
public class StudentTimeTableController {

    private final StudentTimeTableService studentTimeTableService;

    @PostMapping
    public HttpEntity<?> saveStudentTimeTable(@RequestBody ReqStudentTimeTable reqStudentTimeTable){
        return studentTimeTableService.saveStudentTimeTable(reqStudentTimeTable);
    }

    @GetMapping
    public HttpEntity<?> getStudentTimeTables(@RequestParam UUID id){
        return studentTimeTableService.getStudentTimeTable(id);
    }

    @DeleteMapping
    public HttpEntity<?> delStudentTimeTable(@RequestParam UUID id){
        return studentTimeTableService.delStudentTimeTable(id);
    }

    @PutMapping
    public HttpEntity<?> editStudentTimeTable(@RequestParam UUID id,@RequestBody ReqStudentTimeTable reqStudentTimeTable){
        return studentTimeTableService.editStudentTimeTable(id,reqStudentTimeTable);
    }
}
