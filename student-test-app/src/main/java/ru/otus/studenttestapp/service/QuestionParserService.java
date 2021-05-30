package ru.otus.studenttestapp.service;

import ru.otus.studenttestapp.domain.Question;

import java.util.List;
import java.util.Scanner;

public interface QuestionParserService {
    List<Question> parse(Scanner csvSource);
}
