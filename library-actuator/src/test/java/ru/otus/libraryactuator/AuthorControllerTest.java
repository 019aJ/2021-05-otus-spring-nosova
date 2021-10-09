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
import ru.otus.libraryactuator.controllers.AuthorController;
import ru.otus.libraryactuator.models.Author;

import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorController authorController;

    @Test
    public void testNoUserAllAuthors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/authors"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ADMIN"}
    )
    @Test
    public void testAuthenticatedOnAdminAllAuthors() throws Exception {
        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk());
    }

    @Test
    public void testNoUserCreate() throws Exception {
        String json = getAuthor();
        mockMvc.perform(post("/api/authors")
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
        mockMvc.perform(post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getAuthor())
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @BeforeEach
    public void init() throws URISyntaxException {
        Author author = new Author(1L, null, null);
        ResponseEntity<Author> response = ResponseEntity.ok().body(author);
        when(authorController.authors()).thenReturn(List.of(author));
        when(authorController.createAuthor(any())).thenReturn(response);
    }

    private String getAuthor() throws JsonProcessingException {
        Author author = new Author(1L, null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(author);
    }

}
