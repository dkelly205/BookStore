package com.dkelly205.bookstore;

import com.dkelly205.bookstore.domain.Book;
import com.dkelly205.bookstore.domain.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TestData {

    private TestData(){
    }

    public static Book testBook(){
        return Book.builder()
                .isbn("0123456")
                .author("James Clear")
                .title("Atomic Habits")
                .build();
    }

    public static BookEntity testBookEntity(){
        return BookEntity.builder()
                .isbn("0123456")
                .author("James Clear")
                .title("Atomic Habits")
                .build();
    }


    public static List<BookEntity> testBookEntities(){
            return Arrays.asList(
                    new BookEntity("0001", "James Clear", "Atomic Habits"),
                    new BookEntity("0002", "JK Rowling", "Harry Potter and the chamber of secrets"),
                    new BookEntity("0003", "Erich Gamma", "Design Patterns"),
                    new BookEntity("0004", "Raymond Prior", "Golf Beneath the Surface"),
                    new BookEntity("0005", "Lee Child", "Tripwire")

            );
    }

    public static List<Book> testBooks(){
        return Arrays.asList(
                new Book("0001", "James Clear", "Atomic Habits"),
                new Book("0002", "JK Rowling", "Harry Potter and the chamber of secrets"),
                new Book("0003", "Erich Gamma", "Design Patterns"),
                new Book("0004", "Raymond Prior", "Golf Beneath the Surface"),
                new Book("0005", "Lee Child", "Tripwire")

        );
    }
}
