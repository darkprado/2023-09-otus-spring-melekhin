package ru.otus.hw.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AppConfig implements CsvProvider {

    private String testFileName;

    private int skipNumber;

    private char delimiter;

    private String questionField;

    private String answersField;

    @Override
    public String getTestFileName() {
        return testFileName;
    }

    @Override
    public int getSkipNumber() {
        return skipNumber;
    }

    @Override
    public char getDelimiter() {
        return delimiter;
    }

    @Override
    public String getQuestionField() {
        return questionField;
    }

    @Override
    public String getAnswersField() {
        return answersField;
    }
}
