package com.dkelly205.bookstore.services.impl;


import com.dkelly205.bookstore.domain.Book;
import com.dkelly205.bookstore.domain.BookEntity;
import com.dkelly205.bookstore.mapper.BookMapper;
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

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl underTest;

    @Test
    public void testThatBookIsSaved() {
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();

        when(bookMapper.bookToBookEntity(book)).thenReturn(bookEntity);
        when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);
        when(bookMapper.bookEntityToBook(eq(bookEntity))).thenReturn(book);
        final Book result = underTest.create(book);

        verify(bookMapper).bookToBookEntity(eq(book));
        verify(bookRepository).save(eq(bookEntity));
        verify(bookMapper).bookEntityToBook(eq(bookEntity));
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
        when(bookMapper.bookEntityToBook(eq(bookEntity))).thenReturn(book);
        final Optional<Book> result = underTest.findById(book.getIsbn());

        verify(bookRepository).findById(book.getIsbn());
        verify(bookMapper).bookEntityToBook(eq(bookEntity));
        assertEquals(Optional.of(book), result);
    }

    @Test
    public void testListBooksReturnsEmptyListWhenNoBooksExist(){
        final List<Book> result = underTest.listBooks();
        assertEquals(0, result.size());
    }

    @Test
    public void testListBooksReturnsBooksWhenBooksExist(){
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();

        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));
        when(bookMapper.bookEntityToBook(eq(bookEntity))).thenReturn(book);

        final List<Book> result = underTest.listBooks();

        verify(bookRepository).findAll();
        verify(bookMapper).bookEntityToBook(eq(bookEntity));
        assertEquals(1, result.size());
        assertEquals(book, result.get(0));

    }

}
