package ru.otus.studenttestappboot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import ru.otus.studenttestappboot.service.LocalizationService;
import ru.otus.studenttestappboot.service.LocalizationServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Проверка для ru-локали")

@SpringBootTest
public class LocalizationRuServiceTest {
    @Autowired
    LocalizationService localizationService;

    @DisplayName("Простая проверка применения к имени файла: answers.csv->answers_ru.csv")
    @Test
    public void rightFileNameCheck() {
        String name = localizationService.localizeResourceName("answers.csv");
        assertEquals("answers_ru.csv", name);
    }

    @DisplayName("Файл без расширения: answers->answers_ru")
    @Test
    public void noExtension() {
        String name = localizationService.localizeResourceName("answers");
        assertEquals("answers_ru", name);
    }

    @DisplayName("Инвалидный файл без расширения: answers.->answers_ru.")
    @Test
    public void noExtension2() {
        String name = localizationService.localizeResourceName("answers.");
        assertEquals("answers_ru.", name);
    }

    @DisplayName("Проверка с null")
    @Test
    public void nullFile() {
        String name = localizationService.localizeResourceName(null);
        assertNull(name);
    }

    @DisplayName("Проверка с пустой строкой")
    @Test
    public void emptyFile() {
        String name = localizationService.localizeResourceName("");
        assertEquals("", name);
    }

    @TestConfiguration
    static class NestedConfiguration {

        @Bean
        LocalizationService localizationService(MessageSource msg) {
            return new LocalizationServiceImpl("ru-RU", msg);
        }
    }
}
