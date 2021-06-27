package ru.otus.studenttestappboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class QuestionResult {
    private final Question question;
    private final Answer rightAnswer;
    private final String userAnswer;
}
