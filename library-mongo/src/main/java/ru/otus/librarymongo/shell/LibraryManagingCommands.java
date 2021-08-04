package ru.otus.librarymongo.shell;

import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.librarymongo.exceptions.InvalidInputException;
import ru.otus.librarymongo.exceptions.LibraryAppException;
import ru.otus.librarymongo.service.CommandProcessingService;

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
     * Удаление
     * Если удаляем книгу, то удаляются и комментарии
     */
    @ShellMethod(value = "delete command", key = {"d"})
    public void delete(@ShellOption() String id, @ShellOption(defaultValue = "Book") String entityName) throws LibraryAppException {
        if (StringUtils.isEmpty(id)) {
            throw new InvalidInputException("Id для удаления не задан");
        }
        commandProcessingService.delete(id, entityName);
    }

    /**
     * Если удаляем автора, то удаляются и их книги с комментариями
     * Если удаляем жанр, то книги апдейтятся пустым значением
     */
    @ShellMethod(value = "delete author command", key = {"dau"})
    public void deleteAuthor(@ShellOption() String name, @ShellOption() String surname) throws LibraryAppException {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(surname)) {
            throw new InvalidInputException("Информация об авторе не задана");
        }
        commandProcessingService.deleteAuthor(name, surname);
    }

    /**
     * Если удаляем жанр, то книги апдейтятся пустым значением
     */
    @ShellMethod(value = "delete author command", key = {"dge"})
    public void deleteGenre(@ShellOption() String name) throws LibraryAppException {
        if (StringUtils.isEmpty(name)) {
            throw new InvalidInputException("Информация о жанре не задана");
        }
        commandProcessingService.deleteGenre(name);
    }

    @ShellMethod(value = "get by Title command", key = {"f", "s"})
    public void readByTitle(@ShellOption() String title) throws LibraryAppException {
        if (StringUtils.isEmpty(title)) {
            throw new InvalidInputException("Название для поиска не задано");
        }
        commandProcessingService.findByTitle(title);
    }

    @ShellMethod(value = "get command", key = {"g", "r"})
    public void read(@ShellOption() String id, @ShellOption(defaultValue = "Book") String entityName) throws LibraryAppException {
        if (StringUtils.isEmpty(id)) {
            throw new InvalidInputException("Id для поиска не задан");
        }
        commandProcessingService.read(id, entityName);
    }

    @ShellMethod(value = "comments for book command", key = {"gc", "rc"})
    public void read(@ShellOption() String id) throws LibraryAppException {
        if (StringUtils.isEmpty(id)) {
            throw new InvalidInputException("Id для поиска не задан");
        }
        commandProcessingService.comments(id);
    }

    @ShellMethod(value = "all books by author command", key = {"bau"})
    public void readBooksByAuthor(@ShellOption() String name, @ShellOption() String surname) {
        commandProcessingService.booksByAuthor(name, surname);
    }

    @ShellMethod(value = "all books by genre command", key = {"bge"})
    public void readBooksByGenre(@ShellOption() String name) {
        commandProcessingService.booksByGenre(name);
    }

    @ShellMethod(value = "all authors command", key = {"au"})
    public void readAuthors() {
        commandProcessingService.authors();
    }

    @ShellMethod(value = "all authors command", key = {"ge"})
    public void readGenres() {
        commandProcessingService.genres();
    }

    @ShellMethod(value = "all books command", key = {"b"})
    public void readBooks() {
        commandProcessingService.books();
    }
}
