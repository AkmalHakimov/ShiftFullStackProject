package com.example.demo.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

public interface TimeTableProjection {

    UUID getId();
    String getTitle();
    @Value("#{target.price}")
    String getPrice();
    @Value("#{target.start_date}")
    String getStartDate();
    String getPhone();
    @Value("#{target.end_date}")
    String getEndDate();
    @Value("#{target.end_date}")
    List<String> dates();
}
