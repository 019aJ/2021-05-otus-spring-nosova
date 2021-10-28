package ru.otus.libraryhystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class LibraryHystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryHystrixApplication.class, args);
    }

}
