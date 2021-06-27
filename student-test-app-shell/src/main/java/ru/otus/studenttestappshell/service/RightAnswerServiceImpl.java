package ru.otus.studenttestappshell.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.studenttestappshell.domain.Answer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Slf4j
@Service
@PropertySource("classpath:application.yaml")
public class RightAnswerServiceImpl implements RightAnswerService {

    private Map<String, Answer> rightAnswers;
    private final String csvSource;
    private final RightAnswersParserService rightAnswersParserService;

    public RightAnswerServiceImpl(@Value("${answer}") String csvSource, LocalizationService localizationService, RightAnswersParserService rightAnswersParserService) {
        this.csvSource = localizationService.localizeResourceName(csvSource);
        this.rightAnswersParserService = rightAnswersParserService;
    }

    @Override
    public Map<String, Answer> rightAnswers() {
        if (rightAnswers == null) {
            rightAnswers = new HashMap<>();
            parseQuestions(csvSource, rightAnswersParserService);
        }
        return rightAnswers;
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
}
