package ru.otus.librarymongo;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableMongock
@EnableConfigurationProperties
public class LibraryMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryMongoApplication.class, args);
    }

}
