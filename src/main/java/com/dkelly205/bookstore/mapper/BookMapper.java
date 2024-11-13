package com.dkelly205.bookstore.mapper;

import com.dkelly205.bookstore.domain.Book;
import com.dkelly205.bookstore.domain.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    
    BookEntity bookToBookEntity(Book book);

    Book bookEntityToBook(BookEntity bookEntity);
}
