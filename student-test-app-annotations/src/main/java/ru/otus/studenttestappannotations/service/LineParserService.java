package ru.otus.studenttestappannotations.service;

import ru.otus.studenttestappannotations.domain.Question;

public interface LineParserService {
    public Question getQuestionFromLine(String line);
}
