package com.project.amazecare.service;

import com.project.amazecare.dto.DoctorRespDto;
import com.project.amazecare.dto.ScheduleDto;
import com.project.amazecare.dto.ScheduleRespDto;
import com.project.amazecare.mapper.ScheduleMapper;
import com.project.amazecare.model.Doctor;
import com.project.amazecare.model.DoctorSchedule;
import com.project.amazecare.repository.DoctorScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorScheduleService {
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorService doctorService;

    public void addSchedule(ScheduleDto scheduleDto) {
        DoctorSchedule doctorSchedule= ScheduleMapper.mapToEntity(scheduleDto);
        doctorScheduleRepository.save(doctorSchedule);
    }

    public List<ScheduleRespDto> scheduleByDoctor(long doctorId) {
        // find doctor by doctor_id
        DoctorRespDto doctor= doctorService.findById(doctorId);
        // find DoctorSchedule by doctor id
        List<DoctorSchedule> doctorScheduleList= doctorScheduleRepository.findByDoctorId(doctorId);

        // convert doctorSchedule list to scheduleDto list
        return doctorScheduleList.stream()
                .map(ScheduleMapper::mapToScheduleRespDto)
                .toList();
    }
}
