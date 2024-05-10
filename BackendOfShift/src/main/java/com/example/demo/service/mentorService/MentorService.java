package com.example.demo.service.mentorService;

import com.example.demo.payload.request.ReqMentor;
import com.example.demo.payload.request.ReqUser;
import org.springframework.http.HttpEntity;

public interface MentorService {

    HttpEntity<?> SaveMentor(ReqMentor reqMentor);

    HttpEntity<?> getMentors();

    HttpEntity<?> DelMentor(String mentorId);

    HttpEntity<?> editMentor(String mentorId, ReqMentor reqMentor);
}
