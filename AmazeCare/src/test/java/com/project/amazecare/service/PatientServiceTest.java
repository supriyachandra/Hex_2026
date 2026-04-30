package com.project.amazecare.service;

import com.project.amazecare.dto.PatientReqDto;
import com.project.amazecare.dto.PatientRespDto;
import com.project.amazecare.enums.Gender;
import com.project.amazecare.exception.ResourceNotFoundException;
import com.project.amazecare.model.Patient;
import com.project.amazecare.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Test
    public void getByIdIfExistsTest(){
        // preparing data
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("patient");
        patient.setPhone("1010101010");
        patient.setGender(Gender.FEMALE);
        patient.setDateOfBirth(LocalDate.of(2012, 4, 4));
        patient.setCreatedAt(Instant.now());

        // mock repo
        Mockito.when(patientRepository.findById(1L))
                .thenReturn(Optional.of(patient));

        // call service
        PatientReqDto result = patientService.getById(1L);

        // assertions
        Assertions.assertEquals(patient.getName(), result.name());
        Assertions.assertEquals(patient.getPhone(), result.phone());

        Mockito.verify(patientRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void getByIdIfNotExistsTest(){
        //returns empty list when
        Mockito.when(patientRepository.findById(1L))
                .thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> patientService.getById(1L)
        );
        // checking exception value
        Assertions.assertEquals("Invalid Patient ID", e.getMessage());
    }

    @Test
    public void getByPatientIdTest(){

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("patient");

        Mockito.when(patientRepository.findById(1L))
                .thenReturn(Optional.of(patient));

        Patient result = patientService.getByPatientId(1L);

        Assertions.assertEquals(1L, result.getId());

        Mockito.verify(patientRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void getByPatientIdIfNotExistsTest(){

        Mockito.when(patientRepository.findById(1L))
                .thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> patientService.getByPatientId(1L)
        );

        Assertions.assertEquals("Invalid Patient ID", e.getMessage());
    }

    @Test
    public void getAllPatientsTest(){

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("patient");
        patient.setPhone("1010101010");
        patient.setGender(Gender.FEMALE);
        patient.setDateOfBirth(LocalDate.of(2012, 4, 4));

        List<Patient> list = List.of(patient);

        Mockito.when(patientRepository.findAll()).thenReturn(list);

        List<PatientRespDto> result = patientService.all();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("patient", result.get(0).name());

        Mockito.verify(patientRepository, Mockito.times(1)).findAll();
    }
}
