package ru.otus.libraryauthenticationacl;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class LibraryAuthenticationAclApplication {

    public static void main(String[] args) throws SQLException {
        Console.main(args);

        SpringApplication.run(LibraryAuthenticationAclApplication.class, args);
    }

}
