package com.springboot.myapp.controller;

import com.springboot.myapp.dto.AdminReqDto;
import com.springboot.myapp.service.AdminService;
import com.springboot.myapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody AdminReqDto adminReqDto){
        adminService.addAdmin(adminReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
