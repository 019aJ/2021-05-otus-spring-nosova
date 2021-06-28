package ru.otus.libraryjdbcapp.exceptions;

public class LibraryAppException extends Throwable {
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
