package ru.otus.studenttestappboot.service;


import ru.otus.studenttestappboot.domain.Answer;

import java.util.Map;
import java.util.Scanner;

public interface RightAnswersParserService {
    Map<String, Answer> parse(Scanner csvSource);
}
