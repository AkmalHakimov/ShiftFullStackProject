package com.example.demo.service.mentorService;

import com.example.demo.entity.Mentor;
import com.example.demo.entity.Role;
import com.example.demo.payload.request.ReqMentor;
import com.example.demo.payload.response.ResMentor;
import com.example.demo.projection.MentorProjection;
import com.example.demo.repository.MentorRepo;
import com.example.demo.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {
    private final RoleRepo roleRepo;
    private final MentorRepo mentorRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public HttpEntity<?> SaveMentor(ReqMentor reqMentor) {
            try {
                List<Role> roleUser = roleRepo.findAllByName("ROLE_MENTOR");
                Mentor mentor = new Mentor(
                        UUID.randomUUID(),
                        reqMentor.getPhone(),
                        passwordEncoder.encode(reqMentor.getPassword()),
                        reqMentor.getFirstName(),
                        reqMentor.getLastName(),
                         reqMentor.getBirthDate(),
                        roleUser
                );
                mentorRepo.save(mentor);
                return ResponseEntity.ok("saved✅");
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.status(500).body("An error has occurred");
            }
    }

    @Override
    public HttpEntity<?> getMentors() {
        try {
            List<Mentor> mentors = mentorRepo.findAll();
            List<ResMentor> mentorList = new ArrayList<>();
            for (Mentor mentor : mentors) {
                ResMentor resMentor = new ResMentor(
                        mentor.getId(),
                        mentor.getFirstName(),
                        mentor.getLastName(),
                        mentor.getBirthDate().toString(),
                        mentor.getPhone()
                );
                mentorList.add(resMentor);
            }
            return ResponseEntity.ok(mentorList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    @Override
    public HttpEntity<?> DelMentor(String mentorId) {
        try {
            mentorRepo.deleteById(UUID.fromString(mentorId));
            return ResponseEntity.ok("deleted✅");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    @Override
    public HttpEntity<?> editMentor(String mentorId, ReqMentor reqMentor) {
        try {
            Mentor oldMentor = mentorRepo.findById(UUID.fromString(mentorId)).orElseThrow();
            Mentor mentor = new Mentor(
                    UUID.fromString(mentorId),
                    reqMentor.getPhone(),
                    oldMentor.getPassword(),
                    reqMentor.getFirstName(),
                    reqMentor.getLastName(),
                    reqMentor.getBirthDate(),
                    oldMentor.getRoles()
            );
            mentorRepo.save(mentor);
            return ResponseEntity.ok("edited✅");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }
}
