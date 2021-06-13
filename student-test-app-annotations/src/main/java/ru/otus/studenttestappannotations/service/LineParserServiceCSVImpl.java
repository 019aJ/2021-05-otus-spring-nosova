package ru.otus.studenttestappannotations.service;

import org.springframework.stereotype.Service;
import ru.otus.studenttestappannotations.domain.Answer;
import ru.otus.studenttestappannotations.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class LineParserServiceCSVImpl implements LineParserService {

    private static final String COMMA_DELIMITER = ",";

    public Question getQuestionFromLine(String line) {
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
                else if (questionText == null) {
                    questionText = cellText;
                } else {
                    answers.add(new Answer(cellText));
                }
            }
        }
        return new Question(questionId, questionText, answers);
    }
}
