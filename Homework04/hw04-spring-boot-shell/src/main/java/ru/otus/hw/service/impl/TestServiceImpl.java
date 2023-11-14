package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.TestService;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            ioService.printLine(question.text());
            for (var answer : question.answers()) {
                ioService.printLine(answer.text());
            }
            String userAnswer = ioService.readString();
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
