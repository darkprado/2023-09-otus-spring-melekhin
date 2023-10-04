package ru.otus.hw.config;

/**
 * @author s.melekhin
 * @since 04 окт. 2023 г.
 */
public interface ConstantProvider {

    String getExceptionMessage();
    Integer getSkipNumber();
    Character getDelimiter();
    String getQuestionField();
    String getAnswersField();

}
