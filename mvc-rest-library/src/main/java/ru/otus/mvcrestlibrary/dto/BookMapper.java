package ru.otus.mvcrestlibrary.dto;

import org.springframework.stereotype.Service;
import ru.otus.mvcrestlibrary.models.Book;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookMapper {

    public BookDTO toDto(Book entity) {
        BookDTO dto = new BookDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setGenre(entity.getGenre());
        return dto;
    }

    public Book toEntity(BookDTO dto) {
        Book entity = new Book();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setGenre(dto.getGenre());
        return entity;
    }

    public List<BookDTO> toDto(List<Book> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
