package com.example.demo.service.mentorService;

import com.example.demo.entity.Mentor;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.payload.request.ReqMentor;
import com.example.demo.payload.request.ReqUser;
import com.example.demo.payload.response.ResMentor;
import com.example.demo.repository.*;
import com.example.demo.service.authService.AuthServiceImpl;
import com.example.demo.service.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MentorServiceImplTest {

    private MentorServiceImpl underTest;

    @Mock
    private UserRepo userRepo;

    @Mock
    private StudentTimeTableRepo studentTimeTableRepo;

    @Mock
    TimeTableRepo timeTableRepo;

    @Mock
    MentorRepo mentorRepo;

    @Mock
    GroupRepo groupRepo;
    @Mock
    RoleRepo roleRepo;

    @Mock
    PasswordEncoder passwordEncoder;


    @Mock
    TimeTableDayRepo timeTableDayRepo;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtService jwtService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new MentorServiceImpl(roleRepo,mentorRepo,passwordEncoder);
    }

    @Test
    void saveMentor() {
        String phone = "123456789";
        String password = "password";
        String firstName = "John";
        String lastName = "Doe";
        LocalDate birthDate = LocalDate.now();
        List<Role> roleUser = List.of(new Role(1,"ROLE_MENTOR"));
        UUID mentorId = UUID.randomUUID();
        when(roleRepo.findAllByName("ROLE_MENTOR")).thenReturn(roleUser);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        ReqMentor reqMentor = new ReqMentor();
        reqMentor.setPhone(phone);
        reqMentor.setPassword(password);
        reqMentor.setFirstName(firstName);
        reqMentor.setLastName(lastName);
//        reqMentor.setBirthDate(birthDate);

        HttpEntity<?> response = underTest.SaveMentor(reqMentor);

        verify(roleRepo, times(1)).findAllByName("ROLE_MENTOR");
        verify(passwordEncoder, times(1)).encode(password);
        verify(mentorRepo, times(1)).save(any(Mentor.class));
        assertEquals("saved✅", response.getBody());
    }

    @Test
    void getMentors() {
        Mentor mentor1 = new Mentor();
        mentor1.setId(UUID.randomUUID());
        mentor1.setFirstName("John");
        mentor1.setLastName("Doe");
        mentor1.setBirthDate( new Date(10, 15, 1990));
        mentor1.setPhone("123456789");

        Mentor mentor2 = new Mentor();
        mentor2.setId(UUID.randomUUID());
        mentor2.setFirstName("Jane");
        mentor2.setLastName("Smith");
        mentor2.setBirthDate( new Date(10, 15, 1990));
        mentor2.setPhone("987654321");

        List<Mentor> mentors = new ArrayList<>();
        mentors.add(mentor1);
        mentors.add(mentor2);

        when(mentorRepo.findAll()).thenReturn(mentors);

        HttpEntity<?> response = underTest.getMentors();

        verify(mentorRepo, times(1)).findAll();

        List<ResMentor> mentorList = (List<ResMentor>) response.getBody();
        assertEquals(2, mentorList.size());

        ResMentor resMentor1 = mentorList.get(0);
        assertEquals(mentor1.getId(), resMentor1.getId());
        assertEquals(mentor1.getFirstName(), resMentor1.getFirstName());
        assertEquals(mentor1.getLastName(), resMentor1.getLastName());
        assertEquals(mentor1.getBirthDate().toString(), resMentor1.getBirthDate());
        assertEquals(mentor1.getPhone(), resMentor1.getPhone());

        ResMentor resMentor2 = mentorList.get(1);
        assertEquals(mentor2.getId(), resMentor2.getId());
        assertEquals(mentor2.getFirstName(), resMentor2.getFirstName());
        assertEquals(mentor2.getLastName(), resMentor2.getLastName());
        assertEquals(mentor2.getBirthDate().toString(), resMentor2.getBirthDate());
        assertEquals(mentor2.getPhone(), resMentor2.getPhone());
    }

    @Test
    void delMentor() {
        UUID mentorId = UUID.randomUUID();

        HttpEntity<?> response = underTest.DelMentor(mentorId.toString());

        verify(mentorRepo, times(1)).deleteById(mentorId);
        assertEquals("deleted✅", response.getBody());
    }

    @Test
    void editMentor() {
        UUID mentorId = UUID.randomUUID();
        String phone = "123456789";
        String firstName = "John";
        String lastName = "Doe";
        Mentor oldMentor = new Mentor();
        oldMentor.setId(mentorId);
        oldMentor.setPassword("oldPassword");
        oldMentor.setRoles(Collections.emptyList());

        Mentor updatedMentor = new Mentor();
        updatedMentor.setId(mentorId);
        updatedMentor.setPhone(phone);
        updatedMentor.setPassword(oldMentor.getPassword());
        updatedMentor.setFirstName(firstName);
        updatedMentor.setLastName(lastName);
        updatedMentor.setBirthDate( new Date(10, 15, 1990));
        updatedMentor.setRoles(oldMentor.getRoles());

        when(mentorRepo.findById(mentorId)).thenReturn(Optional.of(oldMentor));
        when(mentorRepo.save(updatedMentor)).thenReturn(updatedMentor);

        ReqMentor reqMentor = new ReqMentor();
        reqMentor.setPhone(phone);
        reqMentor.setFirstName(firstName);
        reqMentor.setLastName(lastName);
        reqMentor.setBirthDate( new Date(10, 15, 1990));

        HttpEntity<?> response = underTest.editMentor(mentorId.toString(), reqMentor);

        verify(mentorRepo, times(1)).findById(mentorId);
        verify(mentorRepo, times(1)).save(updatedMentor);
        assertEquals("edited✅", response.getBody());
    }
}