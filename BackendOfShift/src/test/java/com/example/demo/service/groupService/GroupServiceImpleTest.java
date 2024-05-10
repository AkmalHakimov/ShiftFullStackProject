package com.example.demo.service.groupService;

import com.example.demo.entity.DayType;
import com.example.demo.entity.Group;
import com.example.demo.entity.Room;
import com.example.demo.payload.request.ReqGroup;
import com.example.demo.payload.response.ResGroup;
import com.example.demo.repository.GroupRepo;
import com.example.demo.repository.RoomRepo;
import com.example.demo.repository.TimeTableRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GroupServiceImpleTest {

    private GroupServiceImple underTest;

    @Mock
    GroupRepo groupRepo;

    @Mock
    TimeTableRepo timeTableRepo;

    @Mock
    RoomRepo roomRepo;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new GroupServiceImple(groupRepo,roomRepo,timeTableRepo);
    }

    @Test
    void saveGroup() {
        UUID id = UUID.randomUUID();
        LocalTime time = LocalTime.now();
        var room = Room.builder()
                .id(1)
                .name("Goo")
                .build();
        Mockito.when(roomRepo.save(room)).thenReturn(room);
        when(roomRepo.findById(anyInt())).thenReturn(Optional.of(room));
        ReqGroup reqGroup = ReqGroup.builder()
                .name("G33")
                .startTime(time)
                .endTime(time)
                .roomId(1)
                .dayType(DayType.EVEN)
                .build();
        ArgumentCaptor<Group> captor = ArgumentCaptor.forClass(Group.class);
        HttpEntity<?> responses = underTest.saveGroup(reqGroup);
            Mockito.verify(groupRepo,Mockito.times(1)).save(captor.capture());
        Group group = captor.getValue();
        assertEquals(group.getRoom().getId(),reqGroup.getRoomId());
        assertEquals(group.getName(),reqGroup.getName());
        assertEquals(group.getStartTime(),reqGroup.getStartTime());
        assertEquals(group.getEndTime(),reqGroup.getEndTime());
        assertEquals(group.getDayType(),reqGroup.getDayType());
        Assertions.assertEquals(responses,ResponseEntity.ok("saved"));
    }

    @Test
    public void itShouldThrowException(){
        ReqGroup reqGroup = new ReqGroup();
        reqGroup.setRoomId(1);
        when(roomRepo.findById(1)).thenThrow(new RuntimeException());
        HttpEntity<?> httpEntity = underTest.saveGroup(reqGroup);
        assertEquals("An error has occurred", httpEntity.getBody());
        verify(roomRepo, times(1)).findById(1);
        verify(groupRepo, never()).save(any(Group.class));
    }

    @Test
    void delGroup() {
        UUID id = UUID.randomUUID();
        HttpEntity<?> httpEntity = underTest.DelGroup(id);
        assertEquals(httpEntity.getBody(),"deleted✅");
        verify(groupRepo,Mockito.times(1)).deleteById(id);
        verify(timeTableRepo,Mockito.times(1)).deleteAllByGroup_Id(id);
    }

    @Test
    void testDelGroup_Exception() {
        UUID groupId = UUID.randomUUID();
        doThrow(new RuntimeException()).when(timeTableRepo).deleteAllByGroup_Id(any(UUID.class));
        HttpEntity<?> response = underTest.DelGroup(groupId);
        assertEquals(ResponseEntity.status(500).body("An error has occurred"), response);
        verify(timeTableRepo, times(1)).deleteAllByGroup_Id(groupId);
        verify(groupRepo, never()).deleteById(any(UUID.class));
    }

    @Test
    void editGroup() {
        Room room = new Room(
                1,
                "123"
        );
        Optional<Room> existingRoom = Optional.of(room);
        Mockito.when(roomRepo.findById(1)).thenReturn(existingRoom);
        UUID id = UUID.randomUUID();
        LocalTime time = LocalTime.now();
        ReqGroup reqGroup = ReqGroup.builder()
                .name("G33")
                .startTime(time)
                .endTime(time)
                .roomId(1)
                .dayType(DayType.EVEN)
                .build();
        Group group = Group.builder()
                .id(id)
                .name("G33")
                .startTime(time)
                .endTime(time)
//                .dayType("EVEN")
                .room(room)
                .build();
        Mockito.when(groupRepo.save(group)).thenReturn(group);
//        HttpEntity<?> httpEntity = underTest.EditGroup(id.toString(), reqGroup);
        Mockito.verify(groupRepo,Mockito.times(1)).save(group);
//        Assertions.assertEquals(httpEntity.getBody(),"edited");
    }

    @Test
    void getEvenGroups() {
        List<Group> groupList = CreateGroupList();
        List<ResGroup> resGroups = CreateResList(groupList);
//        Mockito.when(groupRepo.findAllGroupByDayTypeEven()).thenReturn(groupList);
        HttpEntity<?> httpEntity = underTest.getEvenGroups();
        List<ResGroup> body = (List<ResGroup>)httpEntity.getBody();
        assertEquals(body,resGroups);
    }

    @Test
    void getOddGroups() {
        List<Group> groupList = CreateGroupList();
        List<ResGroup> resGroups = CreateResList(groupList);
//        Mockito.when(groupRepo.findAllGroupByDayTypeOdd()).thenReturn(groupList);
        HttpEntity<?> httpEntity = underTest.getOddGroups();
        List<ResGroup> body = (List<ResGroup>)httpEntity.getBody();
        assertEquals(body,resGroups);
    }

    @Test
    void getAllGroups() {
        List<Group> groupList = CreateGroupList();
        List<ResGroup> resGroups = CreateResList(groupList);
//        Mockito.when(groupRepo.findAllGroupByDayTypeAll()).thenReturn(groupList);
        HttpEntity<?> httpEntity = underTest.getAllGroups();
        List<ResGroup> body = (List<ResGroup>)httpEntity.getBody();
        assertEquals(body,resGroups);
    }

    private static List<ResGroup> CreateResList(List<Group> groupList) {
        List<ResGroup> resGroups = new ArrayList<>();
        for (Group group : groupList) {
            resGroups.add(new ResGroup(
                    group.getId(),
                    group.getName(),
                    group.getDayType().name(),
                    group.getStartTime(),
                    group.getEndTime(),
                    group.getRoom().getName(),
                    group.getRoom().getId()
            ));
        }
        return resGroups;
    }

    private List<Group> CreateGroupList() {
        Room room = new Room(
                1,
                "123"
        );

        when(roomRepo.save(room)).thenReturn(room);
        Room save = roomRepo.save(room);
        List<Group> groupList = List.of(new Group(
                UUID.randomUUID(),
                "",
                DayType.ALL,
                LocalTime.now(),
                LocalTime.now(),
                save
        ),new Group(
                UUID.randomUUID(),
                "",
                DayType.ODD,
                LocalTime.now(),
                LocalTime.now(),
                save
        ));
        return groupList;
    }

    @Test
    void getGroups() {
        List<Group> groupList = CreateGroupList();
        List<ResGroup> resGroups = CreateResList(groupList);
//        Mockito.when(groupRepo.findAll()).thenReturn(groupList);
        HttpEntity<?> httpEntity = underTest.getGroups();
        List<ResGroup> body = (List<ResGroup>)httpEntity.getBody();
        assertEquals(body,resGroups);
    }

    @Test
    void getRoom() {
        List<Room> roomList = List.of(new Room(),new Room());
        Mockito.when(roomRepo.findAll()).thenReturn(roomList);
        HttpEntity<?> room = underTest.getRoom();
        List<Room> body = (List<Room>)room.getBody();
        Assertions.assertEquals(body,roomList);
    }
}