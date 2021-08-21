package ru.otus.mvcrestlibrary.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.mvcrestlibrary.dto.BookDTO;
import ru.otus.mvcrestlibrary.dto.BookMapper;
import ru.otus.mvcrestlibrary.models.Book;
import ru.otus.mvcrestlibrary.repositories.BookRepository;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class BookController {

    private final BookRepository repository;
    private final BookMapper bookMapper;

    @GetMapping("/api/books")
    public List<BookDTO> books() {
        return bookMapper.toDto(repository.findAll());
    }

    @GetMapping("/api/books/{bookId}")
    public ResponseEntity<BookDTO> book(@PathVariable Long bookId) {
        Optional<Book> books = repository.findById(bookId);
        return books.map(book -> ResponseEntity.ok().body(bookMapper.toDto(book)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/api/books")
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO book) throws URISyntaxException {
        Book result = repository.save(bookMapper.toEntity(book));
        return ResponseEntity.created(new URI("/api/books/" + result.getId()))
                .body(bookMapper.toDto(result));
    }

    @PutMapping("/api/books")
    public ResponseEntity<BookDTO> updateBook(@Valid @RequestBody BookDTO book) {
        Book result = repository.save(bookMapper.toEntity(book));
        return ResponseEntity.ok().body(bookMapper.toDto(result));
    }

    @DeleteMapping("/api/books/{bookId}")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable Long bookId) {
        repository.deleteById(bookId);
        return ResponseEntity.ok().build();
    }
}
