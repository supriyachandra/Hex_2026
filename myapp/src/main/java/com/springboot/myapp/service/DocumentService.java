package com.springboot.myapp.service;

import com.springboot.myapp.dto.DocumentDto;
import com.springboot.myapp.mapper.DocumentMapper;
import com.springboot.myapp.model.Customer;
import com.springboot.myapp.model.Document;
import com.springboot.myapp.repository.DocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final CustomerService customerService;

    private final static String UPLOAD_PATH= "D:/HEX_FSD_march/trs-ui/public/uploads";

    public DocumentDto upload(String customerUsername, MultipartFile file) throws IOException {
        Customer customer= customerService.getByUsername(customerUsername);

        // Create a File handler to save the directory path
        File directory= new File(UPLOAD_PATH);

        String fileName= file.getOriginalFilename();

        Path path= Paths.get(UPLOAD_PATH + "/"+ fileName);

        Files.write(path, file.getBytes());

        Document doc= new Document();
        doc.setCustomer(customer);
        doc.setProfileImage(fileName);
        documentRepository.save(doc);

        return DocumentMapper.mapToDto(doc);

    }
}
