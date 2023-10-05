package ru.otus.hw.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * @author s.melekhin
 * @since 05 окт. 2023 г.
 */
@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    private static final String QUESTION_TEXT = "questionText";

    private static final String ANSWER_TEXT = "answerText";

    @Mock
    private IOService ioService;

    @Mock
    private QuestionDao dao;

    @InjectMocks
    private TestServiceImpl service;

    private Question question;
    private Answer answer;

    @BeforeEach
    void init() {
        question = mock(Question.class);
        answer = mock(Answer.class);
    }

    @Test
    @DisplayName("Проверка вызовов ioService и dao из TestServiceImpl. Ожидаемый результат: dao - 1 раз, ioService - 3 раза(приветствие, вопрос, ответ).")
    public void test01() {
        when(dao.findAll()).thenReturn(Collections.singletonList(question));
        when(question.text()).thenReturn(QUESTION_TEXT);
        when(question.answers()).thenReturn(Collections.singletonList(answer));
        when(answer.text()).thenReturn(ANSWER_TEXT);
        service.executeTest();
        verify(dao, times(1)).findAll();
        verify(ioService, times(3)).printLine(anyString());
    }

}