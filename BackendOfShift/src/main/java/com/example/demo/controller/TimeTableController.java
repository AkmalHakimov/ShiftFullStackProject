package com.example.demo.controller;

import com.example.demo.payload.request.ReqTimeTable;
import com.example.demo.service.timeTableService.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/timetable")
@RequiredArgsConstructor
public class TimeTableController {

    private final TimeTableService timeTableService;

    @PostMapping
    public HttpEntity<?> SaveTimeTable(@RequestBody ReqTimeTable reqTimeTable){
        return timeTableService.SaveTimeTable(reqTimeTable);
    }

    @PostMapping("/all")
    public HttpEntity<?> SaveAllTimeTable(@RequestBody ReqTimeTable reqTimeTable){
        return timeTableService.SaveAllTimeTable(reqTimeTable);
    }

    @GetMapping
    public HttpEntity<?> getTimeTables(@RequestParam(defaultValue = "") UUID groupId){
            return timeTableService.GetTimeTable(groupId);
    }

    @GetMapping("/one")
    public HttpEntity<?> getOneTimeTable(@RequestParam(defaultValue = "") UUID id){
            return timeTableService.GetTimeTableById(id);
    }

    @GetMapping("/last")
    public HttpEntity<?> getLastTimeTable(@RequestParam(defaultValue = "") UUID groupId){
        return timeTableService.GetLastTimeTable(groupId);
    }

    @DeleteMapping
    public HttpEntity<?> delTimeTable(@RequestParam UUID id){
        return timeTableService.delTimeTable(id);
    }

    @PutMapping
    public HttpEntity<?> editTimeTable(@RequestParam UUID id, @RequestBody ReqTimeTable reqTimeTable){
        return timeTableService.editTimeTable(id,reqTimeTable);
    }

    @PutMapping("/startTime")
    public HttpEntity<?> startTimeTable(@RequestParam UUID id,@RequestBody ReqTimeTable reqTimeTable){
        return timeTableService.startTimeTable(id,reqTimeTable);
    }

    @PutMapping("/complete")
    public HttpEntity<?> completeTimeTable(@RequestParam UUID id){
        return timeTableService.completeTable(id);
    }
}
