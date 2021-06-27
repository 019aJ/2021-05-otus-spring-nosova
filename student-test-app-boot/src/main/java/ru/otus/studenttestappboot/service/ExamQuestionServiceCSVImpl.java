package ru.otus.studenttestappboot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service("examQuestions")
@PropertySource("classpath:application.yaml")
public class ExamQuestionServiceCSVImpl extends QuestionServiceCSVImpl implements QuestionService {
    public ExamQuestionServiceCSVImpl(@Value("${question}") String csvSource, LocalizationService localizationService, QuestionParserService questionParserService) {
        super(localizationService.localizeResourceName(csvSource), questionParserService);
    }
}
