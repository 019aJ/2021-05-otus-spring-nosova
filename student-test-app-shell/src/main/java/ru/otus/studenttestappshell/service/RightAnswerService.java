package ru.otus.studenttestappshell.service;

import ru.otus.studenttestappshell.domain.Answer;

import java.util.Map;


public interface RightAnswerService {
    Map<String, Answer> rightAnswers();
}
