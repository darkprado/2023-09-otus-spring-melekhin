package ru.otus.hw.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import ru.otus.hw.config.TestFileNameProvider;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author s.melekhin
 * @since 08 нояб. 2023 г.
 */
@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest {

    private static final String FILE_NAME = "questions.csv";
    private static final char DELIMITER = ';';
    private static final int SKIP_NUMBER_RECORD = 1;
    private static final String QUESTION_FIELD = "text";
    private static final String ANSWER_FIELD = "answers";

    @Mock
    private TestFileNameProvider fileNameProvider;

    @InjectMocks
    private CsvQuestionDao dao;

    @Test
    @DisplayName("Проверка чтения вопросов из csv.")
    public void testReadQuestionsInCsv() {
        when(fileNameProvider.getTestFileName()).thenReturn(FILE_NAME);
        when(fileNameProvider.getQuestionField()).thenReturn(QUESTION_FIELD);
        when(fileNameProvider.getAnswerField()).thenReturn(ANSWER_FIELD);
        when(fileNameProvider.getDelimiter()).thenReturn(DELIMITER);
        when(fileNameProvider.getSkipNumberRecord()).thenReturn(SKIP_NUMBER_RECORD);
        assertEquals(2, dao.findAll().size());
    }

}