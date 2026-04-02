package com.project.amazecare.service;

import com.project.amazecare.dto.AppointmentDto;
import com.project.amazecare.dto.DoctorReqDto;
import com.project.amazecare.dto.PatientReqDto;
import com.project.amazecare.dto.PatientSignUpDto;
import com.project.amazecare.enums.Role;
import com.project.amazecare.exception.ResourceNotFoundException;
import com.project.amazecare.mapper.DoctorMapper;
import com.project.amazecare.mapper.PatientMapper;
import com.project.amazecare.model.Doctor;
import com.project.amazecare.model.Patient;
import com.project.amazecare.model.User;
import com.project.amazecare.repository.AppointmentRepository;
import com.project.amazecare.repository.DoctorRepository;
import com.project.amazecare.repository.PatientRepository;
import com.project.amazecare.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AppointmentRepository appointmentRepository;


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

    public Patient getByUsername(String username) {
        return appointmentRepository.getByUsername(username);
    }
}
