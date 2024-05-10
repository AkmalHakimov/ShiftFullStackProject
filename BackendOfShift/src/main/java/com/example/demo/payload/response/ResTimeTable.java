package com.example.demo.payload.response;

import com.example.demo.entity.Group;
import com.example.demo.entity.Mentor;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResTimeTable {
    private UUID id;
    private String title;
    private Integer price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Group group;
    private Mentor mentor;
    List<LocalDate> dates;
}
