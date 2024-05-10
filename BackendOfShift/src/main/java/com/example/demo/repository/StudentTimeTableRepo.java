package com.example.demo.repository;


import com.example.demo.entity.TimeTable;
import com.example.demo.entity.TimeTableStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentTimeTableRepo extends JpaRepository<TimeTableStudent, UUID> {
    List<TimeTableStudent> findAllByTimeTableId(UUID x);
}
