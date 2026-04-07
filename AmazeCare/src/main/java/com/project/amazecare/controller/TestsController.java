package com.project.amazecare.controller;

import com.project.amazecare.dto.TestReqDto;
import com.project.amazecare.service.TestsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tests")
@AllArgsConstructor
public class TestsController {
    private final TestsService testsService;

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addTest(@RequestBody TestReqDto testReqDto){
        testsService.addTest(testReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
