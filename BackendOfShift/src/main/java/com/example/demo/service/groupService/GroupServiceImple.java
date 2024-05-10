package com.example.demo.service.groupService;

import com.example.demo.entity.Group;
import com.example.demo.entity.Room;
import com.example.demo.payload.request.ReqGroup;
import com.example.demo.payload.response.ResGroup;
import com.example.demo.projection.GroupProjection;
import com.example.demo.repository.GroupRepo;
import com.example.demo.repository.RoomRepo;
import com.example.demo.repository.TimeTableRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceImple implements GroupService {

    private final GroupRepo groupRepo;
    private final RoomRepo roomRepo;
    private final TimeTableRepo timeTableRepo;

    @Override
    public HttpEntity<?> getEvenGroups() {
        try {
            List<GroupProjection> evenGroups = groupRepo.findAllGroupByDayTypeEven();
            return ResponseEntity.ok(evenGroups);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    @Override
    public HttpEntity<?> getOddGroups() {
        try {
            List<GroupProjection> oddGroups = groupRepo.findAllGroupByDayTypeOdd();
            return ResponseEntity.ok(oddGroups);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    @Override
    public HttpEntity<?> getAllGroups() {
        try {
            List<GroupProjection> allGroups = groupRepo.findAllGroupByDayTypeAll();
            return ResponseEntity.ok(allGroups);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

//    private static List<ResGroup> getResGroups(List<Group> allGroups) {
//        List<ResGroup> resGroups = new ArrayList<>();
//        for (Group allGroup : allGroups) {
//            ResGroup resGroup = new ResGroup(
//                    allGroup.getId(),
//                    allGroup.getName(),
//                    allGroup.getDayType(),
//                    allGroup.getStartTime(),
//                    allGroup.getEndTime(),
//                    allGroup.getRoom().getName(),
//                    allGroup.getRoom().getId()
//            );
//            resGroups.add(resGroup);
//        }
//        return resGroups;
//    }

    @Override
    public HttpEntity<?> getGroups() {
        try {
            List<GroupProjection> groups = groupRepo.findAllGroups();
            return ResponseEntity.ok(groups);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    @Override
    public HttpEntity<?> getOneGroup(UUID groupId) {
        try {
            GroupProjection group = groupRepo.findGroupById(groupId);
            return ResponseEntity.ok(group);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    @Override
    public HttpEntity<?> DelGroup(UUID groupId) {
        timeTableRepo.deleteAllByGroup_Id(groupId);
        groupRepo.deleteById(groupId);
        return ResponseEntity.ok("deleted✅");
    }

    @Override
    public HttpEntity<?> EditGroup(UUID groupId, ReqGroup reqGroup) {
            validateTimes(reqGroup);

        Room room = roomRepo.findById(reqGroup.getRoomId()).orElseThrow();

        Group group = groupRepo.findById(groupId).orElseThrow();

        group.setName(reqGroup.getName());
        group.setDayType(reqGroup.getDayType());
        group.setEndTime(reqGroup.getEndTime());
        group.setStartTime(reqGroup.getStartTime());
        group.setRoom(room);
        groupRepo.save(group);
        return ResponseEntity.ok("edited");
    }


    @Override
    public HttpEntity<?> saveGroup(ReqGroup reqGroup) {
        validateTimes(reqGroup);
        Room room = roomRepo.findById(reqGroup.getRoomId()).orElseThrow();
        Group group = Group.builder()
                .id(UUID.randomUUID())
                .name(reqGroup.getName())
                .dayType(reqGroup.getDayType())
                .startTime(reqGroup.getStartTime())
                .endTime(reqGroup.getEndTime())
                .room(room)
                .build();
        Group save = groupRepo.save(group);
        return ResponseEntity.ok(save);

    }


    @Override
    public HttpEntity<?> getRoom() {
        List<Room> all = roomRepo.findAll();
        return ResponseEntity.ok(all);
    }

    private void validateTimes(ReqGroup reqGroup) {
        LocalTime startTime = reqGroup.getStartTime();
        LocalTime endTime = reqGroup.getEndTime();

        if (
                startTime.isBefore(LocalTime.of(9, 0))
                        || endTime.isAfter(LocalTime.of(20, 0))
        ) throw new RuntimeException("start time and end time should be between 9:00 and 20:00");

        if (endTime.isBefore(startTime))
            throw new RuntimeException("start time can't be before end time");

        if (startTime.getMinute() % 30 != 0 || endTime.getMinute() % 30 != 0)
            throw new RuntimeException("start and end time minutes should be either 30 or 00");
        if (groupRepo.getIfRoomIsTaken(reqGroup.getDayType(),reqGroup.getStartTime(),reqGroup.getEndTime(),reqGroup.getRoomId()).isPresent())
            throw new RuntimeException("Room is taken");
//        if (groupRepo.checkIfMentorIsFree().isPresent()) throw new RuntimeException("teacher is busy");
    }
}
