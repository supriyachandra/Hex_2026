package com.project.amazecare.service;

import com.project.amazecare.dto.*;
import com.project.amazecare.enums.AppointmentStatus;
import com.project.amazecare.enums.PatientType;
import com.project.amazecare.exception.AppointmentAndAdmissionException;
import com.project.amazecare.exception.AppointmentUpdateException;
import com.project.amazecare.exception.ResourceNotFoundException;
import com.project.amazecare.mapper.ConsultationMapper;
import com.project.amazecare.mapper.PrescriptionMapper;
import com.project.amazecare.mapper.TestsMapper;
import com.project.amazecare.model.*;
import com.project.amazecare.repository.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final PrescriptionRepository prescriptionRepository;
    private final TestsRepository testsRepository;

    public void addConsult(ConsultReqDto consultReqDto, Principal principal) {
        log.atLevel(Level.INFO)
                .log("Called addConsult: Add a consult by appointment ID(for OPD patients) " +
                        "or admission ID(for IPD patients)");

        Consultation consultation;

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
            // --not needed since using principal REMOVE LATER
            if(!principal.getName().equals(appointment.getDoctor().getUser().getUsername())){
                throw new AppointmentUpdateException("Doctor cannot consult this patient");
            }

            consultation = ConsultationMapper.mapToEntity(consultReqDto);
            consultation.setAppointment(appointment);
            consultationRepository.save(consultation);

            // update status and save appointment
            appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
            appointmentService.saveAppointment(appointment);
        }

        else{
            // appointment by id
            Admission admission = admissionService.findAdmissionById(consultReqDto.admission_id());

            // validate if the consultation ID belongs to the doctor that has logged in
            // REMOVE ITTT
            if(!principal.getName().equals(admission.getDoctor().getUser().getUsername())){
                throw new AppointmentUpdateException("Doctor cannot consult this patient");
            }

            consultation = ConsultationMapper.mapToEntity(consultReqDto);
            consultation.setAdmission(admission);
            consultationRepository.save(consultation);
        }

        if (consultReqDto.prescriptions() != null) {
            for (PrescriptionDto p : consultReqDto.prescriptions()) {

                Prescription prescription = PrescriptionMapper.mapToEntity2(p);
                prescription.setConsultation(consultation);

                prescriptionRepository.save(prescription);
            }
        }

        if (consultReqDto.tests() != null) {
            for (TestReqDto t : consultReqDto.tests()) {

                RecommendedTests test = TestsMapper.mapToEntity(t);
                test.setConsultation(consultation);

                testsRepository.save(test);
            }
        }

        log.atLevel(Level.INFO).log("Consult added!");
    }

    public Consultation findConsultById(long id) {
        log.atLevel(Level.INFO).log("Called findConsultById: get consultation by ID!");
        return consultationRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Consultation ID Invalid"));
    }

    public List<MedicalRecordDto> getMedicalRecord(String username) {
        List<Consultation> consultationList= consultationRepository.getAllByUsername(username);

        return consultationList.stream()
                .map(ConsultationMapper :: mapToRecordDto)
                .toList();
    }

    public MedicalRecordPagination getConsultHistory(int page, int size, String type, String username) {
        Pageable pageable= PageRequest.of(page, size);
        Page<Consultation> consultationPage;

        if (type.equalsIgnoreCase("OPD")) {
            consultationPage= consultationRepository.getOpdConsults(username, pageable);
        } else {
            consultationPage= consultationRepository.getIpdConsults(username, pageable);
        }

        List<MedicalRecordDto> medicalRecordList= consultationPage.stream()
                .map(ConsultationMapper :: mapToRecordDto)
                .toList();

        long records= consultationPage.getTotalElements();
        int pages= consultationPage.getTotalPages();

        return new MedicalRecordPagination(
                medicalRecordList,
                records,
                pages
        );
    }

    public MedicalRecordDto getConsultByAppId(Long appId, String username) {
        Consultation consultation= consultationRepository.getConsultByAppId(appId, username);

        return ConsultationMapper.mapToRecordDto(consultation);
    }
}
