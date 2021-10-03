package ru.otus.springintegration.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.springintegration.domain.Person;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AdultLife {

    public Person retire(Person person) {
        Person adult = new Person(person.getName(), person.getGender(), person.getAchievements());
        adult.setAge(adult.getGender() == Person.GENDER.WOMAN ? 65 : 67);
        adult.setHappiness(person.getHappiness() + 10);
        String text = "Дожил до пенсии";
        log.info(person.getName() + " " + text);
        adult.getAchievements().add(text);
        return adult;
    }

    public Person sacking(Person person) {
        Person adult = new Person(person.getName(), person.getGender(), person.getAchievements());
        adult.setAge(person.getAge() + RandomUtils.nextInt(0, 1));
        adult.setHappiness(person.getHappiness() - 30);
        String text = "Уволен с записью в трудовой";
        log.info(person.getName() + " " + text);
        adult.getAchievements().add(text);
        return adult;
    }

    public Person divorce(Person person) {
        Person adult = new Person(person.getName(), person.getGender(), person.getAchievements());
        adult.setAge(person.getAge() + RandomUtils.nextInt(0, 1));
        adult.setMarried(false);
        adult.setHappiness(person.getHappiness() - 50);
        log.info(person.getName() + " " + "В разводе");
        adult.getAchievements().add("В разводе");
        return adult;
    }

    public List<Person> privateLife(List<Person> persons) {
        int i = 0;
        List<Person> personsAfterPrivateLifeChanged = new ArrayList<>();
        while (i < persons.size()) {
            if (i + 1 < persons.size() && persons.get(i + 1).getGender() != persons.get(i).getGender()) {
                personsAfterPrivateLifeChanged.add(marriage(persons.get(i + 1), persons.get(i).getName()));
                personsAfterPrivateLifeChanged.add(marriage(persons.get(i), persons.get(i + 1).getName()));
                i += 2;
            } else if (persons.get(i).getGender() == Person.GENDER.MAN) {
                personsAfterPrivateLifeChanged.add(moto(persons.get(i)));
                i++;
            } else {
                personsAfterPrivateLifeChanged.add(cat(persons.get(i)));
                i++;
            }
        }
        return personsAfterPrivateLifeChanged;
    }

    private Person marriage(Person person, String partnerName) {
        Person adult = new Person(person.getName(), person.getGender(), person.getAchievements());
        adult.setAge(person.getAge() + RandomUtils.nextInt(0, 15));
        adult.setHappiness(person.getHappiness() + 10);
        adult.setMarried(true);
        String msg = person.getGender() == Person.GENDER.MAN ? "Женился на " + partnerName : "Вышла замуж за " + partnerName;
        log.info(person.getName() + " " + msg + ".");
        adult.getAchievements().add(msg);
        return adult;
    }

    private Person cat(Person person) {
        Person adult = new Person(person.getName(), person.getGender(), person.getAchievements());
        adult.setAge(person.getAge() + RandomUtils.nextInt(0, 10));
        adult.setHappiness(person.getHappiness() + 5);
        String text = "Завела кота";
        log.info(person.getName() + " " + text);
        adult.getAchievements().add(text);
        return adult;
    }

    private Person moto(Person person) {
        Person adult = new Person(person.getName(), person.getGender(), person.getAchievements());
        adult.setAge(person.getAge() + RandomUtils.nextInt(0, 2));
        adult.setHappiness(person.getHappiness() + 5);
        String text = "Купил мотоцикл";
        log.info(person.getName() + " " + text);
        adult.getAchievements().add(text);
        return adult;
    }
}
