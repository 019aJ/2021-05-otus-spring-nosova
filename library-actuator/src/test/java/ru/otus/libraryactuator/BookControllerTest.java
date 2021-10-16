package ru.otus.libraryactuator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.libraryactuator.controllers.BookController;
import ru.otus.libraryactuator.dto.BookDTO;
import ru.otus.libraryactuator.models.Author;
import ru.otus.libraryactuator.models.Genre;

import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookController bookController;

    @Test
    public void testNoUserAllBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ADMIN"}
    )
    @Test
    public void testAuthenticatedOnAdminAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk());
    }

    @Test
    public void testNoUserOneBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ADMIN"}
    )
    @Test
    public void testAuthenticatedOnAdminOneBook() throws Exception {
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testNoUserCreate() throws Exception {
        String json = getBook();
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ADMIN"}
    )
    @Test
    public void testAuthenticatedOnAdminCreate() throws Exception {
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getBook())
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void testNoUserUpdate() throws Exception {
        mockMvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getBook())
                .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ADMIN"}
    )
    @Test
    public void testAuthenticatedOnAdminUpdate() throws Exception {
        mockMvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getBook())
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void testNoUserDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ADMIN"}
    )
    @Test
    public void testAuthenticatedOnAdminDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1"))
                .andExpect(status().isOk());
    }

    @BeforeEach
    public void init() throws URISyntaxException {
        BookDTO book = new BookDTO(1L, "test", new Author("123", "123"), new Genre("123"));
        ResponseEntity<BookDTO> response = ResponseEntity.ok().body(book);

        when(bookController.books()).thenReturn(List.of(book));
        when(bookController.book(any())).thenReturn(response);
        when(bookController.createBook(any())).thenReturn(response);
        when(bookController.updateBook(any())).thenReturn(response);
        when(bookController.deleteBook(any())).thenReturn(ResponseEntity.ok().build());
    }

    private String getBook() throws JsonProcessingException {
        BookDTO book = new BookDTO(1L, "test", new Author(1L, null, null), new Genre(1L, null));
        ResponseEntity<BookDTO> response = ResponseEntity.ok().body(book);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(book);
    }

}
