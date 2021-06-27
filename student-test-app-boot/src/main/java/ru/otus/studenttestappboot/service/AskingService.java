package ru.otus.studenttestappboot.service;



import ru.otus.studenttestappboot.domain.QuestionResult;

import java.util.List;

public interface AskingService {
    List<QuestionResult> ask();
}
