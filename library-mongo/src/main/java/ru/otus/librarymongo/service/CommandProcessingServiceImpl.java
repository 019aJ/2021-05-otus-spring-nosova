package ru.otus.librarymongo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.librarymongo.exceptions.LibraryAppException;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CommandProcessingServiceImpl implements CommandProcessingService {
    private final LibraryManagingService libraryManagingService;
    private final ReadInputService askingService;

    @Override
    public void delete(String id, String entityName) throws LibraryAppException {
        libraryManagingService.deleteById(entityName, id);
    }

    @Override
    public void deleteAuthor(String name, String surname) {
        libraryManagingService.deleteAuthor(name, surname);
    }

    @Override
    public void deleteGenre(String name) {
        libraryManagingService.deleteGenre(name);
    }

    @Override
    public void insert(String entityName) throws LibraryAppException {
        List<String> fieldsForInput = libraryManagingService.insertingFields(entityName);
        if (!fieldsForInput.isEmpty()) {
            Map<String, String> fieldValues = askingService.ask(fieldsForInput);
            libraryManagingService.insert(entityName, fieldValues);
        }
    }

    @Override
    public void read(String id, String entityName) throws LibraryAppException {
        libraryManagingService.findById(id, entityName);
    }

    @Override
    public void update(String id, String entityName) throws LibraryAppException {
        Map<String, String> fieldValues = askingService.ask(libraryManagingService.updatingFields(entityName));
        libraryManagingService.update(entityName, id, fieldValues);
    }

    @Override
    public void findByTitle(String title) {
        libraryManagingService.bookByTitle(title);
    }

    @Override
    public void comments(String bookId) throws LibraryAppException {
        libraryManagingService.comments(bookId);
    }

    @Override
    public void books() {
        libraryManagingService.books();
    }

    @Override
    public void authors() {
        libraryManagingService.authors();
    }

    @Override
    public void genres() {
        libraryManagingService.genres();
    }

    @Override
    public void booksByAuthor(String name, String surname) {
        libraryManagingService.booksByAuthor(name, surname);
    }

    @Override
    public void booksByGenre(String name) {
        libraryManagingService.booksByGenre(name);
    }

}