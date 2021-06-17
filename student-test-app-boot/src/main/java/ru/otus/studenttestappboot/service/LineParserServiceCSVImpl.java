package ru.otus.studenttestappboot.service;

import org.springframework.stereotype.Service;
import ru.otus.studenttestappboot.domain.Answer;
import ru.otus.studenttestappboot.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class LineParserServiceCSVImpl implements LineParserService {

    private static final String COMMA_DELIMITER = ",";

    @Override
    public Question getQuestionFromLine(String line) {
        String questionId = null;
        String questionText = null;
        List<Answer> answers = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                String cellText = rowScanner.next();
                if (questionId == null) {
                    questionId = cellText;
                } else if (questionText == null) {
                    questionText = cellText;
                } else {
                    answers.add(new Answer(cellText));
                }
            }
        }
        return new Question(questionId, questionText, answers);
    }
}
