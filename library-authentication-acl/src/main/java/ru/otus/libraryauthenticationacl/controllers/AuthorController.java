package ru.otus.libraryauthenticationacl.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.libraryauthenticationacl.models.Author;
import ru.otus.libraryauthenticationacl.repositories.AuthorRepository;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class AuthorController {
    private final AuthorRepository authorRepository;

    @GetMapping("/api/authors")
    public List<Author> authors() {
        return authorRepository.findAll();
    }

    @PostMapping("/api/authors")
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) throws URISyntaxException {
        Author result = authorRepository.save(author);
        return ResponseEntity.created(new URI("/api/authors/" + result.getId()))
                .body(result);
    }
}
