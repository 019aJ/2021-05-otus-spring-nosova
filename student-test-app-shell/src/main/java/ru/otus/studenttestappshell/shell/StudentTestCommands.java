package ru.otus.studenttestappshell.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.studenttestappshell.service.ResultProcessingService;

@ShellComponent
public class StudentTestCommands {

    private final ResultProcessingService resultProcessingService;

    public StudentTestCommands(ResultProcessingService resultProcessingService){
        this.resultProcessingService = resultProcessingService;
    }

    @ShellMethod(value = "Start test command", key = {"s", "start"})
    public void startTest() {
        resultProcessingService.process();
    }

}
