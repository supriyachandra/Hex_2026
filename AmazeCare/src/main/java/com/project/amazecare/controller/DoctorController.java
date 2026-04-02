package com.project.amazecare.controller;

import com.project.amazecare.dto.DoctorReqDto;
import com.project.amazecare.dto.DoctorRespDto;
import com.project.amazecare.dto.DoctorSignUpDto;
import com.project.amazecare.service.DoctorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    // Access: Admin (add doctor)
    @PostMapping("/add-doctor")
    public ResponseEntity<?> addDoctor(@Valid @RequestBody DoctorSignUpDto doctorSignUpDto){
        doctorService.addDoctor(doctorSignUpDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // GET: list of all doctors --- Access: permit all (update in security)
    @GetMapping("/get-all")
    public List<DoctorReqDto> getAllDoctors(
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
}
