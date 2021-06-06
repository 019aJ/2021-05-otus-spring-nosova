package ru.otus.studenttestappannotations.service;

import ru.otus.studenttestappannotations.domain.Question;

import java.util.List;
import java.util.Scanner;

public interface QuestionParserService {
    List<Question> parse(Scanner csvSource);
}
