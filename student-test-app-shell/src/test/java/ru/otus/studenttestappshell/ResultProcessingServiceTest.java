package ru.otus.studenttestappshell;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import ru.otus.studenttestappshell.domain.Answer;
import ru.otus.studenttestappshell.domain.Question;
import ru.otus.studenttestappshell.domain.QuestionResult;
import ru.otus.studenttestappshell.service.AskingService;
import ru.otus.studenttestappshell.service.LocalizationService;
import ru.otus.studenttestappshell.service.LocalizationServiceImpl;
import ru.otus.studenttestappshell.service.ResultProcessingServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
@SpringBootTest
public class ResultProcessingServiceTest {
    @TestConfiguration
    static class NestedConfiguration {
        @Bean
        LocalizationService localizationService(MessageSource msg) {
            return new LocalizationServiceImpl("ru-RU", msg);
        }
    }
    @Autowired
    LocalizationService localizationService;
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
        ResultProcessingServiceImpl resultProcessingServiceImpl = new ResultProcessingServiceImpl(1, askingService, localizationService);
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
        ResultProcessingServiceImpl resultProcessingServiceImpl = new ResultProcessingServiceImpl(1, askingService, localizationService);
        resultProcessingServiceImpl.process();
    }
}
