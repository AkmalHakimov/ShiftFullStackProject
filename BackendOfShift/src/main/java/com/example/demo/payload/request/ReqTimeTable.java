package com.example.demo.payload.request;

import com.example.demo.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqTimeTable {
    private String title;
    private Integer price;
    private UUID groupId;
    private UUID mentorId;
    private LocalDate startDate;
    private Status status;
}
