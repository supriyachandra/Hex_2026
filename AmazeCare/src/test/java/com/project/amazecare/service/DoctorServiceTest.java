package com.project.amazecare.service;

import com.project.amazecare.dto.DoctorPaginationDto;
import com.project.amazecare.dto.DoctorReqDto;
import com.project.amazecare.dto.DoctorRespDto;
import com.project.amazecare.exception.ResourceNotFoundException;
import com.project.amazecare.model.Doctor;
import com.project.amazecare.model.Specialization;
import com.project.amazecare.repository.DoctorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Test
    public void getAllDoctorsPaginationTest() {

        // specialization
        Specialization s = new Specialization();
        s.setId(1L);
        s.setName("Cardiology");

        // doctor
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("doctor1");
        doctor.setEmail("doc@gmail.com");
        doctor.setSpecialization(s);

        List<Doctor> list = List.of(doctor);

        Page<Doctor> doctorPage = new PageImpl<>(list);

        Pageable pageable = PageRequest.of(0, 1);

        Mockito.when(doctorRepository.findAll(pageable)).thenReturn(doctorPage);

        DoctorPaginationDto result = doctorService.getAllDoctors(0, 1);

        Assertions.assertEquals(1, result.data().size());
        Assertions.assertEquals(1, result.totalRecords());
        Assertions.assertEquals(1, result.totalPages());

        Mockito.verify(doctorRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    public void findByIdTestIfExists(){
        // specialization
        Specialization s= new Specialization();
        s.setId(1L);
        s.setName("Cardiology");
        // Prepare doctor
        Doctor doctor= new Doctor();
        doctor.setId(1L);
        doctor.setName("doctor1");
        doctor.setEmail("doctor@gmail.com");
        doctor.setPhone("1425371932");
        doctor.setQualification("XYZ");
        doctor.setSpecialization(s);
        doctor.setCreatedAt(Instant.now());

        // response dto
        DoctorRespDto doctorRespDto1= new DoctorRespDto(
                doctor.getId(),
                doctor.getName(),
                doctor.getExperience(),
                doctor.getEmail(),
                doctor.getQualification(),
                doctor.getDesignation(),
                doctor.getSpecialization().getName()
        );

        // set the prepared data... when then
        Mockito.when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        Assertions.assertEquals(doctorRespDto1, doctorService.findById(1L));

        Mockito.verify(doctorRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void findByIdIfNotExists(){
        // return empty object
        Mockito.when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e= Assertions.assertThrows(ResourceNotFoundException.class,
                ()-> doctorService.findById(1L)
        );

        // check message equality
        Assertions.assertEquals("Invalid Doctor ID", e.getMessage());
    }

    @Test
    public void filterDoctorBySpecializationIdTest() {

        // specialization
        Specialization s = new Specialization();
        s.setId(1L);
        s.setName("Cardiology");

        // for doctor list
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("doctor1");
        doctor.setSpecialization(s);

        List<Doctor> list = List.of(doctor);

        Mockito.when(doctorRepository.filterDoctorBySpecializationId(1L))
                .thenReturn(list);

        List<DoctorReqDto> result =
                doctorService.filterDoctorBySpecializationId(1L);

        Assertions.assertEquals(1, result.size());

        Mockito.verify(doctorRepository, Mockito.times(1))
                .filterDoctorBySpecializationId(1L);
    }

    @Test
    public void totalDoctorsTest(){
        // specialization
        Specialization s= new Specialization();
        s.setId(1L);
        s.setName("Cardiology");

        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("doctor1");
        doctor.setSpecialization(s);
        Doctor doctor2 = new Doctor();
        doctor2.setId(2L);
        doctor2.setName("doctor2");
        doctor2.setSpecialization(s);

        List<Doctor> list = List.of(doctor, doctor2);

        Mockito.when(doctorRepository.totalDoctors())
                .thenReturn((long) list.size());

        Assertions.assertEquals(2, doctorService.totalDoctors());
    }
}
