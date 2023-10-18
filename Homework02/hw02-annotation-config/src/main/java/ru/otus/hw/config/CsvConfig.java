package ru.otus.hw.config;

public interface CsvConfig {

    String getTestFileName();

    char getDelimiter();

    int getSkipNumberRecord();

    String getQuestionField();

    String getAnswerField();

}
