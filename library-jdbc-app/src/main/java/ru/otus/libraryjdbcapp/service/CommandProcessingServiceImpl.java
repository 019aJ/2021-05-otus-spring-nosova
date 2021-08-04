package ru.otus.libraryjdbcapp.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.libraryjdbcapp.exceptions.InvalidInputException;
import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CommandProcessingServiceImpl implements CommandProcessingService {
    private final LibraryManagingService libraryManagingService;
    private final ReadInputService askingService;

    @Override
    public void insert(String entityName) throws LibraryAppException {
        List<String> fieldsForInput = this.libraryManagingService.insertingFields(entityName);
        if (!fieldsForInput.isEmpty()) {
            Map<String, String> fieldValues = this.askingService.ask(fieldsForInput);
            libraryManagingService.insert(entityName, fieldValues);
        }
    }

    @Override
    public void update(String id) throws LibraryAppException {
        Long bookId = getId(id);
        List<String> fieldsForInput = this.libraryManagingService.updatingFields();
        if (!fieldsForInput.isEmpty()) {
            Map<String, String> fieldValues = this.askingService.ask(fieldsForInput);
            libraryManagingService.update(bookId, fieldValues);
        }
    }

    @Override
    public void delete(String id) throws LibraryAppException {
        Long bookId = getId(id);
        libraryManagingService.deleteById(bookId);
    }

    @Override
    public void read(String id, String entityName) throws LibraryAppException {
        Long bookId = getId(id);
        libraryManagingService.findById(bookId, entityName);
    }

    private Long getId(String id) throws InvalidInputException {
        if (StringUtils.isEmpty(id)) {
            throw new InvalidInputException("Id книги должен быть задан");
        }
        Long bookId;
        try {
            bookId = Long.valueOf(id);
        } catch (NumberFormatException | NullPointerException e) {
            throw new InvalidInputException("Id книги должен быть числом");
        }
        return bookId;
    }
}