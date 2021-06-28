package ru.otus.libraryjdbcapp.service;


import java.util.List;
import java.util.Map;

public interface AskingService {
    Map<String, String> ask(List<String> questions);
}
