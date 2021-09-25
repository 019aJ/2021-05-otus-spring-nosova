package ru.otus.libraryauthenticationacl.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.libraryauthenticationacl.models.Genre;
import ru.otus.libraryauthenticationacl.repositories.GenreRepository;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class GenreController {
    private final GenreRepository genreRepository;

    @GetMapping("/api/genres")
    public List<Genre> genres() {
        return genreRepository.findAll();
    }

    @PostMapping("/api/genres")
    public ResponseEntity<Genre> createGenre(@Valid @RequestBody Genre genre) throws URISyntaxException {
        Genre result = genreRepository.save(genre);
        return ResponseEntity.created(new URI("/api/genres/" + result.getId()))
                .body(result);
    }
}
