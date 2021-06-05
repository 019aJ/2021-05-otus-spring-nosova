package ru.otus.studenttestapp.service;

import ru.otus.studenttestapp.domain.Question;

import java.util.List;

public interface AskingService {
    void ask(List<Question> questions);
}
