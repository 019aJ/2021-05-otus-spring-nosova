package ru.otus.rxlibrary.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootTest
@DisplayName("Тест рест контроллера для книг")
public class BookControllerTest {

    @Autowired
    private RouterFunction<ServerResponse> composedBookRoutes;

    @Test
    public void testRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(composedBookRoutes)
                .build();

        client.get()
                .uri("/api/books")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
