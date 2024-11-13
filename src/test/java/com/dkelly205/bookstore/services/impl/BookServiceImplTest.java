package com.dkelly205.bookstore.services.impl;


import com.dkelly205.bookstore.domain.Book;
import com.dkelly205.bookstore.domain.BookEntity;
import com.dkelly205.bookstore.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;

import java.util.List;
import java.util.Optional;

import static com.dkelly205.bookstore.TestData.testBook;
import static com.dkelly205.bookstore.TestData.testBookEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl underTest;

    @Test
    public void testThatBookIsSaved() {
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();

        when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);
        final Book result = underTest.create(book);

        verify(bookRepository).save(eq(bookEntity));
        assertEquals(book, result);
    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoBook(){
        String isbn = "12332424556";

        when(bookRepository.findById(isbn)).thenReturn(Optional.empty());
        final Optional<Book> result = underTest.findById(isbn);

        verify(bookRepository).findById(isbn);
        assertTrue(result.isEmpty());

    }

    @Test
    public void testThatBookIsFoundWhenBookExists() {
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();

        when(bookRepository.findById(book.getIsbn())).thenReturn(Optional.of(bookEntity));
        final Optional<Book> result = underTest.findById(book.getIsbn());

        verify(bookRepository).findById(book.getIsbn());
        assertEquals(Optional.of(book), result);
    }

    @Test
    public void testListBooksReturnsEmptyListWhenNoBooksExist(){
        final List<Book> result = underTest.listBooks();
        assertEquals(0, result.size());
    }

    @Test
    public void testListBooksReturnsBooksWhenBooksExist(){
        final BookEntity bookEntity = testBookEntity();

        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));
        final List<Book> result = underTest.listBooks();

        verify(bookRepository).findAll();
        assertEquals(1, result.size());
    }

}
