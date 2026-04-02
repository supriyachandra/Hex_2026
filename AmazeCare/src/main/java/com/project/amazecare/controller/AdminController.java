package com.project.amazecare.controller;

import com.project.amazecare.dto.AddAdminDto;
import com.project.amazecare.dto.AppointmentRespDto;
import com.project.amazecare.dto.DoctorReqDto;
import com.project.amazecare.repository.AppointmentRepository;
import com.project.amazecare.service.AdminService;
import com.project.amazecare.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;


    // for now permit all, then only admin
    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody AddAdminDto addAdminDto){
        adminService.addAdmin(addAdminDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
