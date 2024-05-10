package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.payload.request.ReqGroup;
import com.example.demo.service.groupService.GroupService;
import com.example.demo.service.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public HttpEntity<?> saveGroup(@RequestBody ReqGroup reqGroup){
        return groupService.saveGroup(reqGroup);
    }

    @GetMapping("/evens")
    public HttpEntity<?> getEvenGroups(){
        return groupService.getEvenGroups();
    }

    @GetMapping("/odds")
    public HttpEntity<?> getOddGroups(){
        return groupService.getOddGroups();
    }

    @GetMapping("/all")
    public HttpEntity<?> getAllGroups(){
        return groupService.getAllGroups();
    }

    @GetMapping("/one/{groupId}")
    public HttpEntity<?> getOneGroup(@PathVariable UUID groupId){
        return groupService.getOneGroup(groupId);
    }

    @GetMapping
    public HttpEntity<?> getGroups(@CurrentUser User user){
        return groupService.getGroups();
    }

    @DeleteMapping
    public HttpEntity<?> delGroup(@RequestParam UUID groupId){
        return groupService.DelGroup(groupId);
    }

    @PutMapping
    public HttpEntity<?> editGroup(@RequestParam UUID groupId,@RequestBody ReqGroup reqGroup){
        return groupService.EditGroup(groupId,reqGroup);
    }

    @GetMapping("/room")
    public HttpEntity<?> getRooms(){
        return groupService.getRoom();
    }
}
