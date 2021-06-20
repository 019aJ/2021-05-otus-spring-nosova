package ru.otus.studenttestappshell.service;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import ru.otus.studenttestappshell.domain.Answer;
import ru.otus.studenttestappshell.domain.Question;
import ru.otus.studenttestappshell.domain.QuestionResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class AskingServiceConsolImpl implements AskingService {

    private final QuestionService examQuestions;
    private final QuestionService personalQuestions;
    private final RightAnswerService answerService;

    public AskingServiceConsolImpl(QuestionService examQuestions, QuestionService personalQuestions, RightAnswerService answerService) {
        this.examQuestions = examQuestions;
        this.personalQuestions = personalQuestions;
        this.answerService = answerService;
    }

    @Override
    public List<QuestionResult> ask() {
        List<QuestionResult> questionResults = new ArrayList<>();
        Map<String, Answer> rightAnswers = answerService.rightAnswers();
        questionResults.addAll(
                personalQuestions.questions().stream().
                map(q -> askQuestion(q, rightAnswers)).
                collect(Collectors.toList()));
        questionResults.addAll(
                examQuestions.questions().stream().
                map(q -> askQuestion(q, rightAnswers)).
                collect(Collectors.toList()));

        if (CollectionUtils.isEmpty(questionResults)) {
            System.out.println("No question in this test. Take a rest=)");
            return new ArrayList<>();
        }
        return questionResults;
    }

    private QuestionResult askQuestion(Question q, Map<String, Answer> rightAnswers) {
        System.out.println(q.getQuestionText());
        List<Answer> answers = q.getAnswerVariants();
        if (!CollectionUtils.isEmpty(answers)) {
            answers.forEach(a -> System.out.println(" * " + a.getText()));
        }
        return getAnswer(q, rightAnswers);
    }

    private QuestionResult getAnswer(Question q, Map<String, Answer> rightAnswers) {
        Scanner scanner = new Scanner(System.in);
        String answerText = scanner.nextLine();
        return new QuestionResult(q, rightAnswers.get(q.getId()), answerText);
    }
}
