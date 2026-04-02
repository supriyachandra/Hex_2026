package com.project.amazecare.service;

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

import javax.print.Doc;
import java.lang.reflect.Array;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Test
    public void getAllDoctorsTest(){
        Specialization s= new Specialization();
        s.setId(1L);
        s.setName("Cardiology");
        // Prepare list of doctors
        Doctor doctor1= new Doctor();
        doctor1.setId(1L);
        doctor1.setName("doctor1");
        doctor1.setEmail("doctor@gmail.com");
        doctor1.setPhone("1425371932");
        doctor1.setQualification("XYZ");
        doctor1.setSpecialization(s);
        doctor1.setCreatedAt(Instant.now());

        Doctor doctor2= new Doctor();
        doctor2.setId(2L);
        doctor2.setName("doctor2");
        doctor2.setEmail("doctor2@gmail.com");
        doctor2.setPhone("1425373932");
        doctor2.setQualification("XYZ");
        doctor2.setSpecialization(s);
        doctor2.setCreatedAt(Instant.now());

        List<Doctor> list= List.of(doctor1, doctor2);

        /*
        // create page of doctor list
        Page<Doctor> doctorPage= new PageImpl<>(list);

        // define page and size for pageable
        int page=0;
        int size=2;
        Pageable pageable1= PageRequest.of(page, size);

        // so if pageable is 0,2 then doctor page list will be used
        Mockito.when(doctorRepository.findAll(pageable1)).thenReturn(doctorPage);

        Assertions.assertEquals(2, doctorService.getAllDoctors(page, size).size());

        */
        // create page of doctor list
        Page<Doctor> doctorPage= new PageImpl<>(list.subList(0,1));

        // define page and size for pageable
        int page=0;
        int size=1;
        Pageable pageable2= PageRequest.of(page, size);

        // so if pageable is 0,1 then doctor page list will be used
        Mockito.when(doctorRepository.findAll(pageable2)).thenReturn(doctorPage);

        Assertions.assertEquals(1, doctorService.getAllDoctors(page, size).size());
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
}
