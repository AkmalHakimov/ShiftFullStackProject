package com.example.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqStudentTimeTable {
    private UUID timeTableId;
    private UUID studentId;
    private Integer price;
}
