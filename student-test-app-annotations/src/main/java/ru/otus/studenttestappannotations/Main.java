package ru.otus.studenttestappannotations;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.studenttestappannotations.service.ResultProcessingService;

@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        ResultProcessingService resultProcessingService = context.getBean(ResultProcessingService.class);
        resultProcessingService.process();
    }
}
