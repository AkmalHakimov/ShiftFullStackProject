package com.example.demo.service.timeTableService;

import com.example.demo.entity.*;
import com.example.demo.payload.request.ReqTimeTable;
import com.example.demo.repository.*;
import com.example.demo.service.studentTimeTableService.StudentTimeTableServiceImple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TimeTableServiceImple implements TimeTableService {

    private final MentorRepo mentorRepo;
    private final GroupRepo groupRepo;
    private final TimeTableRepo timeTableRepo;
    private final StudentTimeTableRepo studentTimeTableRepo;
    private final TimeTableDayRepo timeTableDayRepo;

    @Override
    public HttpEntity<?> completeTable(UUID id) {
        try {
            TimeTable timeTable = timeTableRepo.findById(id).orElseThrow();
            if(timeTable.getEndDate().equals(LocalDate.now())){
                System.out.println("salom");
                timeTable.setStatus(Status.COMPLETED);
                TimeTable save = timeTableRepo.save(timeTable);
                return ResponseEntity.ok(save);
            }else {
                return ResponseEntity.status(404).body("An error has occurred");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    @Override
    public HttpEntity<?> SaveTimeTable(ReqTimeTable reqTimeTable) {
        try {
            Group group = groupRepo.findById(reqTimeTable.getGroupId()).orElseThrow();
            Mentor mentor = mentorRepo.findById(reqTimeTable.getMentorId()).orElseThrow();
            UUID id = UUID.randomUUID();
            TimeTable timeTable = new TimeTable(
                    id,
                    reqTimeTable.getTitle(),
                    reqTimeTable.getPrice(),
                    Status.CREATED,
                    group,
                    mentor
            );

            TimeTable save = timeTableRepo.save(timeTable);
            return ResponseEntity.ok(save);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    private void saveCompletedTimeTableStudents(TimeTable timeTable) {
        List<TimeTableStudent> students = copyLastStudents(timeTable.getGroup());
        List<TimeTableStudent> newTableStudents = new ArrayList<>();
        for (TimeTableStudent student : students) {
            TimeTableStudent timeTableStudent = new TimeTableStudent(
                    UUID.randomUUID(),
                    student.getPrice(),
                    false,
                    student.getArchive(),
                    timeTable,
                    student.getUser()
            );
            TimeTableStudent save = studentTimeTableRepo.save(timeTableStudent);
            SaveTimeTableDay(save);
            newTableStudents.add(timeTableStudent);
        }
    }

    private void SaveTimeTableDay(TimeTableStudent timeTableStudent) {
        List<TimeTableDay> timeTableDays = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            TimeTableDay timeTableDay = new TimeTableDay(
                    null,
                    false,
                    null,
                    timeTableStudent
            );
            timeTableDays.add(timeTableDay);
        }
        timeTableDayRepo.saveAll(timeTableDays);
    }

    private List<TimeTableStudent> copyLastStudents(Group group) {
        TimeTable lastCompletedTimeTable = timeTableRepo.findLastCompletedTimeTable(group.getId());
        return studentTimeTableRepo.findAllByTimeTableId(lastCompletedTimeTable.getId());
    }

    @Override
    public HttpEntity<?> SaveAllTimeTable(ReqTimeTable reqTimeTable) {
        try {
            Group group = groupRepo.findById(reqTimeTable.getGroupId()).orElseThrow();
            Mentor mentor = mentorRepo.findById(reqTimeTable.getMentorId()).orElseThrow();
            List<LocalDate> dates = detectEndTime(group.getDayType().name(), reqTimeTable.getStartDate());
            TimeTable timeTable = new TimeTable(
                    UUID.randomUUID(),
                    reqTimeTable.getTitle(),
                    reqTimeTable.getPrice(),
                    reqTimeTable.getStartDate(),
                    dates.get(11),
                    Status.STARTED,
                    group,
                    mentor,
                    dates
            );
            TimeTable save = timeTableRepo.save(timeTable);
            saveCompletedTimeTableStudents(save);
            return ResponseEntity.ok(save);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    @Override
    public HttpEntity<?> GetTimeTable(UUID groupId) {
        try {
            List<TimeTable> all = timeTableRepo.findAllByGroup_Id(groupId);
            return ResponseEntity.ok(all);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    @Override
    public HttpEntity<?> GetLastTimeTable(UUID groupId) {
        try {
            TimeTable lastTimeTable = timeTableRepo.getLastTimeTable(groupId);
            return ResponseEntity.ok(lastTimeTable);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }


    @Override
    public HttpEntity<?> GetTimeTableById(UUID id) {
        try {
            TimeTable timeTable = timeTableRepo.findById(id).orElseThrow();
            return ResponseEntity.ok(timeTable);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

        @Override
        public HttpEntity<?> delTimeTable(UUID id) {
            try {
                timeTableRepo.deleteById(id);
                return ResponseEntity.ok("Deleted✅");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("An error has occurred");
            }
        }

    @Override
    public HttpEntity<?> editTimeTable(UUID id, ReqTimeTable reqTimeTable) {
        try {
            Group group = groupRepo.findById(reqTimeTable.getGroupId()).orElseThrow();
            Mentor mentor = mentorRepo.findById(reqTimeTable.getMentorId()).orElseThrow();
            TimeTable timeTable = new TimeTable(
                    id,
                    reqTimeTable.getTitle(),
                    reqTimeTable.getPrice(),
                    reqTimeTable.getStatus(),
                    group,
                    mentor
            );
            timeTableRepo.save(timeTable);
            return ResponseEntity.ok("edited✅");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    @Override
    public HttpEntity<?> startTimeTable(UUID id, ReqTimeTable reqTimeTable) {
        try {
            if(reqTimeTable.getStartDate().getDayOfWeek() == DayOfWeek.SUNDAY){
                return ResponseEntity.status(500).body("Please, Select weekdays");
            }
            TimeTable oldTimeTable = timeTableRepo.findById(id).orElseThrow();
            List<LocalDate> dates = detectEndTime(oldTimeTable.getGroup().getDayType().name(), reqTimeTable.getStartDate());
            TimeTable timeTable = new TimeTable(
                    id,
                    oldTimeTable.getTitle(),
                    oldTimeTable.getPrice(),
                    reqTimeTable.getStartDate(),
                    dates.get(11),
                    Status.STARTED,
                    oldTimeTable.getGroup(),
                    oldTimeTable.getMentor(),
                    dates
            );
            timeTableRepo.save(timeTable);
            return ResponseEntity.ok(timeTable);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }

    private List<LocalDate> detectEndTime(String dayType, LocalDate startDate) {
        List<LocalDate> dates = new ArrayList<>();
        Integer count = 0;
        String Odd = "ODD";
        String Even = "EVEN";
        if(dayType.equals(Even)){
            while (count < 12) {
                if (startDate.getDayOfWeek() != DayOfWeek.SUNDAY && startDate.getDayOfMonth() % 2 == 0) {
                    dates.add(startDate);
                    count++;
                }
                startDate = startDate.plusDays(1);
            }
        }else if(dayType.equals(Odd)){
            while (count < 12) {
                if (startDate.getDayOfWeek() != DayOfWeek.SUNDAY && startDate.getDayOfMonth() % 2 == 1) {
                    dates.add(startDate);
                    count++;
                }
                startDate = startDate.plusDays(1);
            }
        }else {
            while (count < 12) {
                if (startDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                    dates.add(startDate);
                    count++;
                }
                startDate = startDate.plusDays(1);
            }
        }
        return dates;
    }
}
