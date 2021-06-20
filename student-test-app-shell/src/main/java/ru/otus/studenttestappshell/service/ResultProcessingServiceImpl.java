package ru.otus.studenttestappshell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.studenttestappshell.domain.QuestionResult;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:application.yaml")
@Slf4j
public class ResultProcessingServiceImpl implements ResultProcessingService {
    private final AskingService askingService;
    private final Integer passingScore;
    private final LocalizationService localizationService;

    public ResultProcessingServiceImpl(@Value("${passingScore}") Integer passingScore, AskingService askingService, LocalizationService localizationService) {
        this.askingService = askingService;
        this.passingScore = passingScore;
        this.localizationService = localizationService;
    }

    @Override
    public void process() {
        List<QuestionResult> questionResults = askingService.ask();
        questionResults.stream().filter(x -> "pq1".equals(x.getQuestion().getId())).findAny().ifPresent(name ->
                log.info(name.getUserAnswer() + localizationService.localize("resultGreeting")));
        List<String> rightAnswers = new ArrayList<>();
        List<String> allAnswers = new ArrayList<>();
        questionResults.stream().filter(x -> !"pq1".equals(x.getQuestion().getId()) && x.getRightAnswer() != null).forEach(r -> {
            log.info(r.getQuestion().getQuestionText());
            log.info(localizationService.localize("rightAnswer") + " " + r.getRightAnswer().getText());
            if (r.getRightAnswer().getText().equalsIgnoreCase(r.getUserAnswer())) {
                log.info(localizationService.localize("rightStatement"));
                rightAnswers.add(r.getQuestion().getId());
            } else {
                log.info(localizationService.localize("yourAnswer") + " " + r.getUserAnswer());
                log.info(localizationService.localize("wrongStatement"));
            }
            allAnswers.add(r.getQuestion().getId());
        });
        log.info(localizationService.localize("scores") + " " + rightAnswers.size() + "/" + allAnswers.size());
        log.info(rightAnswers.size() >= passingScore ? localizationService.localize("goodResult") : localizationService.localize("badResult"));
    }
}
