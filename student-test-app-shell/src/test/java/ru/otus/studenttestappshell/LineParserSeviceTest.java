package ru.otus.studenttestappshell;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import ru.otus.studenttestappshell.domain.Answer;
import ru.otus.studenttestappshell.domain.Question;
import ru.otus.studenttestappshell.service.LineParserService;
import ru.otus.studenttestappshell.service.LineParserServiceCSVImpl;
import ru.otus.studenttestappshell.service.QuestionParserService;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class LineParserSeviceTest {

    @TestConfiguration
    static class NestedConfiguration {
    }

    @Autowired
    LineParserService service;

    @Test
    public void parseOneQuestion() {
        Question question = service.getQuestionFromLine("1,Kerry … swim very well yet.,shouldn't,ought,cannot");
        assertNotNull(question);
        assertEquals("Kerry … swim very well yet.", question.getQuestionText());
        List<Answer> answers = question.getAnswerVariants();
        assertNotNull(answers);
        assertTrue(answers.size() == 3);
        assertArrayEquals(new String[]{"shouldn't", "ought", "cannot"}, answers.stream().map(Answer::getText).collect(Collectors.toList()).toArray(new String[3]));
    }

    @Test
    public void parseFreeQuestion() {
        Question question = service.getQuestionFromLine("1,Kerry … swim very well yet.");
        assertNotNull(question);
        assertEquals("Kerry … swim very well yet.", question.getQuestionText());
        List<Answer> answers = question.getAnswerVariants();
        assertTrue(answers == null || answers.size() == 0);
    }

}
