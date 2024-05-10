package com.example.demo.payload.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqUser {
    private String phone;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;
    private String description;
    private UUID photoId;


    public ReqUser(String phone, String password, String firstName, String lastName, Integer age, String description) {
        this.phone = phone;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.description = description;
    }
}
