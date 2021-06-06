package ru.otus.studenttestappannotations.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.studenttestappannotations.domain.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Slf4j
public class QuestionServiceCSVImpl implements QuestionService {

    private final List<Question> questions = new ArrayList<>();

    public QuestionServiceCSVImpl(String csvSource, QuestionParserService questionParserService) {
        parseQuestions(csvSource, questionParserService);
    }

    private void parseQuestions(String csvSource, QuestionParserService questionParserService) {
        if (StringUtils.isBlank(csvSource)) {
            log.error("Empty resource ");
        } else {
            Resource resource = new ClassPathResource(csvSource);
            try (Scanner scanner = new Scanner(resource.getInputStream())) {
                questions.clear();
                questions.addAll(questionParserService.parse(scanner));
            } catch (IOException e) {
                log.error("Cant read resource " + csvSource, e);
            }
        }
    }

    @Override
    public List<Question> questions() {
        return questions;
    }
}
