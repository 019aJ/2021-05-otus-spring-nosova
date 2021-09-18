package ru.otus.libraryauthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class LibraryAuthenticationApplication {
    public static void main(String[] args) throws SQLException {
        //Console.main(args);
        SpringApplication.run(LibraryAuthenticationApplication.class, args);
    }
}
