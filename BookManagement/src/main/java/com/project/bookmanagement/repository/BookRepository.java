package com.project.bookmanagement.repository;

import com.project.bookmanagement.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByISBN(String isbn);

    @Query("select b from Book b where b.ISBN=?1")
    Optional<Book> getByISBN(String isbn);

    @Modifying
    @Transactional
    @Query("delete from Book b where b.ISBN=?1")
    void deleteByISBN(String isbn);

    @Query("select b from Book b where b.authorEntity.user.username=?1")
    Page<Book> getBooksByAuthor(String username, Pageable pageable);
}
