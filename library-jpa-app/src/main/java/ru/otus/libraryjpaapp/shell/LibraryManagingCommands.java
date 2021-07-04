package ru.otus.libraryjpaapp.shell;

import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.libraryjpaapp.exceptions.InvalidInputException;
import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.service.CommandProcessingService;

@ShellComponent
public class LibraryManagingCommands {

    private final CommandProcessingService commandProcessingService;

    public LibraryManagingCommands(CommandProcessingService commandProcessingService) {
        this.commandProcessingService = commandProcessingService;
    }

    /**
     * Создавать можно любые объекты доменной области
     */
    @ShellMethod(value = "insert command", key = {"c", "i"})
    public void insert(@ShellOption(defaultValue = "Book") String entityName) throws LibraryAppException {
        commandProcessingService.insert(entityName);
    }

    /**
     * Апдейтить можно только книгу
     */
    @ShellMethod(value = "update command", key = {"u"})
    public void update(@ShellOption() String id, @ShellOption(defaultValue = "Book") String entityName) throws LibraryAppException {
        if (StringUtils.isEmpty(id)) {
            throw new InvalidInputException("Id для обновления не задан");
        }
        commandProcessingService.update(id, entityName);
    }

    /**
     * Удалять можно только книгу и комментарий
     */
    @ShellMethod(value = "delete command", key = {"d"})
    public void delete(@ShellOption() String id, @ShellOption(defaultValue = "Book") String entityName) throws LibraryAppException {
        if (StringUtils.isEmpty(id)) {
            throw new InvalidInputException("Id для удаления не задан");
        }
        commandProcessingService.delete(id, entityName);
    }

    @ShellMethod(value = "get command", key = {"g", "r"})
    public void read(@ShellOption() String id, @ShellOption(defaultValue = "Book") String entityName) throws LibraryAppException {
        if (StringUtils.isEmpty(id)) {
            throw new InvalidInputException("Id для поиска не задан");
        }
        commandProcessingService.read(id, entityName);
    }

}
