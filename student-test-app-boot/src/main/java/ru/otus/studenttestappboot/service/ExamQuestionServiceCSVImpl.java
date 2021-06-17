package ru.otus.studenttestappboot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service("examQuestions")
@PropertySource("classpath:application.yaml")
public class ExamQuestionServiceCSVImpl extends QuestionServiceCSVImpl implements QuestionService {
    public ExamQuestionServiceCSVImpl(@Value("${question}") String csvSource, MessageSource msg,  QuestionParserService questionParserService) {
        super(msg.getMessage(csvSource, new String[0], Locale.forLanguageTag("ru-RU")), questionParserService);
    }
}
