package com.springboot.myapp.controller;

import com.springboot.myapp.dto.ExecutiveDto;
import com.springboot.myapp.enums.JobTitle;
import com.springboot.myapp.model.Executive;
import com.springboot.myapp.service.ExecutiveService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/executive")
@CrossOrigin(origins = "http://localhost:5173/")
public class ExecutiveController {

    private final ExecutiveService executiveService;

    @PostMapping("/add")
    public Executive insertExecutive(@Valid @RequestBody ExecutiveDto executiveDto){
        return executiveService.insertExecutive(executiveDto);
    }

    @GetMapping("/get-all")
    public List<ExecutiveDto> getAllData(
            @RequestParam (value= "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue= "1", required = false) int size){
        return executiveService.getAllData(page, size);
    }

    @GetMapping("/get/id/{id}")
    public ExecutiveDto getById(@PathVariable Long id){
        return executiveService.getById(id);
    }

    @GetMapping("/get/jobTitle/{jobTitle}")
    public ExecutiveDto getByJobTitle(@PathVariable JobTitle jobTitle){
        return executiveService.getByJobTitle(jobTitle);
    }
}
