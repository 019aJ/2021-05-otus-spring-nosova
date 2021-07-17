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
        Map<String, String> fieldValues = askingService.ask(libraryManagingService.updatingFields(entityName));
        libraryManagingService.update(entityName, id, fieldValues);
    }

    @Override
    public void delete(String inputId, String entityName) throws LibraryAppException {
        Long id = getId(inputId);
        libraryManagingService.deleteById(entityName, id);
    }

    @Override
    public void read(String inputId, String entityName) throws LibraryAppException {
        Long id = getId(inputId);
        libraryManagingService.findById(id, entityName);
    }

    @Override
    public void comments(String inputBookId) throws LibraryAppException {
        Long bookId = getId(inputBookId);
        libraryManagingService.comments(bookId);
    }

    private Long getId(String inputId) throws InvalidInputException {
        if (StringUtils.isEmpty(inputId)) {
            throw new InvalidInputException("Id должен быть задан");
        }
        try {
            return Long.valueOf(inputId);
        } catch (NumberFormatException | NullPointerException e) {
            throw new InvalidInputException("Id должен быть числом");
        }
    }
}