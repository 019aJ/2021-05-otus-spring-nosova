package ru.otus.studenttestappannotations.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.studenttestappannotations.domain.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Slf4j
@Lazy
@Service
public class QuestionServiceCSVImpl implements QuestionService {

    private List<Question> questions;
    private String csvSource;
    private QuestionParserService questionParserService;

    public QuestionServiceCSVImpl(String csvSource, QuestionParserService questionParserService) {
        this.csvSource = csvSource;
        this.questionParserService = questionParserService;
    }

    private void parseQuestions(String csvSource, QuestionParserService questionParserService) {

        if (StringUtils.isBlank(csvSource)) {
            log.error("Empty resource ");
        } else {
            Resource resource = new ClassPathResource(csvSource);
            try (Scanner scanner = new Scanner(resource.getInputStream())) {

                questions.addAll(questionParserService.parse(scanner));
            } catch (IOException e) {
                log.error("Cant read resource " + csvSource, e);
            }
        }
    }

    @Override
    public List<Question> questions() {
        if (questions == null) {
            questions = new ArrayList<>();
            parseQuestions(csvSource, questionParserService);
        }
        return questions;
    }
}
