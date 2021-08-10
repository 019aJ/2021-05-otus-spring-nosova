package ru.otus.mvclibrary.dto;

import org.springframework.stereotype.Service;
import ru.otus.mvclibrary.models.Book;

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
}
