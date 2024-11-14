package com.dkelly205.bookstore.services;

import com.dkelly205.bookstore.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookService {

    Book create(Book book);

    Optional<Book> findById(String isbn);

    Page<Book> findBooks(Pageable pageable);

    void deleteBookById(String isbn);

    Book updateBook(Book updatedBook);
}
