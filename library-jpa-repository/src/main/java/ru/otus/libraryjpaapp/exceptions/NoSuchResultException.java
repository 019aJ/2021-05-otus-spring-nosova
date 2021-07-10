package ru.otus.libraryjpaapp.exceptions;

public class NoSuchResultException extends LibraryAppException {

    public NoSuchResultException(String text) {
        super(text);
    }

    public NoSuchResultException(Throwable e) {
        super(e);
    }

    public NoSuchResultException(String text, Throwable e) {
        super(text, e);
    }
}
