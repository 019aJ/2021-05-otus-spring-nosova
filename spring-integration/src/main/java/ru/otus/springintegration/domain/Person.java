package ru.otus.springintegration.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Person {
    private final String name;
    private final GENDER gender;
    private final List<String> achievements = new ArrayList<>();
    @Setter
    int happiness = 100;
    @Setter
    private int age = 0;
    @Setter
    private boolean married;
    @Setter
    private boolean partnerName;

    public Person(String name, GENDER gender) {
        this.name = name;
        this.gender = gender;
    }

    public Person(String name, GENDER gender, List<String> achievements) {
        this.name = name;
        this.gender = gender;
        this.achievements.addAll(achievements);
    }

    public enum GENDER {
        MAN, WOMAN
    }
}
