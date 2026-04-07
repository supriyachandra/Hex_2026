package com.project.amazecare.controller;

import com.project.amazecare.dto.ScheduleDto;
import com.project.amazecare.dto.ScheduleRespDto;
import com.project.amazecare.dto.TimeSlotsDto;
import com.project.amazecare.service.DoctorScheduleService;
import lombok.AllArgsConstructor;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@AllArgsConstructor
public class DoctorScheduleController {
    private final DoctorScheduleService doctorScheduleService;

    // access: admin
    @PostMapping("/add")
    public ResponseEntity<?> addSchedule(@RequestBody ScheduleDto scheduleDto){
        doctorScheduleService.addSchedule(scheduleDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Access: authenticated
    // get scheduled upcoming Dates and doctor name by doctor id
    @GetMapping("/get-by/{doctor_id}")
    public List<ScheduleRespDto> scheduleByDoctor(@PathVariable long doctor_id){
        return doctorScheduleService.scheduleByDoctor(doctor_id);
    }

    // get time slots available for a particular selected date
    @GetMapping("/get-time-by/{doctor_id}/{date}")
    public List<TimeSlotsDto> timeByDoctorAndDate(@PathVariable long doctor_id,
                                                  @PathVariable LocalDate date){
        return doctorScheduleService.timeByDoctorAndDate(doctor_id, date);
    }
}
