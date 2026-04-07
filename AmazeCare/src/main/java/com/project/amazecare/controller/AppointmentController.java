package com.project.amazecare.controller;

import com.project.amazecare.dto.*;
import com.project.amazecare.service.AppointmentService;
import com.project.amazecare.service.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;

    // Access: PATIENT
    @PostMapping("/book/{doctor_id}")
    public ResponseEntity<?> bookAppointment(@Valid @RequestBody AppointmentDto appointmentDto,
                                             @PathVariable long doctor_id,
                                             Principal principal){
        return appointmentService.bookAppointment(appointmentDto, principal.getName(), doctor_id);
    }

    // Book Appointment--- By admin (add in securityconfig)
    @PostMapping("book/{doctor_id}/{patient_id}")
    public ResponseEntity<?> bookPatientAppointment(@Valid @RequestBody AppointmentDto appointmentDto,
                                                    @PathVariable long doctor_id,
                                                    @PathVariable long patient_id){
        appointmentService.bookPatientAppointment(appointmentDto, doctor_id, patient_id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // edit Appointment status -- doctor clicks confirm button
    @PutMapping("/confirm/{appointment_id}")
    public ResponseEntity<?> confirmAppointment(@PathVariable long appointment_id,
                                                Principal principal){
        appointmentService.confirmAppointment(appointment_id, principal);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // edit Appointment status -- doctor clicks reject button
    @PutMapping("/reject/{appointment_id}")
    public ResponseEntity<?> rejectAppointment(@PathVariable long appointment_id,
                                               Principal principal){
        appointmentService.rejectAppointment(appointment_id, principal);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // patient can reschedule appointment--- access: patient
    @PutMapping("/reschedule/{appointment_id}")
    public ResponseEntity<?> rescheduleAppointment(@PathVariable long appointment_id,
                                                   Principal principal,
                                                   @RequestBody RescheduleDto rescheduleDto){
        appointmentService.rescheduleAppointment(appointment_id, principal, rescheduleDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // cancel appointmnet- patient
    @PutMapping("/cancel/{appointment_id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable long appointment_id,
                                               Principal principal){
        appointmentService.cancelAppointment(appointment_id, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // view appointments- for admin
    //(app_id, app_date, patient_id, patient name, doctor_id, doctor_specialization,  doctor_name, status)
    @GetMapping("/get-all/v1")
    public List<AppointmentRespDto> viewAppointments(){
        return appointmentService.viewAppointments();
    }


    // api to get appointments, with patient name, date, time, status
    // doctor should get HIS patient's appointments only so use principal
    // dynamic filtering, use filter using status, date, and name of patient
    @GetMapping("/get-all/filter")
    public List<PatientAppointmentDto> getAppointmentsWithFilter(
            @RequestBody FilterAppointmentDto filterAppointmentDto,
            Principal principal){
        return appointmentService.getAppointmentsWithFilter(filterAppointmentDto, principal.getName());
    }
}
