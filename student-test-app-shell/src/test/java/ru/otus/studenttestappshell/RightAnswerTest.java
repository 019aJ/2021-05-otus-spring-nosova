package ru.otus.studenttestappshell;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import ru.otus.studenttestappshell.domain.Answer;
import ru.otus.studenttestappshell.service.LocalizationService;
import ru.otus.studenttestappshell.service.LocalizationServiceImpl;
import ru.otus.studenttestappshell.service.RightAnswerService;
import ru.otus.studenttestappshell.service.RightAnswersParserService;

import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest

public class RightAnswerTest {
    @TestConfiguration
    static class NestedConfiguration {
        @Bean
        LocalizationService localizationService(MessageSource msg) {
            return new LocalizationServiceImpl("", msg);
        }
    }
    @Autowired
    RightAnswerService rightAnswerService;

    @Test
    public void rightAnswer() {
        Map<String, Answer> result = rightAnswerService.rightAnswers();
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEquals(result.get("1").getText(), "cannot");
    }
}
