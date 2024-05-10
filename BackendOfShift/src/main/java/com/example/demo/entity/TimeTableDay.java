package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "timetableday")
public class TimeTableDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer mark;
    private Boolean absent;
    private String description;
    @ManyToOne
    private TimeTableStudent timeTableStudent;

    public TimeTableDay(Integer mark, Boolean absent, String description, TimeTableStudent timeTableStudent) {
        this.mark = mark;
        this.absent = absent;
        this.description = description;
        this.timeTableStudent = timeTableStudent;
    }
}
