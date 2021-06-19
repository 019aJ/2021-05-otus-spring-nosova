import org.junit.Test;
import org.mockito.Mockito;
import ru.otus.studenttestappannotations.domain.Question;
import ru.otus.studenttestappannotations.service.LineParserServiceCSVImpl;
import ru.otus.studenttestappannotations.service.QuestionParserService;
import ru.otus.studenttestappannotations.service.QuestionParserServiceCSVImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class QuestionParserServiceTest {
    @Test
    public void parseOneQuestion() {
        QuestionParserService service = new QuestionParserServiceCSVImpl(new LineParserServiceCSVImpl());
        List<Question> questions = new ArrayList<>();
        try (Scanner scanner = new Scanner("Kerry … swim very well yet.,shouldn't,ought,cannot")) {
            questions.addAll(service.parse(scanner));
        }
        assertTrue(questions.size() == 1);
    }
    @Test
    public void parseEmpty() {
        QuestionParserService service = new QuestionParserServiceCSVImpl(Mockito.mock(LineParserServiceCSVImpl.class));
        List<Question> questions = new ArrayList<>();
        try (Scanner scanner = new Scanner("")) {
            questions.addAll(service.parse(scanner));
        }
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
