package com.example.demo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResMentor {
    private UUID id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String phone;
}
