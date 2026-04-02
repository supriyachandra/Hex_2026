package com.project.amazecare.mapper;

import com.project.amazecare.dto.ScheduleDto;
import com.project.amazecare.dto.ScheduleRespDto;
import com.project.amazecare.model.Doctor;
import com.project.amazecare.model.DoctorSchedule;

public class ScheduleMapper {
    public static DoctorSchedule mapToEntity(ScheduleDto scheduleDto){
        DoctorSchedule doctorSchedule= new DoctorSchedule();
        doctorSchedule.setDate(scheduleDto.date());
        doctorSchedule.setStartTime(scheduleDto.startTime());
        doctorSchedule.setEndTime(scheduleDto.endTime());
        doctorSchedule.setSlotDuration(scheduleDto.duration());
        Doctor doctor= new Doctor();
        doctor.setId(scheduleDto.doctor_id());
        doctorSchedule.setDoctor(doctor);
        return doctorSchedule;
    }

    public static ScheduleRespDto mapToScheduleRespDto(DoctorSchedule doctorSchedule){
        return new ScheduleRespDto(
                doctorSchedule.getDoctor().getName(),
                doctorSchedule.getDate(),
                doctorSchedule.getStartTime(),
                doctorSchedule.getEndTime()
        );
    }
}
