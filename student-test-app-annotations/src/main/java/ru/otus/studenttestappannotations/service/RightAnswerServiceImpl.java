package ru.otus.studenttestappannotations.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.studenttestappannotations.domain.Answer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class RightAnswerServiceImpl implements RightAnswerService {

    private Map<String, Answer> rightAnswers;
    private String csvSource;
    private RightAnswersParserService rightAnswersParserService;

    public RightAnswerServiceImpl(@Value("${answer.path}") String csvSource, RightAnswersParserService rightAnswersParserService) {
        this.csvSource = csvSource;
        this.rightAnswersParserService = rightAnswersParserService;
    }

    private void parseQuestions(String csvSource, RightAnswersParserService rightAnswersParserService) {
        if (StringUtils.isBlank(csvSource)) {
            log.error("Empty resource ");
        } else {
            Resource resource = new ClassPathResource(csvSource);
            try (Scanner scanner = new Scanner(resource.getInputStream())) {
                rightAnswers.clear();
                rightAnswers.putAll(rightAnswersParserService.parse(scanner));
            } catch (IOException e) {
                log.error("Cant read resource " + csvSource, e);
            }
        }
    }

    @Override
    public Map<String, Answer> rightAnswers() {
        if (rightAnswers == null) {
            rightAnswers = new HashMap<>();
            parseQuestions(csvSource, rightAnswersParserService);
        }
        return rightAnswers;
    }
}
