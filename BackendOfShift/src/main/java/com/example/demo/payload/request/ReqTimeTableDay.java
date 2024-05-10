package com.example.demo.payload.request;

import com.example.demo.entity.TimeTableStudent;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqTimeTableDay {
    private Integer mark;
    private Boolean absent;
    private String description;
    private TimeTableStudent timeTableStudent;
}
