package ru.otus.studenttestappshell.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service("personalQuestions")
@PropertySource("classpath:application.yaml")
public class PersonalDataQuestionServiceCSVImpl extends QuestionServiceCSVImpl implements QuestionService {
    public PersonalDataQuestionServiceCSVImpl(@Value("${personalQuestion}") String csvSource, LocalizationService localizationService, QuestionParserService questionParserService) {
        super(localizationService.localizeResourceName(csvSource), questionParserService);
    }
}

