package com.project.amazecare.mapper;

import com.project.amazecare.dto.AppointmentDto;
import com.project.amazecare.dto.AppointmentRespDto;
import com.project.amazecare.enums.VisitType;
import com.project.amazecare.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentMapper {

    public static Appointment mapTo(AppointmentDto appointmentDto){
        Appointment appointment= new Appointment();
        appointment.setAppointmentDate(appointmentDto.appointmentDate());
        appointment.setTimeSlot(appointmentDto.timeSlot());
        appointment.setSymptoms(appointmentDto.symptoms());
        appointment.setVisitType(appointmentDto.visitType());
        return appointment;
    }

    public static AppointmentRespDto mapToDto(Appointment appointment) {
        return new AppointmentRespDto(
                appointment.getId(),
                appointment.getAppointmentDate(),
                appointment.getPatient().getId(),
                appointment.getPatient().getName(),
                appointment.getDoctor().getId(),
                appointment.getDoctor().getSpecialization().getName(),
                appointment.getDoctor().getName(),
                appointment.getAppointmentStatus()
        );
    }
    //(app_id, app_date, patient_id, patient name, doctor_id, docter_specialization,  doctor_name, status)
}