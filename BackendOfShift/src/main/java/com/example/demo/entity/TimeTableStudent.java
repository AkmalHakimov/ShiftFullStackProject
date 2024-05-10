package com.example.demo.entity;


import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "timetablestudent")
public class    TimeTableStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Integer price;
    private Boolean active;
    private Boolean archive;
    @ManyToOne
    private TimeTable timeTable;
    @OneToOne
    private User user;
}
