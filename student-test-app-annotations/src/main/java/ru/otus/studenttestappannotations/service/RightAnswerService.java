package ru.otus.studenttestappannotations.service;

import ru.otus.studenttestappannotations.domain.Answer;

import java.util.Map;


public interface RightAnswerService {
    Map<String, Answer> rightAnswers();
}
