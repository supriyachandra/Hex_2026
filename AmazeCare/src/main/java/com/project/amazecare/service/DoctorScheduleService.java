package com.project.amazecare.service;

import com.project.amazecare.dto.DoctorRespDto;
import com.project.amazecare.dto.ScheduleDto;
import com.project.amazecare.dto.ScheduleRespDto;
import com.project.amazecare.dto.TimeSlotsDto;
import com.project.amazecare.mapper.ScheduleMapper;
import com.project.amazecare.model.DoctorSchedule;
import com.project.amazecare.repository.DoctorScheduleRepository;
import com.project.amazecare.repository.PrescriptionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DoctorScheduleService {
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorService doctorService;
    private final PrescriptionRepository prescriptionRepository;

    public void addSchedule(ScheduleDto scheduleDto) {
        log.atLevel(Level.INFO).log("Called addSchedule: Admin adds doctor schedule");
        DoctorSchedule doctorSchedule= ScheduleMapper.mapToEntity(scheduleDto);
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
}
