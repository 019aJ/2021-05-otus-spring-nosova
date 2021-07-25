package ru.otus.librarymongo.exceptions;

public class FkViolationException extends LibraryAppException {

    public FkViolationException(String text) {
        super(text);
    }

    public FkViolationException(Throwable e) {
        super(e);
    }

    public FkViolationException(String text, Throwable e) {
        super(text, e);
    }
}
