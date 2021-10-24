package ru.otus.libraryhystrix.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.libraryhystrix.models.Author;
import ru.otus.libraryhystrix.repositories.AuthorRepository;
import ru.otus.libraryhystrix.stub.LibraryDataStub;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class AuthorController {
    private final AuthorRepository authorRepository;
    private final LibraryDataStub defaultLibraryData;

    @GetMapping("/api/authors")
    @HystrixCommand(fallbackMethod = "defaultAuthors")
    public List<Author> authors() {
        return authorRepository.findAll();
    }

    @PostMapping("/api/authors")
    @HystrixCommand(fallbackMethod = "defaultCreation")
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) throws URISyntaxException {
        Author savedAuthor = authorRepository.save(author);
        return ResponseEntity.created(new URI("/api/authors/" + savedAuthor.getId()))
                .body(savedAuthor);
    }

    private List<Author> defaultAuthors() {
        return defaultLibraryData.defaultAuthors();
    }

    private ResponseEntity<Author> defaultCreation(@Valid @RequestBody Author author) throws URISyntaxException {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
