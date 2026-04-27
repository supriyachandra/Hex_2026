package com.project.amazecare.controller;

import com.project.amazecare.dto.TestReqDto;
import com.project.amazecare.service.TestsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tests")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class TestsController {
    private final TestsService testsService;
}
