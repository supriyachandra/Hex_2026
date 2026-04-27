package com.project.bookmanagement.service;

import com.project.bookmanagement.dto.BookReqDto;
import com.project.bookmanagement.dto.BookResDto;
import com.project.bookmanagement.exception.ResourceNotFoundException;
import com.project.bookmanagement.mapper.BookMapper;
import com.project.bookmanagement.model.Author;
import com.project.bookmanagement.model.Book;
import com.project.bookmanagement.repository.BookRepository;
import com.project.bookmanagement.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public void addBook(BookReqDto bookReqDto) {
        Author author= authorService.findById(bookReqDto.author_id());
        log.atLevel(Level.INFO).log("Called addBook");
        Book book= BookMapper.MapToEntity(bookReqDto);
        book.setAuthorEntity(author);
        bookRepository.save(book);

        log.atLevel(Level.INFO).log("Book Added1");
    }

    public List<BookResDto> getAllBooks(int page, int size) {
        log.atLevel(Level.INFO).log("Called getAllBooks: get all Books with pagination");
        // create pageable of size and page
        Pageable pageable= PageRequest.of(page, size);

        Page<Book> bookPage= bookRepository.findAll(pageable);

        return bookPage.stream()
                .map(BookMapper:: mapToBookResDto)
                .toList();
    }

    public BookResDto getBookByISBN(String isbn) {
        log.atLevel(Level.INFO).log("Called getBookByISBN: get book by ISBN if exists");

        Book book= bookRepository.getByISBN(isbn)
                .orElseThrow(()-> new ResourceNotFoundException("ISBN Invalid!"));
        return BookMapper.mapToBookResDto(book);
    }

    public void updateBookTitleById(long id, String title) {
        log.atLevel(Level.INFO).log("Called updateBookTitleById: update title of book by given ID");

        Book book= bookRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid Book ID"));

        book.setTitle(title);
        bookRepository.save(book);

        log.atLevel(Level.INFO).log("Book Title Updated!");
    }

    public void deleteBookByISBN(String isbn) {
        log.atLevel(Level.INFO).log("Called deleteBookByISBN: DELETE book by ISBN if exists");

        Book book= bookRepository.getByISBN(isbn)
                .orElseThrow(()-> new ResourceNotFoundException("ISBN Invalid!"));

        bookRepository.deleteByISBN(isbn);

        log.atLevel(Level.INFO).log("Book deleted!");
    }

    public List<BookResDto> getAllBooksByAuthor(String username, int page, int size) {
        // check author is valid
        Author author= authorService.findByUsername(username);

        Pageable pageable= PageRequest.of(page, size);

        Page<Book> bookList= bookRepository.getBooksByAuthor(username, pageable);


        return bookList.stream()
                .map(BookMapper::mapToBookResDto)
                .toList();
    }
}
