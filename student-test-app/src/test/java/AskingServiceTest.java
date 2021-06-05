import org.junit.Test;
import ru.otus.studenttestapp.domain.Answer;
import ru.otus.studenttestapp.domain.Question;
import ru.otus.studenttestapp.service.AskingServiceConsolReadonlyImpl;

import java.util.ArrayList;
import java.util.List;

public class AskingServiceTest {
    @Test
    public void askEmptyQuestionList() {
        AskingServiceConsolReadonlyImpl askingService = new AskingServiceConsolReadonlyImpl();
        askingService.ask(null);
    }

    @Test
    public void askVariantQuestionList() {
        AskingServiceConsolReadonlyImpl askingService = new AskingServiceConsolReadonlyImpl();
        List<Question> questions = new ArrayList<>();
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("first answer"));
        Question question = new Question("I want to know:", answers);
        questions.add(question);
        askingService.ask(questions);
    }

    @Test
    public void askFreeQuestionWithNull() {
        AskingServiceConsolReadonlyImpl askingService = new AskingServiceConsolReadonlyImpl();
        List<Question> questions = new ArrayList<>();
        Question question = new Question("What is it?", null);
        questions.add(question);
        askingService.ask(questions);
    }

    @Test
    public void askFreeQuestionWithEmptyList() {
        AskingServiceConsolReadonlyImpl askingService = new AskingServiceConsolReadonlyImpl();
        List<Question> questions = new ArrayList<>();
        Question question = new Question("And what is it?", new ArrayList<>());
        questions.add(question);
        askingService.ask(questions);
    }

    @Test
    public void askVariantQuestionListWithEmptyAnswers() {
        AskingServiceConsolReadonlyImpl askingService = new AskingServiceConsolReadonlyImpl();
        List<Question> questions = new ArrayList<>();
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(null));
        Question question = new Question("I want to know:", answers);
        questions.add(question);
        askingService.ask(questions);
    }
}
