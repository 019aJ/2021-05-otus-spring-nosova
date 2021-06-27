package ru.otus.studenttestappboot.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.studenttestappboot.domain.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@Slf4j
public class QuestionParserServiceCSVImpl implements QuestionParserService {

    private LineParserService lineParserService;

    public QuestionParserServiceCSVImpl(LineParserService lineParserService) {
        this.lineParserService = lineParserService;
    }

    @Override
    public List<Question> parse(Resource resource) {
        List<Question> questions = new ArrayList<>();
        if (resource == null) {
            return questions;
        }
        try (Scanner csvSource = new Scanner(resource.getInputStream())) {
            while (csvSource.hasNextLine()) {
                String questionLine = csvSource.nextLine();
                if (!StringUtils.isEmpty(questionLine)) {
                    questions.add(lineParserService.getQuestionFromLine(questionLine));
                }
            }
        } catch (IOException e) {
            log.error("Cant read resource ", e);
        }
        return questions;
    }
}
