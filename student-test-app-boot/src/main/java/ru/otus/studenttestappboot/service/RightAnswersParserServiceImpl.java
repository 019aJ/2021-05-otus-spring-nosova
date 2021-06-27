package ru.otus.studenttestappboot.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.studenttestappboot.domain.Answer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Slf4j
@Service
public class RightAnswersParserServiceImpl implements  RightAnswersParserService{
    private static final String COMMA_DELIMITER = ",";

    @Override
    public Map<String, Answer> parse(Scanner csvSource) {
        Map<String, Answer> answers = new HashMap<>();
        while (csvSource != null && csvSource.hasNextLine()) {
            String questionLine = csvSource.nextLine();
            if (!StringUtils.isEmpty(questionLine)) {
               getAnswerFromLine(questionLine, answers);
            }
        }
        return answers;
    }

    private void getAnswerFromLine(String line, Map<String, Answer> answers) {
        String questionId = null;
        Answer answer = null;
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            if(rowScanner.hasNext()){
                questionId = rowScanner.next();
                if(StringUtils.isEmpty(questionId)){
                    log.error("invalid answer line - no question");
                    return;
                }
            }
            if(rowScanner.hasNext()){
                String answerText = rowScanner.next();
                if(StringUtils.isEmpty(answerText)){
                    log.error("invalid answer - it's empty");
                    return;
                }
                answers.put(questionId, new Answer(answerText));
            }
        }
    }
}
