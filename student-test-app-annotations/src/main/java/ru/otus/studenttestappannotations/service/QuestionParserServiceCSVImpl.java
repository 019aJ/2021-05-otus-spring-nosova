package ru.otus.studenttestappannotations.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.studenttestappannotations.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class QuestionParserServiceCSVImpl implements QuestionParserService {

    private LineParserService lineParserService;

    public QuestionParserServiceCSVImpl(LineParserService lineParserService) {
        this.lineParserService = lineParserService;
    }

    @Override
    public List<Question> parse(Scanner csvSource) {
        List<Question> questions = new ArrayList<>();
        while (csvSource != null && csvSource.hasNextLine()) {
            String questionLine = csvSource.nextLine();
            if (!StringUtils.isEmpty(questionLine)) {
                questions.add(lineParserService.getQuestionFromLine(questionLine));
            }
        }
        return questions;
    }
}
