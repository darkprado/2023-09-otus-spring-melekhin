package ru.otus.hw.config.impl;

import java.util.Locale;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import ru.otus.hw.config.LocaleConfig;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.config.TestFileNameProvider;

@Data
@Component
@ConfigurationProperties(prefix = "test")
public class AppConfig implements TestConfig, TestFileNameProvider, LocaleConfig {

    private int rightAnswersCountToPass;

    private Locale locale;

    private Map<String, String> fileNameByLocaleTag;

    private char delimiter;

    private int skipNumberRecord;

    private String questionField;

    private String answerField;

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }

    @Override
    public String getTestFileName() {
        return fileNameByLocaleTag.get(locale.toLanguageTag());
    }

    @Override
    public char getDelimiter() {
        return delimiter;
    }

    @Override
    public int getSkipNumberRecord() {
        return skipNumberRecord;
    }

    @Override
    public String getQuestionField() {
        return questionField;
    }

    @Override
    public String getAnswerField() {
        return answerField;
    }
}
