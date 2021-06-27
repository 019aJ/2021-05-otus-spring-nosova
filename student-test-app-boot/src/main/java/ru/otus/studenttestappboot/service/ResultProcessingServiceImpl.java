package ru.otus.studenttestappboot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.studenttestappboot.domain.QuestionResult;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:application.yaml")
public class ResultProcessingServiceImpl implements ResultProcessingService {
    private final AskingService askingService;
    private final Integer passingScore;

    public ResultProcessingServiceImpl(@Value("${passingScore}") Integer passingScore, AskingService askingService) {
        this.askingService = askingService;
        this.passingScore = passingScore;
    }

    @Override
    public void process() {
        List<QuestionResult> questionResults = askingService.ask();
        questionResults.stream().filter(x -> "pq1".equals(x.getQuestion().getId())).findAny().ifPresent(name ->
                System.out.println(name.getUserAnswer() + ", here are your results: "));
        List<String> rightAnswers = new ArrayList<>();
        List<String> allAnswers = new ArrayList<>();
        questionResults.stream().filter(x -> !"pq1".equals(x.getQuestion().getId()) && x.getRightAnswer() != null).forEach(r -> {
            System.out.println(r.getQuestion().getQuestionText());
            System.out.println("Right answer: " + r.getRightAnswer().getText());
            if (r.getRightAnswer().getText().equalsIgnoreCase(r.getUserAnswer())) {
                System.out.println("It's correct");
                rightAnswers.add(r.getQuestion().getId());
            } else {
                System.out.println("Your answer: " + r.getUserAnswer());
                System.out.println("It's wrong");
            }
            allAnswers.add(r.getQuestion().getId());
        });
        System.out.println("Your score is " + rightAnswers.size() + "/" + allAnswers.size());
        System.out.println(rightAnswers.size() >= passingScore ? "Well done!" : "Try again next week");

    }
}
