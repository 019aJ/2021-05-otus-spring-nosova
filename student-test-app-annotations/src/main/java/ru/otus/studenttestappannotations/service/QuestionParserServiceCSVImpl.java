package ru.otus.studenttestappannotations.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.studenttestappannotations.domain.Answer;
import ru.otus.studenttestappannotations.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class QuestionParserServiceCSVImpl implements QuestionParserService {

    private static final String COMMA_DELIMITER = ",";

    @Override
    public List<Question> parse(Scanner csvSource) {
        List<Question> questions = new ArrayList<>();
        while (csvSource != null && csvSource.hasNextLine()) {
            String questionLine = csvSource.nextLine();
            if (!StringUtils.isEmpty(questionLine)) {
                questions.add(getQuestionFromLine(questionLine));
            }
        }
        return questions;
    }

    private Question getQuestionFromLine(String line) {
        String questionId = null;
        String questionText = null;
        List<Answer> answers = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                String cellText = rowScanner.next();
                if(questionId == null){
                    questionId = cellText;
                }
                if (questionText == null) {
                    questionText = cellText;
                } else {
                    answers.add(new Answer(cellText));
                }
            }
        }
        return new Question(questionId, questionText, answers);
    }
}
