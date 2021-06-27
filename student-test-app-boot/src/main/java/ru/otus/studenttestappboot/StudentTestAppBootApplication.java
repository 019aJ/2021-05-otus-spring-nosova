package ru.otus.studenttestappboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.studenttestappboot.service.ResultProcessingService;

@SpringBootApplication
public class StudentTestAppBootApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(StudentTestAppBootApplication.class, args);

        ResultProcessingService resultProcessingService = context.getBean(ResultProcessingService.class);
        resultProcessingService.process();
    }

}
