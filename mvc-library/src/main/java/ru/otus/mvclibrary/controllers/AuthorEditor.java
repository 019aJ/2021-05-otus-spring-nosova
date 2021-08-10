package ru.otus.mvclibrary.controllers;

import ru.otus.mvclibrary.models.Author;

import java.beans.PropertyEditorSupport;

public class AuthorEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        Object value = this.getValue();
        if (value != null) {
            return ((Author) value).getId().toString();
        }
        return "";
    }

    @Override
    public void setAsText(String text) {
        if (text == null) {
            this.setValue(null);
        } else {
            this.setValue(new Author(Long.parseLong(text), null, null));
        }
    }
}