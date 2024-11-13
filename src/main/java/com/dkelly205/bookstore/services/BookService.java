package com.dkelly205.bookstore.services;

import com.dkelly205.bookstore.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book create(Book book);

    Optional<Book> findById(String isbn);

    List<Book> listBooks();
}
