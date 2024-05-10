package com.example.demo.service.userService;

import com.example.demo.entity.Group;
import com.example.demo.entity.Role;
import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.payload.request.ReqGroup;
import com.example.demo.payload.request.ReqUser;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

class UserServiceImplTest {

    private UserService userService;

    @Mock
    UserRepo userRepo;

    @Mock
    RoleRepo roleRepo;

    @Mock
    PasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepo, roleRepo, passwordEncoder);
    }

    @Test
    void getUsers() {
        List<User> users = List.of(new User(), new User());
        when(userRepo.getByRoleUser()).thenReturn(users);
        HttpEntity<?> res = userService.getUsers();
        List<User> resStudents = (List<User>) res.getBody();
        Assertions.assertEquals(resStudents, users);
//        Object body = (List<>)res.getBody();
    }

    @Test
    void saveUser() {
        List<Role> roleList = List.of(new Role(1,"ROLE_USER"));
        when(roleRepo.findAllByName("ROLE_USER")).thenReturn(roleList);
        String encodedPassword = "1";
        when(passwordEncoder.encode(encodedPassword)).thenReturn(encodedPassword);
        ReqUser reqUser = ReqUser.builder()
                .age(12)
                .phone("555")
                .description("1")
                .lastName("1")
                .firstName("1")
                .password(encodedPassword)
                .build();
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        HttpEntity<?> httpEntity = userService.SaveUser(reqUser);
        Mockito.verify(userRepo,Mockito.times(1)).save(captor.capture());
        User user = captor.getValue();
        assertEquals(user.getPhone(),reqUser.getPhone());
        assertEquals(user.getAge(),reqUser.getAge());
        assertEquals(user.getLastName(),reqUser.getLastName());
        assertEquals(user.getFirstName(),reqUser.getFirstName());
        assertEquals(user.getDescription(),reqUser.getDescription());
        assertEquals(user.getPassword(),reqUser.getPassword());
        assertEquals(roleList,user.getRoles());
    }

    @Test
    public void itShouldThrowException(){
        List<Role> roleList = Collections.singletonList(new Role(1, "ROLE_USER"));
        when(roleRepo.findAllByName("ROLE_USER")).thenReturn(roleList);
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode("password")).thenReturn(encodedPassword);
        when(userRepo.save(any(User.class))).thenThrow(new RuntimeException("Save failed"));
        ReqUser reqUser = ReqUser.builder()
                .phone("555")
                .password("password")
                .firstName("First")
                .lastName("Last")
                .age(12)
                .description("Description")
                .build();
        HttpEntity<?> httpEntity = userService.SaveUser(reqUser);
        verify(roleRepo).findAllByName("ROLE_USER");
        verify(userRepo).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertEquals(reqUser.getPhone(), capturedUser.getPhone());
        assertEquals(encodedPassword, capturedUser.getPassword());
        assertEquals(reqUser.getFirstName(), capturedUser.getFirstName());
        assertEquals(reqUser.getLastName(), capturedUser.getLastName());
        assertEquals(reqUser.getAge(), capturedUser.getAge());
        assertEquals(reqUser.getDescription(), capturedUser.getDescription());
        assertEquals(roleList, capturedUser.getRoles());
        assertEquals(ResponseEntity.status(500).body("An error has occurred"), httpEntity);
    }


    @Test
    void delUser() {
        UUID id = UUID.randomUUID();
        HttpEntity<?> httpEntity = userService.DelUser(id.toString());
        assertEquals(httpEntity.getBody(),"deleted✅");
        verify(userRepo,Mockito.times(1)).deleteById(id);
    }

    @Test
    void delUser_Error() {
        String userId = UUID.randomUUID().toString();
        Mockito.doThrow(new RuntimeException("Delete failed")).when(userRepo).deleteById(Mockito.any(UUID.class));
        HttpEntity<?> httpEntity = userService.DelUser(userId);
        verify(userRepo).deleteById(Mockito.any(UUID.class));
        assertEquals(ResponseEntity.status(500).body("An error has occurred"), httpEntity);
    }


    @Test
    void editStudent() {
        String userId = "2dbba8f3-edb0-4229-9b81-6072dd8ea505";
        String phone = "555";
        User existingUser = new User();
        existingUser.setId(UUID.fromString(userId));
        existingUser.setPhone(phone);
        when(userRepo.findByPhone(phone)).thenReturn(Optional.of(existingUser));
        ReqUser reqUser = ReqUser.builder()
                .phone(phone)
                .firstName("First")
                .lastName("Last")
                .age(12)
                .description("Description")
                .build();
        HttpEntity<?> httpEntity = userService.editStudent(userId, reqUser);
        verify(userRepo).findByPhone(phone);
        verify(userRepo).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals(UUID.fromString(userId), savedUser.getId());
        assertEquals(phone, savedUser.getPhone());
        assertEquals(existingUser.getPassword(), savedUser.getPassword());
        assertEquals(reqUser.getFirstName(), savedUser.getFirstName());
        assertEquals(reqUser.getLastName(), savedUser.getLastName());
        assertEquals(reqUser.getAge(), savedUser.getAge());
        assertEquals(reqUser.getDescription(), savedUser.getDescription());
        assertEquals(existingUser.getRoles(), savedUser.getRoles());
        assertEquals(ResponseEntity.ok("edited✅"), httpEntity);
    }

    @Test
    void editStudent_UserNotFound() {
        String userId = "2dbba8f3-edb0-4229-9b81-6072dd8ea505";
        String phone = "555";
        when(userRepo.findByPhone(phone)).thenReturn(Optional.empty());
        ReqUser reqUser = ReqUser.builder()
                .phone(phone)
                .firstName("First")
                .lastName("Last")
                .age(12)
                .description("Description")
                .build();
        HttpEntity<?> httpEntity = userService.editStudent(userId, reqUser);
        verify(userRepo).findByPhone(phone);
        verify(userRepo, Mockito.never()).save(Mockito.any(User.class));
        assertEquals(ResponseEntity.status(500).body("An error has occurred"), httpEntity);
    }
}