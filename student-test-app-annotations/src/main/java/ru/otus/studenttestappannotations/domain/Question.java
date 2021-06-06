package ru.otus.studenttestappannotations.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
public class Question {
    @Getter
    private final String id;

    @Getter
    private final String questionText;

    @Getter
    private final List<Answer> answerVariants;
}
