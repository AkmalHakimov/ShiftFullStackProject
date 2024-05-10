package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "timetable")
@Builder
@Entity
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private Integer price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
    @ManyToOne
    private Group group;
    @ManyToOne
    private Mentor mentor;
    @ElementCollection
    private List<LocalDate> dates;

    public TimeTable(UUID id, String title, Integer price, Status status, Group group, Mentor mentor) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.status = status;
        this.group = group;
        this.mentor = mentor;
    }

    //    public TimeTable(UUID id, String title, Integer price, LocalDate startDate, LocalDate endDate, Group group, Mentor mentor) {
//        this.id = id;
//        this.title = title;
//        this.price = price;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.group = group;
//        this.mentor = mentor;
//    }
}
