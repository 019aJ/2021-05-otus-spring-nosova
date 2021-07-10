package ru.otus.libraryjpaapp.exceptions;

public class PkViolationException extends LibraryAppException {

    public PkViolationException(String text) {
        super(text);
    }

    public PkViolationException(Throwable e) {
        super(e);
    }

    public PkViolationException(String text, Throwable e) {
        super(text, e);
    }
}
