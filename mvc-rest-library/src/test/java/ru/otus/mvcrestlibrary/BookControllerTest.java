package ru.otus.mvcrestlibrary;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.mvcrestlibrary.controllers.BookController;
import ru.otus.mvcrestlibrary.dto.BookDTO;
import ru.otus.mvcrestlibrary.models.Author;
import ru.otus.mvcrestlibrary.models.Genre;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@DisplayName("Тест для книг")
class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookController bookController;

    @Test
    @DisplayName("Все книги")
    public void testAllBooks() throws Exception {
        when(bookController.books()).thenReturn(Arrays.asList(new BookDTO(1L, "test1", new Author("123", "123"), new Genre("123")),
                new BookDTO(2L, "test2", new Author("123", "123"), new Genre("123"))));

        mvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", is("test1")))
                .andExpect(jsonPath("$[1].title", is("test2")))
                .andExpect(jsonPath("$[0].author.name", is("123")));
    }

    @Test
    @DisplayName("Книга по id")
    public void testBookById() throws Exception {
        ResponseEntity<BookDTO> response = ResponseEntity.ok().body(new BookDTO(1L, "test", new Author("123", "123"), new Genre("123")));
        when(bookController.book(any())).thenReturn(response);

        mvc.perform(get("/api/books/{bookId}", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is("test")));
    }


    @Test
    @DisplayName("Создание")
    public void testCreateBook() throws Exception {
        BookDTO book = new BookDTO(1L, "test", new Author(1L, null, null), new Genre(1L, null));
        ResponseEntity<BookDTO> response = ResponseEntity.ok().body(book);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(book);
        when(bookController.createBook(any())).thenReturn(response);

        mvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is("test")))
                .andReturn();
    }

    @Test
    @DisplayName("Обновление")
    public void testUpdateBook() throws Exception {
        BookDTO book = new BookDTO(1L, "test", new Author(1L, null, null), new Genre(1L, null));
        ResponseEntity<BookDTO> response = ResponseEntity.ok().body(book);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(book);
        when(bookController.createBook(any())).thenReturn(response);

        mvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is("test")))
                .andReturn();
    }

}
