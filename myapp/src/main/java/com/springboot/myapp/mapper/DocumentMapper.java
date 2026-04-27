package com.springboot.myapp.mapper;

import com.springboot.myapp.dto.DocumentDto;
import com.springboot.myapp.model.Document;

public class DocumentMapper {

    public static DocumentDto mapToDto(Document document){
        return new DocumentDto(
                document.getId(),
                document.getCustomer().getName(),
                document.getProfileImage()
        );
    }
}
