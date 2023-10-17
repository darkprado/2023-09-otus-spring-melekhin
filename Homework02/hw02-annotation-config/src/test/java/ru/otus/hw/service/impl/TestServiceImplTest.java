package ru.otus.hw.service.impl;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.IOService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author s.melekhin
 * @since 14 окт. 2023 г.
 */
@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    private static final String QUESTION_TEXT = "questionText";

    private static final String ANSWER_TEXT = "answerText";
    private static final String INCORRECT_ANSWER_TEXT = "incorrectAnswerText";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";

    @Mock
    private IOService ioService;

    @Mock
    private QuestionDao dao;

    @InjectMocks
    private TestServiceImpl service;

    @Test
    @DisplayName("Проверка вызовов ioService и dao из TestServiceImpl.")
    public void testCallsIOServiceAndQuestionDaoInTestService() {
        Answer answer = new Answer(ANSWER_TEXT, true);
        Question question = new Question(QUESTION_TEXT, Collections.singletonList(answer));
        when(dao.findAll()).thenReturn(Collections.singletonList(question));
        service.executeTestFor(new Student(NAME, SURNAME));
        verify(dao, times(1)).findAll();
        verify(ioService, times(1)).printLine(anyString());
    }

    @Test
    @DisplayName("Проверка правильного ответа тестирования.")
    public void testCorrectResult() {
        Answer answer = new Answer(ANSWER_TEXT, true);
        Question question = new Question(QUESTION_TEXT, Collections.singletonList(answer));
        when(dao.findAll()).thenReturn(Collections.singletonList(question));
        when(ioService.readStringWithPrompt(anyString())).thenReturn(ANSWER_TEXT);
        TestResult testResult = service.executeTestFor(new Student(NAME, SURNAME));
        Assertions.assertEquals(1, testResult.getRightAnswersCount());
    }

    @Test
    @DisplayName("Проверка не правильного ответа тестирования.")
    public void testIncorrectResultFail() {
        Answer answer = new Answer(ANSWER_TEXT, true);
        Question question = new Question(QUESTION_TEXT, Collections.singletonList(answer));
        when(dao.findAll()).thenReturn(Collections.singletonList(question));
        when(ioService.readStringWithPrompt(anyString())).thenReturn(INCORRECT_ANSWER_TEXT);
        TestResult testResult = service.executeTestFor(new Student(NAME, SURNAME));
        Assertions.assertEquals(0, testResult.getRightAnswersCount());
    }

}