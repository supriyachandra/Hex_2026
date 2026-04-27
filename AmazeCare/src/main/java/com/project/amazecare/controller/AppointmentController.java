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
@CrossOrigin(origins = "http://localhost:5173/")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;

    // Access: PATIENT
    @PostMapping("/book/{doctor_id}")
    public ResponseEntity<HttpStatus> bookAppointment(@Valid @RequestBody AppointmentDto appointmentDto,
                                             @PathVariable long doctor_id,
                                             Principal principal){
        return appointmentService.bookAppointment(appointmentDto, principal.getName(), doctor_id);
    }

    // Book Appointment--- By admin (add in securityconfig)
    @PostMapping("book/{doctor_id}/{patient_id}")
    public ResponseEntity<HttpStatus> bookPatientAppointment(@Valid @RequestBody AppointmentDto appointmentDto,
                                                    @PathVariable long doctor_id,
                                                    @PathVariable long patient_id){
        appointmentService.bookPatientAppointment(appointmentDto, doctor_id, patient_id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // edit Appointment status -- doctor clicks confirm button
    @PutMapping("/confirm/{appointment_id}")
    public ResponseEntity<HttpStatus> confirmAppointment(@PathVariable long appointment_id,
                                                Principal principal){
        appointmentService.confirmAppointment(appointment_id, principal);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // edit Appointment status -- doctor clicks reject button
    @PutMapping("/reject/{appointment_id}")
    public ResponseEntity<HttpStatus> rejectAppointment(@PathVariable long appointment_id,
                                               Principal principal){
        appointmentService.rejectAppointment(appointment_id, principal);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // patient can reschedule appointment--- access: patient
    @PutMapping("/reschedule/{appointment_id}")
    public ResponseEntity<HttpStatus> rescheduleAppointment(@PathVariable long appointment_id,
                                                   Principal principal,
                                                   @RequestBody RescheduleDto rescheduleDto){
        appointmentService.rescheduleAppointment(appointment_id, principal, rescheduleDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // cancel appointmnet- patient
    @PutMapping("/cancel/{appointment_id}")
    public ResponseEntity<HttpStatus> cancelAppointment(@PathVariable long appointment_id,
                                               Principal principal){
        appointmentService.cancelAppointment(appointment_id, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/get/patient")
    public AppResPaginationDto getAppointments(Principal principal,
                                               @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                               @RequestParam(name = "size", defaultValue = "5", required = false) int size){
        return appointmentService.getAppointments(principal.getName(), page, size);
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

    // patient
    @GetMapping("/upcoming")
    public List<AppointmentRespDto> getUpcomingAppointments(Principal principal){
        return appointmentService.getUpcomingAppointments(principal.getName());
    }

    @GetMapping("/upcoming/pending")
    public List<AppointmentRespDto> pendingApps(Principal principal){
        return appointmentService.pendingApps(principal.getName());
    }

    @GetMapping("/today/confirm")
    public List<AppointmentRespDto> todayConfirm(Principal principal){
        String confirm= "CONFIRMED";
        return appointmentService.todayConfirm(principal.getName(), confirm);
    }

    // doctor
    @GetMapping("/doc/upcoming")
    public List<AppointmentRespDto> docUpcoming(Principal principal){
        return appointmentService.docUpcoming(principal.getName());
    }

    @GetMapping("/get-all")
    public AppResPaginationDto getAll(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                      @RequestParam(name = "size", defaultValue = "5", required = false) int size){
        return appointmentService.getAll(page, size);
    }

    @GetMapping("/all-today")
    public List<AppointmentRespDto> allToday(){
        return appointmentService.allToday();
    }
}
