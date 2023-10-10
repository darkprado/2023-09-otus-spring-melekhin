package ru.otus.hw.config;

/**
 * @author s.melekhin
 * @since 04 окт. 2023 г.
 */
public interface CsvProvider {

    int getSkipNumber();

    char getDelimiter();

    String getQuestionField();

    String getAnswersField();

    String getTestFileName();

}
