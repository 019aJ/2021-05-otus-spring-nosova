package ru.otus.studenttestapp.service;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import ru.otus.studenttestapp.domain.Answer;
import ru.otus.studenttestapp.domain.Question;

import java.util.List;

public class AskingServiceConsolReadonlyImpl implements AskingService {
    @Override
    public void ask(List<Question> questions) {
        if (CollectionUtils.isEmpty(questions)){
            System.out.println("No question in this test. Take a rest=)");
            return;
        }

        questions.forEach(q -> {
            System.out.println(q.getQuestionText());
            List<Answer> answers = q.getAnswerVariants();
            if (!CollectionUtils.isEmpty(answers)) {
                answers.forEach(a -> System.out.println(" * " + a.getText()));
            } else {
                System.out.println("Type your answer here: ");
            }
        });
    }
}
