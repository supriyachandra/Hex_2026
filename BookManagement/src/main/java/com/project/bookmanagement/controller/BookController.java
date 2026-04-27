package com.project.bookmanagement.controller;

import com.project.bookmanagement.dto.BookReqDto;
import com.project.bookmanagement.dto.BookResDto;
import com.project.bookmanagement.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    // add a new book
    @PostMapping("/add")
    public ResponseEntity<?> addBook(@Valid @RequestBody BookReqDto bookReqDto){
        bookService.addBook(bookReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // retrieve all books
    @GetMapping("/get-all")
    public List<BookResDto> getAllBooks(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size){
        return bookService.getAllBooks(page, size);
    }

    // retrieve book by isbn
    @GetMapping("get-by/{isbn}")
    public BookResDto getBookByISBN(@PathVariable String isbn){
        return bookService.getBookByISBN(isbn);
    }

    // update book's name by id
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTitleById(@PathVariable long id,
                                             @RequestParam String title){
        bookService.updateBookTitleById(id, title);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // delete book by isbn
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteByISBN(@RequestParam String ISBN){
        bookService.deleteBookByISBN(ISBN);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // get list of bookrespdto by author id
    @GetMapping("/get-all/author")
    public List<BookResDto> getBooksByAuthor(Principal principal,
                                             @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                             @RequestParam(value = "size", defaultValue = "5", required = false) int size){
        return bookService.getAllBooksByAuthor(principal.getName(), page, size);
    }

}
