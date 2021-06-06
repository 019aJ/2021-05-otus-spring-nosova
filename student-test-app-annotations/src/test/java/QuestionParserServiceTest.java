import org.junit.Test;
import ru.otus.studenttestappannotations.domain.Answer;
import ru.otus.studenttestappannotations.domain.Question;
import ru.otus.studenttestappannotations.service.QuestionParserService;
import ru.otus.studenttestappannotations.service.QuestionParserServiceCSVImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class QuestionParserServiceTest {
    @Test
    public void parseOneQuestion() {
        QuestionParserService service = new QuestionParserServiceCSVImpl();
        List<Question> questions = new ArrayList<>();
        try (Scanner scanner = new Scanner("Kerry … swim very well yet.,shouldn't,ought,cannot")) {
            questions.addAll(service.parse(scanner));
        }
        assertTrue(questions.size() == 1);
        Question question = questions.get(0);
        assertNotNull(question);
        assertEquals("Kerry … swim very well yet.", question.getQuestionText());
        List<Answer> answers = question.getAnswerVariants();
        assertNotNull(answers);
        assertTrue(answers.size() == 3);
        assertArrayEquals(new String[]{"shouldn't", "ought", "cannot"}, answers.stream().map(a -> a.getText()).collect(Collectors.toList()).toArray(new String[3]));
    }
    @Test
    public void parseEmpty() {
        QuestionParserService service = new QuestionParserServiceCSVImpl();
        List<Question> questions = new ArrayList<>();
        try (Scanner scanner = new Scanner("")) {
            questions.addAll(service.parse(scanner));
        }
        assertTrue(questions.size() == 0);
    }
    @Test
    public void parseNull() {
        QuestionParserService service = new QuestionParserServiceCSVImpl();
        List<Question> questions = new ArrayList<>();
        questions.addAll(service.parse(null));
        assertTrue(questions.size() == 0);
    }
    @Test
    public void parseFreeQuestion() {
        QuestionParserService service = new QuestionParserServiceCSVImpl();
        List<Question> questions = new ArrayList<>();
        try (Scanner scanner = new Scanner("Kerry … swim very well yet.")) {
            questions.addAll(service.parse(scanner));
        }
        assertTrue(questions.size() == 1);
        Question question = questions.get(0);
        assertNotNull(question);
        assertEquals("Kerry … swim very well yet.", question.getQuestionText());
        List<Answer> answers = question.getAnswerVariants();
        assertTrue(answers == null || answers.size() == 0);
    }
}
