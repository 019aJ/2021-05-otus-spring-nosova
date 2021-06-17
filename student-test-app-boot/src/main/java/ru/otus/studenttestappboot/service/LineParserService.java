package ru.otus.studenttestappboot.service;


import ru.otus.studenttestappboot.domain.Question;

public interface LineParserService {
    Question getQuestionFromLine(String line);
}
