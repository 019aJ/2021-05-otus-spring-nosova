package ru.otus.studenttestappboot.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
@PropertySource("classpath:application.yaml")
public class LocalizationServiceImpl implements LocalizationService {
    private final String localeName;
    private final MessageSource messages;
    private final String suffix;

    public LocalizationServiceImpl(@Value("${localeName}") String localeName, MessageSource messages) {
        this.localeName = localeName;
        this.messages = messages;
        suffix = messages.getMessage("localeSuffix", new String[0], Locale.forLanguageTag(localeName));
    }

    @Override
    public String localizeResourceName(String resourceDefaultName) {
        if (!StringUtils.isEmpty(resourceDefaultName) && !StringUtils.isEmpty(localeName)) {
            if (FilenameUtils.indexOfExtension(resourceDefaultName) >= 0) {
                String extension = FilenameUtils.getExtension(resourceDefaultName);
                return resourceDefaultName.substring(0, FilenameUtils.indexOfExtension(resourceDefaultName)) + suffix + "." + extension;
            } else {
                return resourceDefaultName + suffix;
            }
        }
        return resourceDefaultName;
    }
}
