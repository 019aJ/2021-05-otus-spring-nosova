package ru.otus.studenttestappshell.service;



import ru.otus.studenttestappshell.domain.QuestionResult;

import java.util.List;

public interface AskingService {
    List<QuestionResult> ask();
}
