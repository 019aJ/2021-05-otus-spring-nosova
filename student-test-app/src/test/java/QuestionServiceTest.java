import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import ru.otus.studenttestapp.domain.Answer;
import ru.otus.studenttestapp.domain.Question;
import ru.otus.studenttestapp.service.QuestionParserService;
import ru.otus.studenttestapp.service.QuestionParserServiceCSVImpl;
import ru.otus.studenttestapp.service.QuestionServiceCSVImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class QuestionServiceTest {

    @Test
    public void parseQuestions() {
        QuestionServiceCSVImpl service = new QuestionServiceCSVImpl("questions.csv", new QuestionParserServiceCSVImpl());
        List<Question> questions = service.questions();
        assertTrue(questions.size() == 5);
        Question question = questions.get(0);
        assertNotNull(question);
        assertEquals("Kerry … swim very well yet.", question.getQuestionText());
        List<Answer> answers = question.getAnswerVariants();
        assertNotNull(answers);
        assertTrue(answers.size() == 3);
        assertArrayEquals(new String[]{"shouldn't", "ought", "cannot"}, answers.stream().map(a -> a.getText()).collect(Collectors.toList()).toArray(new String[3]));
    }

    @Test
    public void parseOneQuestion() {
        QuestionServiceCSVImpl service = new QuestionServiceCSVImpl("", new QuestionParserServiceCSVImpl());
        List<Question> questions = service.questions();
        assertTrue(questions.size() == 0);
    }
}
