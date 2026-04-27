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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private String invalidAppointmentMsg= "Invalid Appointment ID";

    public ResponseEntity<HttpStatus> bookAppointment(AppointmentDto appointmentDto, String username, long doctor_id) {
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
        //appointment.setPatientType(PatientType.OPD);
        appointment.setPatient(patient);

        appointmentRepository.save(appointment);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public void confirmAppointment(long appointmentId, Principal principal) {
        Appointment appointment= appointmentRepository.findById(appointmentId).
                orElseThrow(() -> new ResourceNotFoundException(invalidAppointmentMsg));

        // ownership check
        if(!appointment.getDoctor().getUser().getUsername().equals(principal.getName())){
            throw new AppointmentStatusUpdateException("Doctor cannot update status of this appointment");
        }
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);
    }

    public void rejectAppointment(long appointmentId, Principal principal) {
        Appointment appointment= appointmentRepository.findById(appointmentId).
                orElseThrow(() -> new ResourceNotFoundException(invalidAppointmentMsg));
        // ownership check
        if(!appointment.getDoctor().getUser().getUsername().equals(principal.getName())){
            throw new AppointmentStatusUpdateException("Doctor cannot update status of this appointment");
        }
        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
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
        //appointment.setAppointmentStatus(AppointmentStatus.PENDING);

        appointmentRepository.save(appointment);

    }

    public Appointment getById(long appointment_id) {
        return appointmentRepository.findById(appointment_id)
                .orElseThrow(()-> new ResourceNotFoundException(invalidAppointmentMsg));
    }

    public void saveAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public void cancelAppointment(long appointmentId, String username) {
        // get appointment by id
        Appointment appointment= appointmentRepository.findById(appointmentId)
                .orElseThrow(()-> new ResourceNotFoundException(invalidAppointmentMsg));

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

    public List<AppointmentRespDto> getUpcomingAppointments(String username) {
        List<Appointment> appointmentList= appointmentRepository.getUpcomingAppointments(username);
        return appointmentList.stream()
                .map(AppointmentMapper :: mapToDto)
                .toList();
    }

    public AppResPaginationDto getAppointments(String username, int page, int size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<Appointment> appointmentPage=
                appointmentRepository.getAppointments(username, pageable);

        List<AppointmentRespDto> list= appointmentPage.stream()
                .map(AppointmentMapper ::mapToDto)
                .toList();

        long records= appointmentPage.getTotalElements();
        int pages= appointmentPage.getTotalPages();

        return new AppResPaginationDto(
                list,
                records,
                pages
        );
    }

    public List<AppointmentRespDto> pendingApps(String doctor_username) {
        List<Appointment> appointmentList=
                appointmentRepository.getUpcomingAppointmentsDoctor(doctor_username);

        return appointmentList.stream()
                .filter(appointment ->
                        appointment.getAppointmentStatus().equals(AppointmentStatus.PENDING))
                .map(AppointmentMapper:: mapToDto)
                .toList();
    }

    public List<AppointmentRespDto> todayConfirm(String d_username, String confirm) {
        AppointmentStatus status= AppointmentStatus.valueOf(confirm);
        List<Appointment> appointmentList=
                appointmentRepository.todayConfirm(d_username, status);

        return appointmentList.stream()
                .map(AppointmentMapper:: mapToDto)
                .toList();
    }

    public List<AppointmentRespDto> docUpcoming(String doctorUsername) {
        List<Appointment> list= appointmentRepository.getUpcomingAppointmentsDoctor(doctorUsername);

        return list.stream()
                .map(AppointmentMapper::mapToDto)
                .toList();
    }

    public void cancelApp(Long id, String username) {
        AppointmentStatus status= AppointmentStatus.valueOf("CANCELLED");
        appointmentRepository.cancelApp(id, username, status);
    }


    public AppResPaginationDto getAll(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<Appointment> appointmentPage=
                appointmentRepository.getAllUpcoming(pageable);

        List<AppointmentRespDto> list= appointmentPage.stream()
                .map(AppointmentMapper ::mapToDto)
                .toList();

        long records= appointmentPage.getTotalElements();
        int pages= appointmentPage.getTotalPages();

        return new AppResPaginationDto(
                list,
                records,
                pages
        );
    }

    public List<AppointmentRespDto> allToday() {
        return appointmentRepository.allToday();
    }
}
