package com.example.demo.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalTime;
import java.util.UUID;

public interface GroupProjection {


    UUID getId();
    @Value("#{target.name}")
    String getName();
    @Value("#{target.day_type}")
    String getDayType();
    @Value("#{target.start_time}")
    LocalTime getStartTime();
    @Value("#{target.end_time}")
    LocalTime getEndTime();
    @Value("#{target.room_id}")
    Integer getRoomId();
    @Value("#{target.room_name}")
    String getRoomName();
}
