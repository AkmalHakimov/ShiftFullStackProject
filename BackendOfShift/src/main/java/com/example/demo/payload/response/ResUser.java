package com.example.demo.payload.response;

import com.example.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResUser {
    private String firstName;
    private String lastName;
    private Integer age;
    private String description;
    private List<Role> roles;
}
