package ru.otus.hw.service;

import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName("Проверка вызовов ioService и dao из TestServiceImpl.")
    public void testCallsIOServiceAndQuestionDaoInTestService() {
        Answer answer = new Answer(ANSWER_TEXT, true);
        Question question = new Question(QUESTION_TEXT, Collections.singletonList(answer));
        when(dao.findAll()).thenReturn(Collections.singletonList(question));
        service.executeTest();
        verify(dao, times(1)).findAll();
        verify(ioService, times(3)).printLine(anyString());
    }

}