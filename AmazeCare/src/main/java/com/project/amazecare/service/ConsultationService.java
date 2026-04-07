package com.project.amazecare.service;

import com.project.amazecare.dto.ConsultReqDto;
import com.project.amazecare.dto.MedicalRecordDto;
import com.project.amazecare.enums.AppointmentStatus;
import com.project.amazecare.exception.AppointmentAndAdmissionException;
import com.project.amazecare.exception.AppointmentUpdateException;
import com.project.amazecare.exception.ResourceNotFoundException;
import com.project.amazecare.mapper.ConsultationMapper;
import com.project.amazecare.model.Admission;
import com.project.amazecare.model.Appointment;
import com.project.amazecare.model.Consultation;
import com.project.amazecare.repository.AppointmentRepository;
import com.project.amazecare.repository.ConsultationRepository;
import com.project.amazecare.repository.PatientRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final AppointmentService appointmentService;
    private final AdmissionService admissionService;

    public void addConsult(ConsultReqDto consultReqDto, Principal principal) {
        log.atLevel(Level.INFO)
                .log("Called addConsult: Add a consult by appointment ID(for OPD patients) " +
                        "or admission ID(for IPD patients)");

        if(consultReqDto.admission_id() == null
                && consultReqDto.appointment_id()==null) {
            throw new AppointmentAndAdmissionException("Either correct appointment ID or Admission ID must be given");
        }

        if(consultReqDto.admission_id() != null
                && consultReqDto.appointment_id()!=null) {
            throw new AppointmentAndAdmissionException("Both appointment ID and Admission ID cannot be given");
        }

        if(consultReqDto.admission_id()==null){
            // appointment by id
            Appointment appointment = appointmentService.getById(consultReqDto.appointment_id());

            // validate if the consultation ID belongs to the doctor that has logged in
            if(!principal.getName().equals(appointment.getDoctor().getUser().getUsername())){
                throw new AppointmentUpdateException("Doctor cannot consult this patient");
            }

            Consultation consultation = ConsultationMapper.mapToEntity(consultReqDto);
            consultation.setAppointment(appointment);
            consultationRepository.save(consultation);

            // update status and save appointment
            appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
            appointmentService.saveAppointment(appointment);
        }

        if(consultReqDto.appointment_id()==null){
            // appointment by id
            Admission admission = admissionService.findAdmissionById(consultReqDto.admission_id());

            // validate if the consultation ID belongs to the doctor that has logged in
            if(!principal.getName().equals(admission.getDoctor().getUser().getUsername())){
                throw new AppointmentUpdateException("Doctor cannot consult this patient");
            }

            Consultation consultation = ConsultationMapper.mapToEntity(consultReqDto);
            consultation.setAdmission(admission);
            consultationRepository.save(consultation);
        }

        log.atLevel(Level.INFO).log("Consult added!");
    }

    public Consultation findConsultById(long id) {
        log.atLevel(Level.INFO).log("Called findConsultById: get consultation by ID!");
        return consultationRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Consultation ID Invalid"));
    }

    /*
    public List<MedicalRecordDto> showMedicalRecord(Principal principal) {
        consultationRepository.showMedicalRecords(principal.getName());
    }
    */
}
