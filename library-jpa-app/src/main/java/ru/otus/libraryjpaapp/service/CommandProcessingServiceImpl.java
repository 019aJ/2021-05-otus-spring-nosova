package ru.otus.libraryjpaapp.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.libraryjpaapp.exceptions.InvalidInputException;
import ru.otus.libraryjpaapp.exceptions.LibraryAppException;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CommandProcessingServiceImpl implements CommandProcessingService {
    private final LibraryManagingService libraryManagingService;
    private final ReadInputService askingService;

    @Override
    public void insert(String entityName) throws LibraryAppException {
        List<String> fieldsForInput = libraryManagingService.insertingFields(entityName);
        if (!fieldsForInput.isEmpty()) {
            Map<String, String> fieldValues = askingService.ask(fieldsForInput);
            libraryManagingService.insert(entityName, fieldValues);
        }
    }

    @Override
    public void update(String inputId, String entityName) throws LibraryAppException {
        Long id = getId(inputId);
        List<String> fieldsForInput = libraryManagingService.updatingFields(entityName);
        if (!fieldsForInput.isEmpty()) {
            Map<String, String> fieldValues = askingService.ask(fieldsForInput);
            libraryManagingService.update(entityName, id, fieldValues);
        }
    }

    @Override
    public void delete(String id, String entityName) throws LibraryAppException {
        Long bookId = getId(id);
        libraryManagingService.deleteById(entityName, bookId);
    }

    @Override
    public void read(String id, String entityName) throws LibraryAppException {
        Long bookId = getId(id);
        libraryManagingService.findById(bookId, entityName);
    }

    private Long getId(String inputId) throws InvalidInputException {
        if (StringUtils.isEmpty(inputId)) {
            throw new InvalidInputException("Id должен быть задан");
        }
        Long id;
        try {
            id = Long.valueOf(inputId);
        } catch (NumberFormatException | NullPointerException e) {
            throw new InvalidInputException("Id должен быть числом");
        }
        return id;
    }
}