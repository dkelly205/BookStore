package com.dkelly205.bookstore.controllers;

import com.dkelly205.bookstore.domain.Book;
import com.dkelly205.bookstore.services.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.dkelly205.bookstore.TestData.testBook;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Test
    public void testThatBookIsCreated() throws Exception {

        final Book book = testBook();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);
        mockMvc.perform(post("/books/" + book.getIsbn())
                .contentType(MediaType.APPLICATION_JSON).content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()));

    }

    @Test
    public void testThatRetrieveBookReturns404WhenBookIsNotFound() throws Exception {
        mockMvc.perform(get("/books/123445" ))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void testThatRetrieveBookReturns200WhenBookExists() throws Exception {
        final Book book = testBook();
        bookService.create(book);

        mockMvc.perform(get("/books/" + book.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()));

    }

}
