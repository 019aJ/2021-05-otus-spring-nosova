package ru.otus.studenttestappshell;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ByteArrayResource;
import ru.otus.studenttestappshell.domain.Question;
import ru.otus.studenttestappshell.service.LineParserServiceCSVImpl;
import ru.otus.studenttestappshell.service.QuestionParserService;
import ru.otus.studenttestappshell.service.QuestionParserServiceCSVImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class QuestionParserServiceTest {
    @Test
    public void parseOneQuestion() {
        QuestionParserService service = new QuestionParserServiceCSVImpl(Mockito.mock(LineParserServiceCSVImpl.class));
        List<Question> questions = new ArrayList<>();
        questions.addAll(service.parse(new ByteArrayResource("Kerry … swim very well yet.,shouldn't,ought,cannot".getBytes())));
        assertTrue(questions.size() == 1);
    }

    @Test
    public void parseEmpty() {
        QuestionParserService service = new QuestionParserServiceCSVImpl(Mockito.mock(LineParserServiceCSVImpl.class));
        List<Question> questions = new ArrayList<>();
        questions.addAll(service.parse(new ByteArrayResource("".getBytes())));
        assertTrue(questions.size() == 0);
    }

    @Test
    public void parseNull() {
        QuestionParserService service = new QuestionParserServiceCSVImpl(Mockito.mock(LineParserServiceCSVImpl.class));
        List<Question> questions = new ArrayList<>();
        questions.addAll(service.parse(null));
        assertTrue(questions.size() == 0);
    }
}
