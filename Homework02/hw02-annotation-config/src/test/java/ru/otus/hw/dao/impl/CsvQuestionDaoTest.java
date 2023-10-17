package ru.otus.hw.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.otus.hw.config.CsvConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author s.melekhin
 * @since 14 окт. 2023 г.
 */
@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest {

    private final String FILE_NAME = "test.csv";
    private final char DELIMITER = ';';
    private final int SKIP_NUMBER_RECORD = 1;
    private final String QUESTION_FIELD = "text";
    private final String ANSWER_FIELD = "answers";

    @Mock
    private CsvConfig config;

    @InjectMocks
    private CsvQuestionDao dao;

    @Test
    @DisplayName("Проверка чтения вопросов из csv.")
    public void testReadQuestionsInCsv() {
        when(config.getTestFileName()).thenReturn(FILE_NAME);
        when(config.getQuestionField()).thenReturn(QUESTION_FIELD);
        when(config.getAnswerField()).thenReturn(ANSWER_FIELD);
        when(config.getDelimiter()).thenReturn(DELIMITER);
        when(config.getSkipNumberRecord()).thenReturn(SKIP_NUMBER_RECORD);
        assertEquals(2, dao.findAll().size());
    }

}