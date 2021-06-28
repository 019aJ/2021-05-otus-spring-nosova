package ru.otus.libraryjdbcapp.shell;

import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.libraryjdbcapp.exceptions.InvalidInputException;
import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.service.CommandProcessingService;

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
        this.commandProcessingService.insert(entityName);
    }

    /**
     * Апдейтить можно только книгу
     */
    @ShellMethod(value = "update command", key = {"u"})
    public void update(@ShellOption() String id) throws LibraryAppException {
        if (StringUtils.isEmpty(id)) {
            throw new InvalidInputException("Id для обновления не задан");
        }
        this.commandProcessingService.update(id);
    }

    /**
     * Удалять можно только книгу
     */
    @ShellMethod(value = "delete command", key = {"d"})
    public void delete(@ShellOption() String id) throws LibraryAppException {
        if (StringUtils.isEmpty(id)) {
            throw new InvalidInputException("Id для удаления не задан");
        }
        this.commandProcessingService.delete(id);
    }

    @ShellMethod(value = "get command", key = {"g", "r"})
    public void read(@ShellOption() String id, @ShellOption(defaultValue = "Book") String entityName) throws LibraryAppException {
        if (StringUtils.isEmpty(id)) {
            throw new InvalidInputException("Id для поиска не задан");
        }
        this.commandProcessingService.read(id, entityName);
    }

}
