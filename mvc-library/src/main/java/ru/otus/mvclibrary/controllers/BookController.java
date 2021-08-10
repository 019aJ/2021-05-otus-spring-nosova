package ru.otus.mvclibrary.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.otus.mvclibrary.dto.BookDTO;
import ru.otus.mvclibrary.dto.BookMapper;
import ru.otus.mvclibrary.models.Author;
import ru.otus.mvclibrary.models.Book;
import ru.otus.mvclibrary.models.Genre;
import ru.otus.mvclibrary.repositories.AuthorRepository;
import ru.otus.mvclibrary.repositories.BookRepository;
import ru.otus.mvclibrary.repositories.GenreRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class BookController {

    private final BookRepository repository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @GetMapping("/")
    public String listPage(Model model) {
        List<Book> books = repository.findAll();
        model.addAttribute("books", books.stream().map(bookMapper::toDto).collect(Collectors.toList()));
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") Long id, Model model) {
        Book book = repository.findById(id).orElse(new Book());
        model.addAttribute("book", bookMapper.toDto(book));
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "edit";
    }

    @PostMapping("/edit")
    public String saveBook(@Valid @ModelAttribute("book") BookDTO book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorRepository.findAll());
            model.addAttribute("genres", genreRepository.findAll());
            return "edit";
        }
        Book saved = repository.save(bookMapper.toEntity(book));
        model.addAttribute(saved);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam("id") Long id, Model model) {
        repository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new BookDTO());
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "add";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Author.class, new AuthorEditor());
        binder.registerCustomEditor(Genre.class, new GenreEditor());
        //binder.setRequiredFields("title");
    }

}
