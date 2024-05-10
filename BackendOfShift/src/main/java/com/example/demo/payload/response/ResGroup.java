package com.example.demo.payload.response;

import com.example.demo.entity.DayType;
import com.example.demo.entity.Mentor;
import com.example.demo.entity.Room;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResGroup {
    private UUID id;
    private String name;
    private String dayType;
    private LocalTime startTime;
    private LocalTime endTime;
    private String roomName;
    private Integer roomId;

}
