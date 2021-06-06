package ru.otus.studenttestappannotations.service;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import ru.otus.studenttestappannotations.domain.Answer;
import ru.otus.studenttestappannotations.domain.Question;
import ru.otus.studenttestappannotations.domain.QuestionResult;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AskingServiceConsolImpl implements AskingService {

    private final List<Question> questions = new ArrayList<>();
    private final Map<String, Answer> rightAnswers = new HashMap<>();


    public AskingServiceConsolImpl(QuestionService examQuestions, QuestionService personalQuestions, RightAnswerService answerService) {
        questions.addAll(personalQuestions.questions());
        questions.addAll(examQuestions.questions());
        rightAnswers.putAll(answerService.rightAnswers());
    }

    @Override
    public List<QuestionResult> ask() {
        if (CollectionUtils.isEmpty(questions)) {
            System.out.println("No question in this test. Take a rest=)");
            return new ArrayList<>();
        }
        return questions.stream().map(this::askQuestion).collect(Collectors.toList());
    }

    protected QuestionResult askQuestion(Question q) {
        System.out.println(q.getQuestionText());
        List<Answer> answers = q.getAnswerVariants();
        if (!CollectionUtils.isEmpty(answers)) {
            answers.forEach(a -> System.out.println(" * " + a.getText()));
        }
        return getAnswer(q);
    }

    protected QuestionResult getAnswer(Question q) {
        Scanner scanner = new Scanner(System.in);
        String answerText = scanner.nextLine();
        return new QuestionResult(q, rightAnswers.get(q.getId()), answerText);
    }
}
