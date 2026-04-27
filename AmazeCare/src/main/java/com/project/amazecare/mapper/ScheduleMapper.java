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
        return doctorSchedule;
    }

    public static ScheduleRespDto mapToScheduleRespDto(DoctorSchedule doctorSchedule){
        return new ScheduleRespDto(
                doctorSchedule.getDate()
        );
    }

    public static ScheduleDto mapToSchedule(DoctorSchedule doctorSchedule) {
        return new ScheduleDto(
                doctorSchedule.getId(),
                doctorSchedule.getDate(),
                doctorSchedule.getStartTime(),
                doctorSchedule.getEndTime()
        );
    }
}
