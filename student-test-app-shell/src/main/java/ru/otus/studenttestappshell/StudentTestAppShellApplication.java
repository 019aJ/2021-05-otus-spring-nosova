package ru.otus.studenttestappshell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.studenttestappshell.service.ResultProcessingService;

@SpringBootApplication
public class StudentTestAppShellApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(StudentTestAppShellApplication.class, args);
        /*ResultProcessingService resultProcessingService = context.getBean(ResultProcessingService.class);
        resultProcessingService.process();*/
    }

}
