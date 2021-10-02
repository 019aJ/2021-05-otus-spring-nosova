package ru.otus.librarymigration;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@EnableMongock
@SpringBootApplication
public class LibraryMigrationApplication {

    public static void main(String[] args) throws SQLException {
        Console.main(args);
        SpringApplication.run(LibraryMigrationApplication.class, args);
    }

}
