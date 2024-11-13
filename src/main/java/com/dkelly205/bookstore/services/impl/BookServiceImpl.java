package com.dkelly205.bookstore.services.impl;

import com.dkelly205.bookstore.domain.Book;
import com.dkelly205.bookstore.domain.BookEntity;
import com.dkelly205.bookstore.repositories.BookRepository;
import com.dkelly205.bookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class  BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public Book create(final Book book) {
        final BookEntity bookEntity = bookToBookEntity(book);
        final BookEntity savedBookedEntity = bookRepository.save(bookEntity);
        return bookEntityToBook(savedBookedEntity);
    }

    private BookEntity bookToBookEntity(Book book) {
        return BookEntity.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }

    private Book bookEntityToBook(BookEntity bookEntity) {
        return Book.builder()
                .isbn(bookEntity.getIsbn())
                .author(bookEntity.getAuthor())
                .title(bookEntity.getTitle())
                .build();
    }

    @Override
    public Optional<Book> findById(String isbn) {
        Optional<BookEntity> foundBook = bookRepository.findById(isbn);
        return foundBook.map(this::bookEntityToBook);
    }

    @Override
    public List<Book> listBooks() {
        final List<BookEntity> bookEntityList = bookRepository.findAll();
        return bookEntityList.stream().map(this::bookEntityToBook).toList();
    }


}
