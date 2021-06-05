package ru.otus.studenttestapp.domain;

import lombok.*;

import java.util.List;


@AllArgsConstructor
public class Question {
    @Getter
    private String questionText;

    @Getter
    private List<Answer> answerVariants;
}
