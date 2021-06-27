package ru.otus.studenttestappshell.service;


import ru.otus.studenttestappshell.domain.Answer;

import java.util.Map;
import java.util.Scanner;

public interface RightAnswersParserService {
    Map<String, Answer> parse(Scanner csvSource);
}
