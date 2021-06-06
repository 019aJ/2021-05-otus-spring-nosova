package ru.otus.studenttestappannotations.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service("examQuestions")
@PropertySource("classpath:application.properties")
public class ExamQuestionServiceCSVImpl extends QuestionServiceCSVImpl implements QuestionService {
    public ExamQuestionServiceCSVImpl(@Value("${question.path}") String csvSource, QuestionParserService questionParserService) {
        super(csvSource, questionParserService);
    }
}
