package ru.otus.libraryjpaapp.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
@NoArgsConstructor
public class ReadInputConsolImpl implements ReadInputService {
    @Override
    public Map<String, String> ask(List<String> questions) {
        Map<String, String> questionResults = new HashMap<>();
        questions.forEach(q -> questionResults.put(q, askQuestion(q)));
        return questionResults;
    }

    private String askQuestion(String q) {
        System.out.println(q + ": ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
