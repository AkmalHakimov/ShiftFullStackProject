package com.example.demo.repository;


import com.example.demo.entity.TimeTable;
import com.example.demo.projection.TimeTableProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TimeTableRepo extends JpaRepository<TimeTable, UUID> {

    List<TimeTable> deleteAllByGroup_Id(UUID x);

    @Query(value = "select id,end_date,price,start_date,title,group_id,mentor_id,status from timetable where group_id = :groupId and status <> 2     order by start_date desc limit 1",nativeQuery = true)
    TimeTable getLastTimeTable(UUID groupId);

    @Query(value = "select id,end_date,price,start_date,title,group_id,mentor_id,status from timetable where group_id = :groupId order by start_date",nativeQuery = true)
    List<TimeTable> findAllByGroup_Id(UUID groupId);

    @Query(value = "select id,end_date,price,start_date,title,group_id,mentor_id,status from timetable where status = 2 and group_id = :groupId order by price desc limit 1",nativeQuery = true)
    TimeTable findLastCompletedTimeTable(UUID groupId);
}
