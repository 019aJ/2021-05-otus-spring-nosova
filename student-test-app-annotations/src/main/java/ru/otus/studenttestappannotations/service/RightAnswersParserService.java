package ru.otus.studenttestappannotations.service;

import ru.otus.studenttestappannotations.domain.Answer;

import java.util.Map;
import java.util.Scanner;

public interface RightAnswersParserService {
    Map<String, Answer> parse(Scanner csvSource);
}
