import org.junit.Test;
import ru.otus.studenttestappannotations.domain.Answer;
import ru.otus.studenttestappannotations.domain.Question;
import ru.otus.studenttestappannotations.service.LineParserServiceCSVImpl;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class LineParserSeviceTest {
    @Test
    public void parseOneQuestion() {
        LineParserServiceCSVImpl service = new LineParserServiceCSVImpl();
        Question question = service.getQuestionFromLine("1,Kerry … swim very well yet.,shouldn't,ought,cannot");
        assertNotNull(question);
        assertEquals("Kerry … swim very well yet.", question.getQuestionText());
        List<Answer> answers = question.getAnswerVariants();
        assertNotNull(answers);
        assertTrue(answers.size() == 3);
        assertArrayEquals(new String[]{"shouldn't", "ought", "cannot"}, answers.stream().map(a -> a.getText()).collect(Collectors.toList()).toArray(new String[3]));
    }

    @Test
    public void parseFreeQuestion() {
        LineParserServiceCSVImpl service = new LineParserServiceCSVImpl();
        Question question = service.getQuestionFromLine("1,Kerry … swim very well yet.");
        assertNotNull(question);
        assertEquals("Kerry … swim very well yet.", question.getQuestionText());
        List<Answer> answers = question.getAnswerVariants();
        assertTrue(answers == null || answers.size() == 0);
    }

}
