package ru.otus.hw.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao dao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        printQuestions(dao.findAll());
    }

    private void printQuestions(List<Question> questions) {
        for (var question : questions) {
            ioService.printLine(question.text());
            printAnswers(question.answers());
        }
    }

    private void printAnswers(List<Answer> answers) {
        for (var answer : answers) {
            ioService.printLine(answer.text());
        }
    }
}
