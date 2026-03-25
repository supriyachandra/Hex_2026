package com.springboot.myapp.mapper;

import com.springboot.myapp.dto.ExecutiveDto;
import com.springboot.myapp.model.Customer;
import com.springboot.myapp.model.Executive;
import jakarta.validation.Valid;

public class ExecutiveMapper {

    public static Executive mapTo(@Valid ExecutiveDto executiveDto) {
        Executive executive= new Executive();
        executive.setName(executiveDto.name());
        executive.setJobTitle(executiveDto.jobTitle());

        return executive;
    }

    public static ExecutiveDto mapToDto(Executive executive){
        return new ExecutiveDto(
                executive.getName(),
                executive.getJobTitle()
        );
    }
}
