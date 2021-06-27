package ru.otus.studenttestappshell.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
@Getter
public class Question {

    private final String id;
    private final String questionText;
    private final List<Answer> answerVariants;
}
