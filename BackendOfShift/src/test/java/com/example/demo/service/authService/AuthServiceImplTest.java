package com.example.demo.service.authService;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.payload.request.ReqUser;
import com.example.demo.repository.*;
import com.example.demo.service.security.JwtService;
import com.example.demo.service.studentTimeTableService.StudentTimeTableServiceImple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    private AuthServiceImpl underTest;

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
        underTest = new AuthServiceImpl(userRepo,roleRepo,passwordEncoder, authenticationManager,jwtService);
    }

    @Test
    void register() {
        String phone = "123456789";
        String password = "password";
        String firstName = "John";
        String lastName = "Doe";
        int age = 25;
        List<Role> roleUser = List.of(new Role(1,"ROLE_USER"));
        UUID userId = UUID.randomUUID();

        when(roleRepo.findAllByName("ROLE_USER")).thenReturn(roleUser);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");;

        ReqUser reqUser = new ReqUser();
        reqUser.setPhone(phone);
        reqUser.setPassword(password);
        reqUser.setFirstName(firstName);
        reqUser.setLastName(lastName);
        reqUser.setAge(age);

        HttpEntity<?> response = underTest.Register(reqUser);

        verify(roleRepo, times(1)).findAllByName("ROLE_USER");
        verify(passwordEncoder, times(1)).encode(password);
        verify(userRepo, times(1)).save(any(User.class));
        assertEquals("registered✅", response.getBody());
    }

    @Test
    void login() {
        String phone = "123456789";
        String password = "password";
        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        Authentication authentication = new UsernamePasswordAuthenticationToken(phone, password);
        String jwt = "access_token";
        String jwtRefresh = "refresh_token";
        Map<String, Object> map = new HashMap<>();
        map.put("refresh_token", jwtRefresh);
        map.put("access_token", jwt);
        map.put("role", user.getRoles());
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(userRepo.findByPhone(phone)).thenReturn(Optional.of(user));
        when(jwtService.generateJwtToken(user)).thenReturn(jwt);
        when(jwtService.generateJwtRefreshToken(user)).thenReturn(jwtRefresh);
        ReqUser reqUser = new ReqUser();
        reqUser.setPhone(phone);
        reqUser.setPassword(password);
        HttpEntity<?> response = underTest.Login(reqUser);
        verify(authenticationManager, times(1)).authenticate(any(Authentication.class));
        verify(userRepo, times(1)).findByPhone(phone);
        verify(jwtService, times(1)).generateJwtToken(user);
        verify(jwtService, times(1)).generateJwtRefreshToken(user);
        assertEquals(map, response.getBody());
    }

    @Test
    void refreshToken() {
        String refreshToken = "wbd";
        String userId = UUID.randomUUID().toString();
        String accessToken = "wgb";
        User user = new User();
        when(jwtService.extractSubjectFromJwt(refreshToken)).thenReturn(userId);
        when(userRepo.findById(UUID.fromString(userId))).thenReturn(java.util.Optional.of(user));
        when(jwtService.generateJwtToken(user)).thenReturn(accessToken);
        HttpEntity<?> response = underTest.refreshToken(refreshToken);
        verify(jwtService, times(1)).extractSubjectFromJwt(refreshToken);
        verify(userRepo, times(1)).findById(UUID.fromString(userId));
        verify(jwtService, times(1)).generateJwtToken(user);
        assertEquals(accessToken, response.getBody());
    }
}