package ru.otus.hw.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.TestService;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            String userAnswer = ioService.readStringWithPrompt(question.text());
            var isAnswerValid = question.answers().stream()
                    .filter(answer -> answer.text().equals(userAnswer))
                    .findFirst()
                    .map(Answer::isCorrect)
                    .orElse(false);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
