package com.project.amazecare.service;

import com.project.amazecare.dto.ConsultReqDto;
import com.project.amazecare.enums.AppointmentStatus;
import com.project.amazecare.mapper.ConsultationMapper;
import com.project.amazecare.model.Appointment;
import com.project.amazecare.model.Consultation;
import com.project.amazecare.repository.ConsultationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final AppointmentService appointmentService;

    public void addConsult(ConsultReqDto consultReqDto) {
        // appointment by id
        Appointment appointment= appointmentService.getById(consultReqDto.appointment_id());
        // update appointment status to completed and save
        appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
        appointmentService.saveAppointment(appointment);

        Consultation consultation= ConsultationMapper.mapToEntity(consultReqDto);

        consultation.setAppointment(appointment);
        consultationRepository.save(consultation);
    }
}
