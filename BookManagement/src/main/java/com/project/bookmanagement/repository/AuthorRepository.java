package com.project.bookmanagement.repository;

import com.project.bookmanagement.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("select a from Author a where a.user.username=?1")
    Author findByUsername(String username);
}
