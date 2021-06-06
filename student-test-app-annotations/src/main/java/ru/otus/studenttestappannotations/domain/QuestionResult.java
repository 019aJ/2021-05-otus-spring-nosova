package ru.otus.studenttestappannotations.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QuestionResult {
    private final Question question;
    private final Answer rightAnswer;
    private final String userAnswer;
}
