package com.dkelly205.bookstore.services.impl;

import com.dkelly205.bookstore.domain.Book;
import com.dkelly205.bookstore.domain.BookEntity;
import com.dkelly205.bookstore.mapper.BookMapper;
import com.dkelly205.bookstore.repositories.BookRepository;
import com.dkelly205.bookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class  BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(final BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }


    @Override
    public Book create(final Book book) {
        final BookEntity bookEntity = bookMapper.bookToBookEntity(book);
        final BookEntity savedBookedEntity = bookRepository.save(bookEntity);
        return bookMapper.bookEntityToBook(savedBookedEntity);
    }





    @Override
    public Optional<Book> findById(String isbn) {
        Optional<BookEntity> foundBook = bookRepository.findById(isbn);
        return foundBook.map(bookMapper::bookEntityToBook);
    }

    @Override
    public Page<Book> findBooks(Pageable pageable) {
        Page<BookEntity> bookEntities =  bookRepository.findAll(pageable);

        return bookEntities.map(bookMapper::bookEntityToBook);


    }


}
