package com.project.bookmanagement.service;

import com.project.bookmanagement.dto.BookResDto;
import com.project.bookmanagement.exception.ResourceNotFoundException;
import com.project.bookmanagement.model.Book;
import com.project.bookmanagement.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void getAllBooksTest(){
        // prepare books data for list
        Book book1= new Book();
        book1.setId(1L);
        book1.setTitle("Six of crows");
        book1.setAuthor("Leigh Bardugo");
        book1.setISBN("ISBN1");
        book1.setPublicationYear("2017");
        book1.setCreatedAt(Instant.now());
        book1.setUpdatedAt(Instant.now());

        Book book2= new Book();
        book2.setId(2L);
        book2.setTitle("Six of crows");
        book2.setAuthor("Leigh Bardugo");
        book2.setISBN("ISBN1");
        book2.setPublicationYear("2017");
        book2.setCreatedAt(Instant.now());
        book2.setUpdatedAt(Instant.now());

        List<Book> list= List.of(book1, book2);

        // create page of this list
        Page<Book> bookPage1= new PageImpl<>(list);

        Pageable pageable1= PageRequest.of(0, 2);

        Mockito.when(bookRepository.findAll(pageable1)).thenReturn(bookPage1);

        Assertions.assertEquals(2, bookService.getAllBooks(0,2).size());


        // case 2: when page is 0 and size is 1
        Page<Book> bookPage2= new PageImpl<>(list.subList(0,1));

        Pageable pageable2= PageRequest.of(0, 1);

        Mockito.when(bookRepository.findAll(pageable2)).thenReturn(bookPage2);

        Assertions.assertEquals(1, bookService.getAllBooks(0,1).size());
    }

    @Test
    public void getBookByISBNIfExists(){
        // prepare book data
        Book book= new Book();
        book.setId(1L);
        book.setTitle("Six of crows");
        book.setAuthor("Leigh Bardugo");
        book.setISBN("ISBN1");
        book.setPublicationYear("2017");
        book.setCreatedAt(Instant.now());
        book.setUpdatedAt(Instant.now());

        // return value: BookResDto
        BookResDto bookResDto= new BookResDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getISBN(),
                book.getPublicationYear()
        );

        Mockito.when(bookRepository.getByISBN("ISBN1")).thenReturn(Optional.of(book));

        Assertions.assertEquals(bookResDto, bookService.getBookByISBN("ISBN1"));

        Mockito.verify(bookRepository, Mockito.times(1)).getByISBN("ISBN1");
    }

    @Test
    public void getBookByISBNIfNotExists(){

        Mockito.when(bookRepository.getByISBN("ISBN1")).thenReturn(Optional.empty());

        Exception e= Assertions.assertThrows(ResourceNotFoundException.class,
                ()-> bookService.getBookByISBN("ISBN1"));

        Assertions.assertEquals("ISBN Invalid!", e.getMessage());

        Mockito.verify(bookRepository, Mockito.times(1)).getByISBN("ISBN1");
    }

    @Test
    public void updateBookTitleIfIdExists(){
        // prepare book data
        Book book= new Book();
        book.setId(1L);
        book.setTitle("Six of crows");
        book.setAuthor("Leigh Bardugo");
        book.setISBN("ISBN1");
        book.setPublicationYear("2017");
        book.setCreatedAt(Instant.now());
        book.setUpdatedAt(Instant.now());


        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.updateBookTitleById(1L, "New Title");

        Assertions.assertEquals("New Title", book.getTitle());

    }

    @Test
    public void updateBookTitleIfIdDoesNotExist(){

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e= Assertions.assertThrows(ResourceNotFoundException.class,
                ()-> bookService.updateBookTitleById(1L, "TITLE"));

        Assertions.assertEquals("Invalid Book ID", e.getMessage());

    }

    @Test
    public void deleteBookByISBNIfNotExists(){

        Mockito.when(bookRepository.getByISBN("ISBN1")).thenReturn(Optional.empty());

        Exception e= Assertions.assertThrows(ResourceNotFoundException.class,
                ()-> bookService.deleteBookByISBN("ISBN1"));

        Assertions.assertEquals("ISBN Invalid!", e.getMessage());

        Mockito.verify(bookRepository, Mockito.times(1)).getByISBN("ISBN1");
    }

    @Test
    public void deleteBookByISBNIfExists(){
        // prepare book data
        Book book= new Book();
        book.setId(1L);
        book.setTitle("Six of crows");
        book.setAuthor("Leigh Bardugo");
        book.setISBN("ISBN1");
        book.setPublicationYear("2017");
        book.setCreatedAt(Instant.now());
        book.setUpdatedAt(Instant.now());


        Mockito.when(bookRepository.getByISBN("ISBN1")).thenReturn(Optional.of(book));
        bookService.deleteBookByISBN("ISBN1");

        //Assertions.assertEquals(null, book);
        Mockito.verify(bookRepository).deleteByISBN("ISBN1");
    }
}
