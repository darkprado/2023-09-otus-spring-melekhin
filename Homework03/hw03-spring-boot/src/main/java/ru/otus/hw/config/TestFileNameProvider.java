package ru.otus.hw.config;

public interface TestFileNameProvider {
    String getTestFileName();

    char getDelimiter();

    int getSkipNumberRecord();

    String getQuestionField();

    String getAnswerField();
}
