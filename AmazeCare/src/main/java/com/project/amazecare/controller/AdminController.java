package com.project.amazecare.controller;

import com.project.amazecare.dto.AddAdminDto;
import com.project.amazecare.dto.AdminRespDto;
import com.project.amazecare.service.AdminService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class AdminController {

    private final AdminService adminService;


    // for now permit all, then only admin
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addAdmin(@RequestBody AddAdminDto addAdminDto){
        adminService.addAdmin(addAdminDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get-admin")
    public AdminRespDto fetchAdmin(Principal principal){
        return adminService.getAdmin(principal.getName());
    }

}
