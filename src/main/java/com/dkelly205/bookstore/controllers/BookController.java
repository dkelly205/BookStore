package com.dkelly205.bookstore.controllers;

import com.dkelly205.bookstore.domain.Book;
import com.dkelly205.bookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/{isbn}")
    public ResponseEntity<Book> createBook(@PathVariable final String isbn, @RequestBody final Book book){
        book.setIsbn(isbn);
        final Book savedBook = bookService.create(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> retrieveBook(@PathVariable final String isbn) {
        final Optional<Book> foundBook = bookService.findById(isbn);

        return foundBook.map(book -> new ResponseEntity<Book>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping()
    public ResponseEntity<Page<Book>> retrieveBooks(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        final Page<Book> foundBooks = bookService.findBooks(pageable);
        return new ResponseEntity<>(foundBooks, HttpStatus.OK);

    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity deleteBook(@PathVariable final String isbn){
        bookService.deleteBookById(isbn);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity updateBook(@PathVariable final String isbn, @RequestBody Book updatedBook) {

        updatedBook.setIsbn(isbn);
        Book updated = bookService.updateBook(updatedBook);
        if(updated == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }
}
