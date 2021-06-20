package ru.otus.studenttestappshell;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import ru.otus.studenttestappshell.domain.Answer;
import ru.otus.studenttestappshell.domain.Question;
import ru.otus.studenttestappshell.service.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class QuestionServiceTest {

    @TestConfiguration
    static class NestedConfiguration {
    }

    @Autowired
    QuestionParserService questionParserService;

    @Test
    public void parseQuestions() {
        QuestionServiceCSVImpl service = new QuestionServiceCSVImpl("/questions.csv", questionParserService);
        List<Question> questions = service.questions();
        assertTrue(questions.size() == 1);
        Question question = questions.get(0);
        assertNotNull(question);
        assertEquals("Kerry ... swim very well yet.", question.getQuestionText());
        List<Answer> answers = question.getAnswerVariants();
        assertNotNull(answers);
        assertTrue(answers.size() == 3);
        assertArrayEquals(new String[]{"shouldn't", "ought", "cannot"}, answers.stream().map(Answer::getText).collect(Collectors.toList()).toArray(new String[3]));
    }

    @Test
    public void parseEmptySoucre() {
        QuestionServiceCSVImpl service = new QuestionServiceCSVImpl("", Mockito.mock(QuestionParserServiceCSVImpl.class));
        List<Question> questions = service.questions();
        assertTrue(questions.size() == 0);
    }
}
