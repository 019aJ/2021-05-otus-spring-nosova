package ru.otus.libraryhystrix.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.libraryhystrix.dto.BookDTO;
import ru.otus.libraryhystrix.dto.BookMapper;
import ru.otus.libraryhystrix.models.Book;
import ru.otus.libraryhystrix.repositories.BookRepository;
import ru.otus.libraryhystrix.stub.LibraryDataStub;

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
    private final LibraryDataStub defaultLibraryData;

    @GetMapping("/api/books")
    @HystrixCommand(fallbackMethod = "defaultBooks",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "500"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "1"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000")
            })
    public List<BookDTO> books() throws InterruptedException {
        return bookMapper.toDto(repository.findAll());
    }

    @GetMapping("/api/books/{bookId}")
    @HystrixCommand(fallbackMethod = "defaultBook")
    public ResponseEntity<BookDTO> book(@PathVariable Long bookId) {
        Optional<Book> books = repository.findById(bookId);
        return books.map(book -> ResponseEntity.ok().body(bookMapper.toDto(book)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/api/books")
    @HystrixCommand(fallbackMethod = "defaultCreate")
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO book) throws URISyntaxException {
        Book result = repository.save(bookMapper.toEntity(book));
        return ResponseEntity.created(new URI("/api/books/" + result.getId()))
                .body(bookMapper.toDto(result));
    }

    @PutMapping("/api/books")
    @HystrixCommand(fallbackMethod = "defaultUpdate")
    public ResponseEntity<BookDTO> updateBook(@Valid @RequestBody BookDTO book) {
        Book result = repository.save(bookMapper.toEntity(book));
        return ResponseEntity.ok().body(bookMapper.toDto(result));
    }

    @DeleteMapping("/api/books/{bookId}")
    @HystrixCommand(fallbackMethod = "defaultDelete")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable Long bookId) {
        repository.deleteById(bookId);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<BookDTO> defaultBook(@PathVariable Long bookId) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private List<BookDTO> defaultBooks() {
        return defaultLibraryData.defaultBooks();
    }

    private ResponseEntity<BookDTO> defaultCreate(@Valid @RequestBody BookDTO book) throws URISyntaxException {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<BookDTO> defaultDelete(@PathVariable Long bookId) throws URISyntaxException {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
