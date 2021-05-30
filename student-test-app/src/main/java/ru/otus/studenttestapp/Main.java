package ru.otus.studenttestapp;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.studenttestapp.domain.Answer;
import ru.otus.studenttestapp.domain.Question;
import ru.otus.studenttestapp.service.AskingService;
import ru.otus.studenttestapp.service.QuestionService;

import java.util.List;


public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService service = context.getBean(QuestionService.class);
        List<Question> questions = service.questions();
        AskingService askingService = context.getBean(AskingService.class);
        askingService.ask(questions);
    }
}
