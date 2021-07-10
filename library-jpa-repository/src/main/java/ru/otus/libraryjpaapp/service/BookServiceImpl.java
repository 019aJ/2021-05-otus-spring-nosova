package ru.otus.libraryjpaapp.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.libraryjpaapp.exceptions.*;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.repositories.BookRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> all() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> allEagerly() {
        return repository.findAllEagerly();
    }

    @Override
    @Transactional(readOnly = true)
    public Book byId(long id) throws LibraryAppException {
        return repository.findById(id).orElseThrow(() -> new NoSuchResultException("Данные для id " + id + "не найдены"));
    }

    @Override
    @Transactional(readOnly = true)
    public Book byIdEagerly(long id) throws LibraryAppException {
        Book book = repository.findById(id).orElseThrow(() -> new NoSuchResultException("Данные для id " + id + "не найдены"));
        book.getComments().size();
        return book;
    }

    @Override
    @Transactional()
    public long insert(Book book) throws LibraryAppException {
        checkMandatory(book);
        try {
            return repository.save(book).getId();
        } catch (DuplicateKeyException e) {
            throw new PkViolationException("Книга с заданным id уже существует", e);
        } catch (DataIntegrityViolationException e) {
            throw new FkViolationException("Неверный автор либо жанр", e);
        } catch (Exception e) {
            throw new LibraryAppException("Невозможно создать книгу", e);
        }
    }

    @Override
    @Transactional()
    public void update(Book book) {
        checkMandatory(book);
        repository.save(book);
    }

    @Override
    @Transactional()
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    private void checkMandatory(Book book) throws LibraryAppException {
        if (StringUtils.isEmpty(book.getTitle())) {
            throw new InvalidInputException("Название книги должно быть заполнено");
        }
        if (book.getAuthor() == null || book.getAuthor().getId() == null) {
            throw new InvalidInputException("Автор книги должен быть заполнен");
        }
    }
}
