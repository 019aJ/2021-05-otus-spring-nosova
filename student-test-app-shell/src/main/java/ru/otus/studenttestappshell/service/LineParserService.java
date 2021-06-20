package ru.otus.studenttestappshell.service;


import ru.otus.studenttestappshell.domain.Question;

public interface LineParserService {
    Question getQuestionFromLine(String line);
}
