package ru.otus.libraryhystrix.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.libraryhystrix.models.Genre;
import ru.otus.libraryhystrix.repositories.GenreRepository;
import ru.otus.libraryhystrix.stub.LibraryDataStub;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class GenreController {
    private final GenreRepository genreRepository;
    private final LibraryDataStub defaultLibraryData;

    @GetMapping("/api/genres")
    @HystrixCommand(fallbackMethod = "defaultGenres")
    public List<Genre> genres() {
        return genreRepository.findAll();
    }

    @PostMapping("/api/genres")
    @HystrixCommand(fallbackMethod = "defaultGenres")
    public ResponseEntity<Genre> createGenre(@Valid @RequestBody Genre genre) throws URISyntaxException {
        Genre savedGenre = genreRepository.save(genre);
        return ResponseEntity.created(new URI("/api/genres/" + savedGenre.getId()))
                .body(savedGenre);
    }

    private List<Genre> defaultGenres() {
        return defaultLibraryData.defaultGenres();
    }

    private ResponseEntity<Genre> defaultCreation(@Valid @RequestBody Genre author) throws URISyntaxException {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
