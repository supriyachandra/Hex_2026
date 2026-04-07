package com.project.bookmanagement.mapper;

import com.project.bookmanagement.dto.BookReqDto;
import com.project.bookmanagement.dto.BookResDto;
import com.project.bookmanagement.model.Book;

public class BookMapper {
    public static Book MapToEntity(BookReqDto bookReqDto){
        Book book= new Book();
        book.setISBN(bookReqDto.ISBN());
        book.setTitle(bookReqDto.name());
        book.setAuthor(bookReqDto.author_name());
        book.setPublicationYear(bookReqDto.publication_year());
        return book;
    }

    public static BookResDto mapToBookResDto(Book book){
        return new BookResDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getISBN(),
                book.getPublicationYear()
        );
    }
}
