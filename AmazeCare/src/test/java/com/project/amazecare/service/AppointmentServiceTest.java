package com.project.amazecare.service;

import com.project.amazecare.dto.AppointmentRespDto;
import com.project.amazecare.enums.AppointmentStatus;
import com.project.amazecare.exception.ResourceNotFoundException;
import com.project.amazecare.model.Appointment;
import com.project.amazecare.model.Doctor;
import com.project.amazecare.model.Patient;
import com.project.amazecare.repository.AppointmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Test
    public void getByIdIfExistsTest() {

        // prepare doctor
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr A");

        // prepare patient
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Patient A");

        // prepare appointment
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setAppointmentDate(LocalDate.now());
        appointment.setTimeSlot(LocalTime.now());
        appointment.setSymptoms("Fever");
        appointment.setAppointmentStatus(AppointmentStatus.PENDING);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        // mock
        Mockito.when(appointmentRepository.findById(1L))
                .thenReturn(Optional.of(appointment));

        // call
        Appointment result = appointmentService.getById(1L);

        // assert
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Fever", result.getSymptoms());

        Mockito.verify(appointmentRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void getByIdIfNotExistsTest() {

        Mockito.when(appointmentRepository.findById(1L))
                .thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> appointmentService.getById(1L)
        );

        Assertions.assertEquals("Invalid Appointment ID", e.getMessage());
    }

}
