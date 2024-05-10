package com.example.demo.controller;


import com.example.demo.payload.request.ReqMentor;
import com.example.demo.service.mentorService.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mentor")
public class MentorController {

    private final MentorService mentorService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public HttpEntity<?> saveMentor(@RequestBody ReqMentor reqMentor){
        return mentorService.SaveMentor(reqMentor);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public HttpEntity<?> getMentors(){
        return mentorService.getMentors();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public HttpEntity<?> delMentor(@RequestParam String mentorId){
        return mentorService.DelMentor(mentorId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public HttpEntity<?> editMentor(@RequestParam String mentorId,@RequestBody ReqMentor reqMentor){
        return mentorService.editMentor(mentorId,reqMentor);
    }
}
