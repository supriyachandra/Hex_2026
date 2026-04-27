package com.springboot.myapp.repository;

import com.springboot.myapp.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
