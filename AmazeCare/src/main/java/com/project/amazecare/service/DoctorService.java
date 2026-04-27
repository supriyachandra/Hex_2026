package com.project.amazecare.service;

import com.project.amazecare.dto.*;
import com.project.amazecare.enums.Role;
import com.project.amazecare.exception.ResourceNotFoundException;
import com.project.amazecare.mapper.DoctorMapper;
import com.project.amazecare.mapper.PatientMapper;
import com.project.amazecare.model.*;
import com.project.amazecare.repository.AdmissionRepository;
import com.project.amazecare.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AdmissionRepository admissionRepository;

    public void addDoctor(DoctorSignUpDto doctorSignUpDto) {

        log.atLevel(Level.INFO)
                .log("Called addDoctor: Add a doctor");

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
        log.atLevel(Level.INFO).log("Doctor added!");
    }


    public DoctorPaginationDto getAllDoctors(int page, int size) {
        log.atLevel(Level.INFO)
                .log("Called getAllDoctors: get all doctors with pagination");
        Pageable pageable= PageRequest.of(page, size);

        Page<Doctor> doctorPage= doctorRepository.findAll(pageable);

        List<DoctorRespDto> data= doctorPage
                .stream()
                .map(DoctorMapper :: mapToRespDto)
                .toList();
        Long records= doctorPage.getTotalElements();
        int pages= doctorPage.getTotalPages();

        return new DoctorPaginationDto(
                data,
                records,
                pages
        );
    }

    public List<DoctorReqDto> filterDoctorBySpecializationId(long specializationId) {
        log.atLevel(Level.INFO)
                .log("Called filterDoctorsBySpecializationId: get all doctors by specialization");
        List<Doctor> doctorList= doctorRepository.filterDoctorBySpecializationId(specializationId);
        return doctorList
                .stream()
                .map(DoctorMapper::mapToDto)
                .toList();
    }

    // testing done
    public DoctorRespDto findById(long doctorId) {
        log.atLevel(Level.INFO)
                .log("Called findById: get doctorRequestDto bY Id");
        Doctor doctor= doctorRepository.findById(doctorId).
                orElseThrow(()-> new ResourceNotFoundException("Invalid Doctor ID"));
        return DoctorMapper.mapToRespDto(doctor);
    }

    public Doctor getById(long id) {
        log.atLevel(Level.INFO)
                .log("Called getById: get doctor By Id");
        return doctorRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Doctor ID Invalid"));
    }

    public Doctor getByUsername(String username) {
        log.atLevel(Level.INFO)
                .log("Called getByUsername: returns logged in doctor");
        return doctorRepository.getByUsername(username);
    }

    public List<DoctorRespDto> filterBySpecAndName(String name, Long specId) {
        log.atLevel(Level.INFO)
                .log("Called filterBySpecAndName: get all doctorRespDto by optional specialization and name");
        List<Doctor> doctor= doctorRepository.filterBySpecAndName(name, specId);
        return doctor.stream()
                .map(DoctorMapper ::mapToRespDto)
                .toList();
    }


    public DoctorRespDto getDoctors(String username) {
        log.atLevel(Level.INFO)
                .log("Called getDoctors: doctorRespDto by username");
        Doctor doctor= doctorRepository.getByUsername(username);
        return DoctorMapper.mapToRespDto(doctor);
    }

    public List<DoctorRespDto> doctorByDate(LocalDate date) {
        log.atLevel(Level.INFO)
                .log("Called doctorsByDate: get all doctorRespDto list by date");
        List<Doctor> list= doctorRepository.doctorByDate(date);
        return list.stream().
                map(DoctorMapper ::mapToRespDto)
                .toList();
    }

    public List<DoctorRespDto> all() {
        log.atLevel(Level.INFO)
                .log("Called all: get all doctors");
        return doctorRepository.findAll().stream()
                .map(DoctorMapper:: mapToRespDto)
                .toList();
    }

    public DoctorRespDto getDoctor(Long doctorId) {
        Doctor doctor= doctorRepository.findById(doctorId).
                orElseThrow(()-> new ResourceNotFoundException("Doctor ID Invalid"));
        return DoctorMapper.mapToRespDto(doctor);
    }

    public Long totalDoctors() {
        log.atLevel(Level.INFO)
                .log("Called totalDoctors: get all doctors count");
        return doctorRepository.totalDoctors();
    }
}
