package ru.otus.studenttestappshell.service;

public interface LocalizationService {
    String localizeResourceName(String resourceDefaultName);
    String localize(String message);
}
