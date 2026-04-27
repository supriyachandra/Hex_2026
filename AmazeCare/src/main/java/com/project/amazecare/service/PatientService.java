package com.project.amazecare.service;

import com.project.amazecare.dto.*;
import com.project.amazecare.enums.PatientType;
import com.project.amazecare.enums.Role;
import com.project.amazecare.exception.ResourceNotFoundException;
import com.project.amazecare.mapper.DoctorMapper;
import com.project.amazecare.mapper.PatientMapper;
import com.project.amazecare.model.Doctor;
import com.project.amazecare.model.Patient;
import com.project.amazecare.model.User;
import com.project.amazecare.repository.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final ConsultationRepository consultationRepository;


    public void patientSignUp(PatientSignUpDto patientSignUpDto) {
        // map to patient
        Patient patient= PatientMapper.mapFromSignUpDto(patientSignUpDto);

        // User creation-- role assignment-- saving user
        User user= new User();
        user.setRole(Role.PATIENT);
        user.setUsername(patientSignUpDto.username());
        user.setPassword(passwordEncoder.encode(patientSignUpDto.password()));
        userService.saveUser(user);

        // save patient to DB
        patient.setUser(user);
        patientRepository.save(patient);
    }

    public PatientReqDto getById(long id) {
        Patient patient= patientRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Invalid Patient ID")
        );
        return PatientMapper.mapToDto(patient);
    }

    public Patient getByPatientId(long id) {
        return patientRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Invalid Patient ID"));
    }

    public Patient getByUsername(String username) {
        return appointmentRepository.getByUsername(username);
    }

    public void createPatient(@Valid CreatePatientDto createPatientDto) {
        Patient patient= PatientMapper.mapFromCreatePatient(createPatientDto);
        patientRepository.save(patient);
    }

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    public PatientPaginationDto getAllPatients(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);

        Page<Patient> patientPage= patientRepository.findAll(pageable);

        List<CreatePatientDto> patientDtoList= patientPage.stream()
                .map(PatientMapper::mapToCreatePatientDto)
                .toList();
        long records= patientPage.getTotalElements();
        int pages= patientPage.getTotalPages();

        return new PatientPaginationDto(
                patientDtoList,
                records,
                pages
        );
    }

    public PatientRespDto getPatient(String username) {
        Patient patient= patientRepository.getPatient(username);

        return PatientMapper.mapToRespDto(patient);
    }

    public List<PatientStatDto> getStats(String username){

        int totalVisits = consultationRepository.countVisitsByUsername(username);
        int upcoming = appointmentRepository.countUpcomingByUsername(username);
        int prescriptions = prescriptionRepository.countByUsername(username);

        return List.of(
                new PatientStatDto("Total Visits", totalVisits),
                new PatientStatDto("Upcoming", upcoming),
                new PatientStatDto("Prescriptions", prescriptions)
        );
    }

    public List<PatientRespDto> all() {
        return patientRepository.findAll().stream()
                .map(PatientMapper:: mapToRespDto)
                .toList();
    }

    public Long totalPatient() {
        return patientRepository.totalPatients();
    }
}
