package ru.otus.mvcrestlibrary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.otus.mvcrestlibrary.dto.BookDTO;
import ru.otus.mvcrestlibrary.models.Author;
import ru.otus.mvcrestlibrary.models.Genre;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Интеграционные тесты для книг")
class BookControllerIntegrationTest {

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final HttpHeaders headers = new HttpHeaders();
    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Все книги")
    public void testAllBooks() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/books"),
                HttpMethod.GET, entity, String.class);

        String expected = "[{\"id\":1,\"title\":\"War and Peace\",\"author\":{\"id\":1,\"surname\":\"Tolstoy\",\"name\":\"Lev\"},\"genre\":{\"id\":2,\"name\":\"Novel\"}},{\"id\":2,\"title\":\"Pride and prejudice\",\"author\":{\"id\":2,\"surname\":\"Austen\",\"name\":\"Jane\"},\"genre\":{\"id\":2,\"name\":\"Novel\"}},{\"id\":3,\"title\":\"Murder on the Orient Express\",\"author\":{\"id\":3,\"surname\":\"Christie\",\"name\":\"Agatha\"},\"genre\":{\"id\":1,\"name\":\"Detective\"}},{\"id\":4,\"title\":\"It\",\"author\":{\"id\":4,\"surname\":\"King\",\"name\":\"Stephen \"},\"genre\":{\"id\":3,\"name\":\"Horror\"}}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @DisplayName("Книга по id")
    public void testBookById() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/books/1"),
                HttpMethod.GET, entity, String.class);

        String expected = "{\"id\":1,\"title\":\"War and Peace\",\"author\":{\"id\":1,\"surname\":\"Tolstoy\",\"name\":\"Lev\"},\"genre\":{\"id\":2,\"name\":\"Novel\"}}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }


    @Test
    @DisplayName("Создание")
    public void testCreateBook() {
        BookDTO book = new BookDTO(null, "test", new Author(1L, null, null), new Genre(1L, null));
        HttpEntity<BookDTO> entity = new HttpEntity<>(book, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/books"),
                HttpMethod.POST, entity, String.class);
        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        assertTrue(actual.contains("/api/books"));
    }

    @Test
    @DisplayName("Обновление")
    public void testUpdateBook() throws Exception {
        BookDTO book = new BookDTO(1L, "test", new Author(1L, null, null), new Genre(1L, null));
        HttpEntity<BookDTO> entity = new HttpEntity<>(book, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/books"),
                HttpMethod.PUT, entity, String.class);

        String expected = "{\"id\":1,\"title\":\"test\",\"author\":{\"id\":1,\"surname\":\"Tolstoy\",\"name\":\"Lev\"},\"genre\":{\"id\":1,\"name\":\"Detective\"}}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
