package com.project.amazecare.service;

import com.project.amazecare.dto.DoctorReqDto;
import com.project.amazecare.dto.DoctorRespDto;
import com.project.amazecare.dto.DoctorSignUpDto;
import com.project.amazecare.dto.ScheduleRespDto;
import com.project.amazecare.enums.Role;
import com.project.amazecare.exception.ResourceNotFoundException;
import com.project.amazecare.mapper.DoctorMapper;
import com.project.amazecare.mapper.PatientMapper;
import com.project.amazecare.model.*;
import com.project.amazecare.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public void addDoctor(DoctorSignUpDto doctorSignUpDto) {
        // map to doctor
        Doctor doctor= DoctorMapper.mapFromSignUpDto(doctorSignUpDto);

        // inject specialization
        Specialization specialization= new Specialization();
        specialization.setId(doctorSignUpDto.specialization_id());

        User user= new User();
        user.setRole(Role.DOCTOR);
        user.setUsername(doctorSignUpDto.username());

        // encrypted password
        user.setPassword(passwordEncoder.encode(doctorSignUpDto.password()));

        // save the transient user
        user= userService.saveUser(user);

        doctor.setUser(user);
        doctor.setSpecialization(specialization);
        // save patient to DB
        doctorRepository.save(doctor);
    }


    public List<DoctorReqDto> getAllDoctors(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);

        Page<Doctor> doctorPage= doctorRepository.findAll(pageable);

        return doctorPage
                .stream()
                .map(DoctorMapper :: mapToDto)
                .toList();
    }

    public List<DoctorReqDto> filterDoctorBySpecializationId(long specializationId) {
        List<Doctor> doctorList= doctorRepository.filterDoctorBySpecializationId(specializationId);
        return doctorList
                .stream()
                .map(DoctorMapper::mapToDto)
                .toList();
    }

    // testing done
    public DoctorRespDto findById(long doctorId) {
        Doctor doctor= doctorRepository.findById(doctorId).
                orElseThrow(()-> new ResourceNotFoundException("Invalid Doctor ID"));
        return DoctorMapper.mapToRespDto(doctor);
    }
}
