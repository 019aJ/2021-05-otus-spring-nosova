package ru.otus.libraryauthentication;

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
import ru.otus.libraryauthentication.controllers.GenreController;
import ru.otus.libraryauthentication.models.Genre;

import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class GenreControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreController genreController;

    @Test
    public void testNoUserAllGenres() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/genres"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ADMIN"}
    )
    @Test
    public void testAuthenticatedOnAdminAllGenres() throws Exception {
        mockMvc.perform(get("/api/genres"))
                .andExpect(status().isOk());
    }

    @Test
    public void testNoUserCreate() throws Exception {
        String json = getGenre();
        mockMvc.perform(post("/api/genres")
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
        mockMvc.perform(post("/api/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getGenre())
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @BeforeEach
    public void init() throws URISyntaxException {
        Genre genre = new Genre(1L, null);
        ResponseEntity<Genre> response = ResponseEntity.ok().body(genre);
        when(genreController.genres()).thenReturn(List.of(genre));
        when(genreController.createGenre(any())).thenReturn(response);
    }

    private String getGenre() throws JsonProcessingException {
        Genre genre = new Genre(1L, null);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(genre);
    }

}
