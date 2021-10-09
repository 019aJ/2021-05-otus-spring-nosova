package ru.otus.springintegration.gateways;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.springintegration.domain.Person;

import java.util.List;

@MessagingGateway
public interface Life {
    @Gateway(requestChannel = "peopleChannel")
    List<Person> life(List<Person> p);
}
