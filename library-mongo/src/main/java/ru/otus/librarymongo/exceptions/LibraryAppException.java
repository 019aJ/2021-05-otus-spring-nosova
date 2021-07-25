package ru.otus.librarymongo.exceptions;

public class LibraryAppException extends RuntimeException {
    public LibraryAppException(String text) {
        super(text);
    }

    public LibraryAppException(Throwable e) {
        super(e);
    }

    public LibraryAppException(String text, Throwable e) {
        super(text, e);
    }
}
