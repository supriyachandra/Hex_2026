package com.project.amazecare.service;

import com.project.amazecare.dto.*;
import com.project.amazecare.mapper.ScheduleMapper;
import com.project.amazecare.model.Doctor;
import com.project.amazecare.model.DoctorSchedule;
import com.project.amazecare.repository.DoctorScheduleRepository;
import com.project.amazecare.repository.PrescriptionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DoctorScheduleService {
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorService doctorService;
    private final PrescriptionRepository prescriptionRepository;

    public void addSchedule(ScheduleDto scheduleDto, long doctorId) {
        log.atLevel(Level.INFO).log("Called addSchedule: Admin adds doctor schedule");
        DoctorSchedule doctorSchedule= ScheduleMapper.mapToEntity(scheduleDto);
        // add doctor now
        Doctor doctor= new Doctor();
        doctor.setId(doctorId);
        doctorSchedule.setDoctor(doctor);

        doctorScheduleRepository.save(doctorSchedule);
        log.atLevel(Level.INFO).log("Schedule Added!");
    }

    // update --- returns list of upcoming dates available
    public List<ScheduleRespDto> scheduleByDoctor(long doctorId) {
        log.atLevel(Level.INFO).log("Called scheduleByDoctor: get schedule(dates) by doctor ID");
        // find doctor by doctor_id
        DoctorRespDto doctor= doctorService.findById(doctorId);
        // find DoctorSchedule by doctor id
        List<DoctorSchedule> doctorScheduleList= doctorScheduleRepository.findByDoctorId(doctorId);

        // convert doctorSchedule list to scheduleDto list
        return doctorScheduleList.stream()
                .map(ScheduleMapper::mapToScheduleRespDto)
                .toList();
    }

    public List<TimeSlotsDto> timeByDoctorAndDate(long doctorId, LocalDate date) {
        log.atLevel(Level.INFO).log("Called timeByDoctorAndDate:" +
                " Gets time slots available by selected doctor ID and Date");

        // validate Doctor ID
        doctorService.findById(doctorId);

        return prescriptionRepository.getTimeSlots(doctorId, date);
    }

    public List<ScheduleDto> getSchedule(Long doctorId) {
        List<DoctorSchedule> list= doctorScheduleRepository.getSchedule(doctorId);
        return list.stream()
                .map(ScheduleMapper:: mapToSchedule)
                .toList();
    }

    public void delete(Long id) {
        doctorScheduleRepository.deleteById(id);
    }

    public List<TimeSlotDto> getSlotsByDoctorAndDate(Long doctorId, LocalDate date) {

        List<DoctorSchedule> schedules=
                doctorScheduleRepository.findByDoctorIdAndDate(doctorId, date);

        if (schedules.isEmpty()) {
            throw new RuntimeException("No schedule found");
        }
        List<TimeSlotDto> allSlots = new ArrayList<>();
        for (DoctorSchedule s : schedules) {
            allSlots.addAll(generateSlots(s.getStartTime(), s.getEndTime()));
        }
        return allSlots;
    }

    //slot generation
    public List<TimeSlotDto> generateSlots(LocalTime start, LocalTime end) {

        List<TimeSlotDto> slots = new ArrayList<>();
        LocalTime current = start;

        while (current.isBefore(end)) {
            slots.add(new TimeSlotDto(current.toString())); // "10:00"
            current = current.plusMinutes(15); // slot size
        }
        return slots;
    }
}
