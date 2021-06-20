package ru.otus.studenttestappshell.service;


import org.springframework.core.io.Resource;
import ru.otus.studenttestappshell.domain.Question;

import java.util.List;
import java.util.Scanner;

public interface QuestionParserService {
    List<Question> parse(Resource resource);
}
