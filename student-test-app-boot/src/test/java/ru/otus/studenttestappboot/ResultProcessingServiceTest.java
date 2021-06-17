package ru.otus.studenttestappboot;

import org.junit.jupiter.api.Test;
import ru.otus.studenttestappboot.domain.Answer;
import ru.otus.studenttestappboot.domain.Question;
import ru.otus.studenttestappboot.domain.QuestionResult;
import ru.otus.studenttestappboot.service.AskingService;
import ru.otus.studenttestappboot.service.ResultProcessingServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ResultProcessingServiceTest {
    @Test
    public void rightAnswer() {
        AskingService askingService = () -> {
                List<QuestionResult> result = new ArrayList<>();
                Answer a1 = new Answer("a1");
                Answer a2 = new Answer("a2");
                List<Answer> ans = new ArrayList<>();
                ans.add(a1);
                ans.add(a2);
                Question q = new Question("1", "qst", ans);
                QuestionResult res = new QuestionResult(q, a1, "a1");
                result.add(res);
                return result;
        };
        ResultProcessingServiceImpl resultProcessingServiceImpl = new ResultProcessingServiceImpl(1, askingService);
        resultProcessingServiceImpl.process();
    }

    @Test
    public void wrongAnswer() {
        AskingService askingService = () -> {
                List<QuestionResult> result = new ArrayList<>();
                Answer a1 = new Answer("a1");
                Answer a2 = new Answer("a2");
                List<Answer> ans = new ArrayList<>();
                ans.add(a1);
                ans.add(a2);
                Question q = new Question("1", "qst", ans);
                QuestionResult res = new QuestionResult(q, a1, "a2");
                result.add(res);
                return result;
        };
        ResultProcessingServiceImpl resultProcessingServiceImpl = new ResultProcessingServiceImpl(1, askingService);
        resultProcessingServiceImpl.process();
    }
}
