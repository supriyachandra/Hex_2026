package com.project.amazecare.controller;

import com.project.amazecare.dto.*;
import com.project.amazecare.service.DoctorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/doctor")
@CrossOrigin(origins = "http://localhost:5173/")
public class DoctorController {

    private final DoctorService doctorService;

    // Access: Admin (add doctor)
    @PostMapping("/add-doctor")
    public ResponseEntity<HttpStatus> addDoctor(@Valid @RequestBody DoctorSignUpDto doctorSignUpDto){
        doctorService.addDoctor(doctorSignUpDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // GET: list of all doctors --- Access: permit all (update in security)
    @GetMapping("/get-all/v1")
    public DoctorPaginationDto getAllDoctors(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam (value = "size", defaultValue = "5", required = false) int size
    ){
        return doctorService.getAllDoctors(page, size);
    }

    // Get: Search doctors (filter by specialization--- dropdown)
    // access- authenticated
    @GetMapping("get-by/{specialization_id}")
    public List<DoctorReqDto> filterBySpecializationId(@PathVariable long specialization_id){
        return doctorService.filterDoctorBySpecializationId(specialization_id);
    }

    // doctor info by id (authenticated)
    @GetMapping("get/{id}")
    public DoctorRespDto getById(@PathVariable long id){
        return doctorService.findById(id);
    }

    @GetMapping("filter/name/spec")
    public List<DoctorRespDto> filterBySpecAndName(@RequestParam(required = false) String name,
                                                   @RequestParam(required = false) Long spec_id){
        return doctorService.filterBySpecAndName(name, spec_id);
    }

    @GetMapping("/get-one/v1")
    public DoctorRespDto getDoctor(Principal principal){
        return doctorService.getDoctors(principal.getName());
    }

    @GetMapping("/get-one/v2")
    public DoctorRespDto getDoctorById(@RequestParam Long doctorId){
        return doctorService.getDoctor(doctorId);
    }

    @GetMapping("/available")
    public List<DoctorRespDto> DoctorByDate(@RequestParam(required = false) LocalDate date){
        return doctorService.doctorByDate(date);
    }

    @GetMapping("/get-all/v2")
    public List<DoctorRespDto> all(){
        return doctorService.all();
    }

    @GetMapping("/count-doc")
    public Long totalDoctors(){
        return doctorService.totalDoctors();
    }


}
