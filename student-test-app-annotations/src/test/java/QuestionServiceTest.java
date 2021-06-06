import org.junit.Test;
import ru.otus.studenttestappannotations.domain.Answer;
import ru.otus.studenttestappannotations.domain.Question;
import ru.otus.studenttestappannotations.service.QuestionParserServiceCSVImpl;
import ru.otus.studenttestappannotations.service.QuestionServiceCSVImpl;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class QuestionServiceTest {

    @Test
    public void parseQuestions() {
        QuestionServiceCSVImpl service = new QuestionServiceCSVImpl("/questions.csv", new QuestionParserServiceCSVImpl());
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
    public void parseOneQuestion() {
        QuestionServiceCSVImpl service = new QuestionServiceCSVImpl("", new QuestionParserServiceCSVImpl());
        List<Question> questions = service.questions();
        assertTrue(questions.size() == 0);
    }
}
