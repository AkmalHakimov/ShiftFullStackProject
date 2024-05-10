package com.example.demo.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

public interface MentorProjection {

    UUID getId();
    @Value("#{target.first_name}")
    String getFirstName();
    @Value("#{target.last_name}")
    String getLastName();
    @Value("#{target.birth_date}")
    String getBirthDate();
    String getPhone();
    String getAttachmentId();
    List<String> getGroups();
}
