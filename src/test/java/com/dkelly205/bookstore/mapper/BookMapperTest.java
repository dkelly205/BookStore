package com.dkelly205.bookstore.mapper;

import com.dkelly205.bookstore.domain.Book;
import com.dkelly205.bookstore.domain.BookEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.dkelly205.bookstore.TestData.testBook;
import static com.dkelly205.bookstore.TestData.testBookEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookMapperTest {

    private BookMapper bookMapper;

    @BeforeEach
    public void setUp() {
        bookMapper = BookMapper.INSTANCE;
    }

    @Test
    public void testBookToBookEntity() {
        Book book = testBook();

        BookEntity bookEntity = bookMapper.bookToBookEntity(book);

        assertEquals(book.getIsbn(), bookEntity.getIsbn());
        assertEquals(book.getTitle(), bookEntity.getTitle());
        assertEquals(book.getAuthor(), bookEntity.getAuthor());
    }

    @Test
    public void testBookEntityToBook() {
        BookEntity bookEntity = testBookEntity();

        Book book = bookMapper.bookEntityToBook(bookEntity);

        assertEquals(bookEntity.getIsbn(), book.getIsbn());
        assertEquals(bookEntity.getTitle(), book.getTitle());
        assertEquals(bookEntity.getAuthor(), book.getAuthor());
    }
}
