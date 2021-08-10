package ru.otus.mvclibrary.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.mvclibrary.models.Genre;

import java.beans.PropertyEditorSupport;

@Slf4j
@Service
@AllArgsConstructor
public class GenreEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        Object value = this.getValue();
        if (value != null) {
            return ((Genre) value).getId().toString();
        }
        return "";
    }

    @Override
    public void setAsText(String text) {
        if (text == null) {
            this.setValue(null);
        } else {
            this.setValue(new Genre(Long.parseLong(text), null));
        }
    }
}