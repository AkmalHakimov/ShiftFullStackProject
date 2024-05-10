package com.example.demo.payload.request;

import com.example.demo.entity.DayType;
import com.example.demo.entity.Mentor;
import com.example.demo.entity.Room;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqGroup {
    private String name;
    private DayType dayType;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer roomId;
}
