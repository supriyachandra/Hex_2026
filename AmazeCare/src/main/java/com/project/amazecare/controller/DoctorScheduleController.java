package com.project.amazecare.controller;

import com.project.amazecare.dto.ScheduleDto;
import com.project.amazecare.dto.ScheduleRespDto;
import com.project.amazecare.dto.TimeSlotDto;
import com.project.amazecare.dto.TimeSlotsDto;
import com.project.amazecare.service.DoctorScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class DoctorScheduleController {
    private final DoctorScheduleService doctorScheduleService;

    // access: admin
    @PostMapping("/add/{doctorId}")
    public ResponseEntity<HttpStatus> addSchedule(@RequestBody ScheduleDto scheduleDto,
                                         @PathVariable long doctorId){
        doctorScheduleService.addSchedule(scheduleDto, doctorId);
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

    @GetMapping("/{doctorId}")
    public List<ScheduleDto> getSchedule(@PathVariable Long doctorId){
        return doctorScheduleService.getSchedule(doctorId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSchedule(@PathVariable Long id){
        doctorScheduleService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/slots/{doctorId}/{date}")
    public ResponseEntity<List<TimeSlotDto>> getSlots(
            @PathVariable Long doctorId,
            @PathVariable LocalDate date) {
        return ResponseEntity.ok(doctorScheduleService.getSlotsByDoctorAndDate(doctorId, date));
    }
}
