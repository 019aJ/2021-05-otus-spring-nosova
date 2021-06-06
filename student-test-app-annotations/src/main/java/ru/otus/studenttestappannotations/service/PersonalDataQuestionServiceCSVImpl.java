package ru.otus.studenttestappannotations.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service("personalQuestions")
@PropertySource("classpath:application.properties")
public class PersonalDataQuestionServiceCSVImpl extends QuestionServiceCSVImpl implements QuestionService {
    public PersonalDataQuestionServiceCSVImpl(@Value("${personalQuestion.path}") String csvSource, QuestionParserService questionParserService) {
        super(csvSource, questionParserService);
    }
}

