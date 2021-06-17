package ru.otus.studenttestappboot;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.studenttestappboot.domain.Answer;
import ru.otus.studenttestappboot.domain.Question;
import ru.otus.studenttestappboot.service.LineParserServiceCSVImpl;
import ru.otus.studenttestappboot.service.QuestionParserServiceCSVImpl;
import ru.otus.studenttestappboot.service.QuestionServiceCSVImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;


public class QuestionServiceTest {

    @Test
    public void parseQuestions() {
        QuestionServiceCSVImpl service = new QuestionServiceCSVImpl("/questions.csv", new QuestionParserServiceCSVImpl(new LineParserServiceCSVImpl()));
        List<Question> questions = service.questions();
        assertTrue(questions.size() == 5);
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
