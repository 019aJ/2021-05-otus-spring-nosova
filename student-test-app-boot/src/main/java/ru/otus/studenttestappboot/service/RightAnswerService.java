package ru.otus.studenttestappboot.service;

import ru.otus.studenttestappboot.domain.Answer;

import java.util.Map;


public interface RightAnswerService {
    Map<String, Answer> rightAnswers();
}
