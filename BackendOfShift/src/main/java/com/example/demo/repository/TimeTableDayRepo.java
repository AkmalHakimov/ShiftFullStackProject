package com.example.demo.repository;


import com.example.demo.entity.TimeTableDay;
import com.example.demo.entity.TimeTableStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TimeTableDayRepo extends JpaRepository<TimeTableDay, Integer> {

    @Query(value = "select id,absent,description,mark,time_table_student_id from timetableday order by id",nativeQuery = true)
    List<TimeTableDay> findAllOrderByIdAsc();
}
