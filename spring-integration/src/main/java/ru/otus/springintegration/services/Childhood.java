package ru.otus.springintegration.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.springintegration.domain.Person;

@Service
@Slf4j
public class Childhood {

    public Person babyTime(Person person) {
        Person baby = new Person(person.getName(), person.getGender());
        baby.setAge(RandomUtils.nextInt(1, 2) == 2 ? 7 : 6);
        baby.setHappiness(person.getHappiness() + RandomUtils.nextInt(0, 10) - 5);
        log.info(person.getName() + " пошел в школу.");
        return baby;
    }

    public Person schoolTime(Person person) {
        Person student = new Person(person.getName(), person.getGender(), person.getAchievements());
        student.setAge(person.getAge() + 11);
        int delta = RandomUtils.nextInt(0, 30) - 15;
        student.setHappiness(person.getHappiness() + delta);
        log.info(person.getName() + " окончил школу");
        student.getAchievements().add("Окончил школу");
        return student;
    }


    public Person universityTime(Person person) {
        Person student = new Person(person.getName(), person.getGender(), person.getAchievements());
        student.setAge(person.getAge() + 6);
        int delta = RandomUtils.nextInt(0, 20) - 5;
        student.setHappiness(person.getHappiness() + delta);
        log.info(person.getName() + " окончил университет");
        student.getAchievements().add("Окончил университет");
        return student;
    }

}
