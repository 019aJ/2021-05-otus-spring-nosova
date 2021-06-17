package ru.otus.studenttestappboot.service;


import org.springframework.core.io.Resource;
import ru.otus.studenttestappboot.domain.Question;

import java.util.List;
import java.util.Scanner;

public interface QuestionParserService {
    List<Question> parse(Resource resource);
}
