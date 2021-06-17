package ru.otus.studenttestappboot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service("personalQuestions")
@PropertySource("classpath:application.yaml")
public class PersonalDataQuestionServiceCSVImpl extends QuestionServiceCSVImpl implements QuestionService {
    public PersonalDataQuestionServiceCSVImpl(@Value("${personalQuestion}") String csvSource, MessageSource msg, QuestionParserService questionParserService) {
        super(msg.getMessage(csvSource, new String[0], Locale.getDefault()), questionParserService);
    }
}

