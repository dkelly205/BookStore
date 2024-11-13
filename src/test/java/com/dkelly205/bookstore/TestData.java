package com.dkelly205.bookstore;

import com.dkelly205.bookstore.domain.Book;
import com.dkelly205.bookstore.domain.BookEntity;

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
}
