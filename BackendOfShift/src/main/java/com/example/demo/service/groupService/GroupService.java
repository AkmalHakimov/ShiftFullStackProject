package com.example.demo.service.groupService;

import com.example.demo.payload.request.ReqGroup;
import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface GroupService {

    HttpEntity<?> saveGroup(ReqGroup reqGroup);

    HttpEntity<?> getEvenGroups();

    public HttpEntity<?> getAllGroups();

    public HttpEntity<?> getOddGroups();

    public HttpEntity<?> getGroups();

    HttpEntity<?> DelGroup(UUID groupId);

    HttpEntity<?> EditGroup(UUID groupId, ReqGroup reqGroup);

    HttpEntity<?> getRoom();

    HttpEntity<?> getOneGroup(UUID groupId);
}
