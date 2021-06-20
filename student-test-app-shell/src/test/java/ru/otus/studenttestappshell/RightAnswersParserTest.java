package ru.otus.studenttestappshell;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import ru.otus.studenttestappshell.domain.Answer;
import ru.otus.studenttestappshell.domain.Question;
import ru.otus.studenttestappshell.domain.QuestionResult;
import ru.otus.studenttestappshell.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RightAnswersParserTest {
    @TestConfiguration
    static class NestedConfiguration {
    }

    @Autowired
    RightAnswersParserService rightAnswersParserService;

    @Test
    public void rightAnswer() {
        Map<String, Answer> result;
        try (Scanner csvSource = new Scanner("1,cannot" + System.getProperty("line.separator") + "2,can")) {
            result = rightAnswersParserService.parse(csvSource);
        }
        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertEquals(result.get("1").getText(), "cannot");
        assertEquals(result.get("2").getText(), "can");
    }
}
