package com.dkelly205.bookstore.services.impl;


import com.dkelly205.bookstore.domain.Book;
import com.dkelly205.bookstore.domain.BookEntity;
import com.dkelly205.bookstore.mapper.BookMapper;
import com.dkelly205.bookstore.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.dkelly205.bookstore.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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
    public void testListBooksReturnsEmptyFindWhenNoBooksExist(){
        Pageable pageable = PageRequest.of(0, 3);
        final Page<Book> result = underTest.findBooks(pageable);
        assertEquals(0, result.getTotalElements());
    }

    @ParameterizedTest
    @CsvSource({
            "0, 2",
            "1, 2",
            "2, 2"
    })
    public void testFindBooksReturnsBooksWhenBooksExist(int page, int size) {
        // Arrange
        final List<Book> books = testBooks();
        final List<BookEntity> bookEntities = testBookEntities();

        Pageable pageable = PageRequest.of(page, size);

        // Calculate the start and end indices for the requested page
        int start = Math.min(page * size, bookEntities.size());
        int end = Math.min((page + 1) * size, bookEntities.size());

        // Create a sublist for the current page
        List<BookEntity> pagedBookEntities = bookEntities.subList(start, end);
        List<Book> expectedPagedBooks = books.subList(start, end);

        Page<BookEntity> bookEntitiesPage = new PageImpl<>(pagedBookEntities, pageable, bookEntities.size());
        when(bookRepository.findAll(pageable)).thenReturn(bookEntitiesPage);

        for (int i = 0; i < pagedBookEntities.size(); i++) {
            when(bookMapper.bookEntityToBook(eq(pagedBookEntities.get(i)))).thenReturn(expectedPagedBooks.get(i));
        }

        final Page<Book> result = underTest.findBooks(pageable);

        verify(bookRepository).findAll(pageable);
        verify(bookMapper, times(pagedBookEntities.size())).bookEntityToBook(any(BookEntity.class));
        assertEquals(bookEntities.size(), result.getTotalElements());
        assertEquals(expectedPagedBooks, result.getContent());
    }

}
