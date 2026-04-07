package com.project.amazecare.service;

import com.project.amazecare.dto.*;
import com.project.amazecare.enums.AppointmentStatus;
import com.project.amazecare.exception.AppointmentStatusUpdateException;
import com.project.amazecare.exception.AppointmentUpdateException;
import com.project.amazecare.exception.ResourceNotFoundException;
import com.project.amazecare.mapper.AppointmentMapper;
import com.project.amazecare.model.Appointment;
import com.project.amazecare.model.Doctor;
import com.project.amazecare.model.Patient;
import com.project.amazecare.repository.AppointmentRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j      //---------add logs here
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public ResponseEntity<?> bookAppointment(AppointmentDto appointmentDto, String username, long doctor_id) {
        // Map to Appointment
        Appointment appointment= AppointmentMapper.mapTo(appointmentDto);

        // additional DI
        Doctor doctor= new Doctor();
        doctor.setId(doctor_id);
        // get patient by username
        Patient patient= patientService.getByUsername(username);

        appointment.setAppointmentStatus(AppointmentStatus.PENDING);

        //save to DB
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        appointmentRepository.save(appointment);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public void confirmAppointment(long appointmentId, Principal principal) {
        Appointment appointment= appointmentRepository.findById(appointmentId).
                orElseThrow(() -> new ResourceNotFoundException("Invalid Appointment ID"));

        // ownership check
        if(!appointment.getDoctor().getUser().getUsername().equals(principal.getName())){
            throw new AppointmentStatusUpdateException("Doctor cannot update status of this appointment");
        }
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);
    }

    public void rejectAppointment(long appointmentId, Principal principal) {
        Appointment appointment= appointmentRepository.findById(appointmentId).
                orElseThrow(() -> new ResourceNotFoundException("Invalid Appointment ID"));
        // ownership check
        if(!appointment.getDoctor().getUser().getUsername().equals(principal.getName())){
            throw new AppointmentStatusUpdateException("Doctor cannot update status of this appointment");
        }
        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    public List<AppointmentRespDto> viewAppointments() {
        //(app_id, app_date, patient_id, patient name, doctor_id, doctor_specialization,  doctor_name, status)
        List<Appointment> appointmentList= appointmentRepository.viewAppointments();
        return appointmentList.stream()
                .map(AppointmentMapper :: mapToDto)
                .toList();
    }

    public void rescheduleAppointment(long appointmentId, Principal principal, RescheduleDto rescheduleDto) {
        // get appointment by id
        Appointment appointment= appointmentRepository.findById(appointmentId)
                .orElseThrow(()-> new ResourceNotFoundException("Appointment ID Invalid"));

        // Ownership check
        if (!appointment.getPatient().getUser().getUsername().equals(principal.getName())) {
            throw new AppointmentUpdateException("Patient did not book this appointment");
        }

        // Status validation
        if (appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED
                || appointment.getAppointmentStatus() == AppointmentStatus.CANCELLED) {
            throw new AppointmentUpdateException("Cannot reschedule this appointment");
        }

        // Update
        appointment.setAppointmentDate(rescheduleDto.date());
        appointment.setTimeSlot(rescheduleDto.time());
        appointment.setAppointmentStatus(AppointmentStatus.PENDING);

        appointmentRepository.save(appointment);

    }

    public Appointment getById(long appointment_id) {
        return appointmentRepository.findById(appointment_id)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid Appointment ID"));
    }

    public void saveAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public void cancelAppointment(long appointmentId, String username) {
        // get appointment by id
        Appointment appointment= appointmentRepository.findById(appointmentId)
                .orElseThrow(()-> new ResourceNotFoundException("Appointment ID Invalid"));

        // Ownership check
        if (!appointment.getPatient().getUser().getUsername().equals(username)) {
            throw new AppointmentUpdateException("Patient did not book this appointment");
        }

        // Status validation
        if (appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
            throw new AppointmentUpdateException("Cannot cancel this appointment");
        }

        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    public void bookPatientAppointment(@Valid AppointmentDto appointmentDto, long doctor_id, long patient_id) {
        // Map to Appointment
        Appointment appointment= AppointmentMapper.mapTo(appointmentDto);

        // additional DI
        Doctor doctor= new Doctor();
        doctor.setId(doctor_id);

        // get patient by username
        Patient patient= patientService.getByPatientId(patient_id);

        appointment.setAppointmentStatus(AppointmentStatus.PENDING);

        //save to DB
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        appointmentRepository.save(appointment);
    }

    public List<PatientAppointmentDto> getAppointmentsWithFilter(FilterAppointmentDto filterAppointmentDto, String username) {
        // get doctor ID using username
        Doctor doctor= doctorService.getByUsername(username);
        long doctor_id= doctor.getId();
        List<Appointment> list= appointmentRepository.getAppointmentsWithFilter(
                filterAppointmentDto.status(),
                filterAppointmentDto.name(),
                filterAppointmentDto.date(),
                doctor_id
        );

        return list.stream()
                .map(AppointmentMapper::mapToPatientAppointmentDto)
                .toList();
    }
}
