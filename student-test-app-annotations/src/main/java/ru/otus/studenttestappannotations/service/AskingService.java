package ru.otus.studenttestappannotations.service;


import ru.otus.studenttestappannotations.domain.QuestionResult;

import java.util.List;

public interface AskingService {
    List<QuestionResult> ask();
}
