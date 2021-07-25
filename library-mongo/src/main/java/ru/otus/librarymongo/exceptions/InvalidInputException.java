package ru.otus.librarymongo.exceptions;

public class InvalidInputException extends LibraryAppException {

    public InvalidInputException(String text) {
        super(text);
    }

    public InvalidInputException(Throwable e) {
        super(e);
    }
}
