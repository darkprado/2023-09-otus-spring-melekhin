package ru.otus.hw.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ru.otus.hw.commands.TestCommands;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.service.ResultService;
import ru.otus.hw.service.impl.LocalizedMessagesServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author s.melekhin
 * @since 08 нояб. 2023 г.
 */
@SpringBootTest
class CsvQuestionDaoTest {

    private static final String FILE_NAME = "questions.csv";
    private static final char DELIMITER = ';';
    private static final int SKIP_NUMBER_RECORD = 1;
    private static final String QUESTION_FIELD = "text";
    private static final String ANSWER_FIELD = "answers";

    @MockBean
    private TestFileNameProvider fileNameProvider;

    @MockBean
    private TestCommands testCommands;

    @MockBean
    private LocalizedMessagesServiceImpl localizedMessagesService;

    @MockBean
    private ResultService resultService;

    @Autowired
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