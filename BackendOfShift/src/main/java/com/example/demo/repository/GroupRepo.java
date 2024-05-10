package com.example.demo.repository;

import com.example.demo.entity.DayType;
import com.example.demo.entity.Group;
import com.example.demo.projection.GroupProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupRepo extends JpaRepository<Group, UUID> {

    @Query(value = "select g.id,g.day_type,g.end_time,g.start_time,g.name,g.room_id,r.name as room_name  from groups g inner join rooms r on r.id = g.room_id where day_type = 'EVEN'",nativeQuery = true)
    List<GroupProjection> findAllGroupByDayTypeEven();

    @Query(value = "select g.id,g.day_type,g.end_time,g.start_time,g.name,g.room_id,r.name as room_name  from groups g inner join rooms r on r.id = g.room_id",nativeQuery = true)
    List<GroupProjection> findAllGroups();

    @Query(value = "select g.id,g.day_type,g.end_time,g.start_time,g.name,g.room_id,r.name as room_name  from groups g inner join rooms r on r.id = g.room_id and g.id = :groupId",nativeQuery = true)
    GroupProjection findGroupById(UUID groupId);

    @Query(value = "select g.id,g.day_type,g.end_time,g.start_time,g.name,g.room_id,r.name as room_name  from groups g inner join rooms r on r.id = g.room_id where day_type = 'ODD'",nativeQuery = true)
    List<GroupProjection> findAllGroupByDayTypeOdd();

    @Query(value = "select g.id,g.day_type,g.end_time,g.start_time,g.name,g.room_id,r.name as room_name  from groups g inner join rooms r on r.id = g.room_id where day_type = 'ALL'",nativeQuery = true)
    List<GroupProjection> findAllGroupByDayTypeAll();


    @Query("""
        select t from Group t
        where t.dayType=:dayType
        and (:startTime between t.startTime and t.endTime
              or :endTime between t.startTime and t.endTime)
        and t.room.id=:room_id
        """)
    Optional<Group> getIfRoomIsTaken(DayType dayType, LocalTime startTime, LocalTime endTime, Integer room_id);

//    @Query("""
//        select t from Group t
//        where t.dayType=:dayType
//        and (:startTime between t.startTime and t.endTime
//              or :endTime between t.startTime and t.endTime)
//        and t.user.id=:teacher_id
//        """)
//    Optional<Group> getIfTeacherBusy(DayType dayType, LocalTime startTime, LocalTime endTime, Integer teacher_id);
}
