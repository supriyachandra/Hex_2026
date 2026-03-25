package com.springboot.myapp.service;

import com.springboot.myapp.dto.ExecutiveDto;
import com.springboot.myapp.enums.JobTitle;
import com.springboot.myapp.exceptions.ResourceNotFoundException;
import com.springboot.myapp.mapper.ExecutiveMapper;
import com.springboot.myapp.model.Customer;
import com.springboot.myapp.model.Executive;
import com.springboot.myapp.repository.ExecutiveRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExecutiveService {

    private final ExecutiveRepository executiveRepository;

    public Executive insertExecutive(@Valid ExecutiveDto executiveDto) {
        Executive executive= ExecutiveMapper.mapTo(executiveDto);
        return executiveRepository.save(executive);
    }

    public List<ExecutiveDto> getAllData(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<Executive> executivePage= executiveRepository.findAll(pageable);

        return executivePage.stream()
                .map(ExecutiveMapper :: mapToDto)
                .toList();
    }

    public ExecutiveDto getById(long id) {
        Executive executive= executiveRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ID Invalid")
        );

        return new ExecutiveDto(
                executive.getName(),
                executive.getJobTitle()
        );
    }

    public ExecutiveDto getByJobTitle(JobTitle jobTitle) {
        return executiveRepository.findByJobTitle(jobTitle);
    }
}
