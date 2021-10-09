package ru.otus.springintegration.services;

import org.springframework.stereotype.Service;
import ru.otus.springintegration.domain.Person;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Revision {
    public String revise(List<Person> p) {
        return p.stream().map(x -> x.getName() + ": \r\n" +
                String.join("\r\n", x.getAchievements()) + "\r\n Уровень счастья: " + x.getHappiness()).collect(Collectors.joining("\r\n\r\n"));
    }

    public String reviseHappiness(List<Person> p) {
        if (p.size() > 0) {
            if (p.get(0).getHappiness() > 100) {
                return "Веселенькие пенсионеры: " + p.stream().map(Person::getName).collect(Collectors.joining(", "));
            } else {
                return "Унылые пенсионеры: " + p.stream().map(Person::getName).collect(Collectors.joining(", "));
            }
        }
        return "";
    }
}
