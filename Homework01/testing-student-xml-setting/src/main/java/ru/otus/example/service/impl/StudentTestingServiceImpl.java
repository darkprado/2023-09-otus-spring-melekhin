package ru.otus.example.service.impl;

import java.util.List;

import lombok.AllArgsConstructor;
import ru.otus.example.dto.StudentDto;
import ru.otus.example.service.QuestionsService;
import ru.otus.example.service.StudentTestingService;
import ru.otus.example.dto.QuestionDto;
import ru.otus.example.utils.impl.StudentIO;

/**
 * @author s.melekhin
 * @since 04 окт. 2023 г.
 */
@AllArgsConstructor
public class StudentTestingServiceImpl implements StudentTestingService {

    private final QuestionsService qService;

    @Override
    public void test() {
        startTesting(welcomeStudent());
    }

    private StudentDto welcomeStudent() {
        StudentDto student = StudentIO.createStudent();
        System.out.printf("Hello %s %n", student);
        return student;
    }

    public void startTesting(StudentDto studentDto) {
        List<QuestionDto> questionDtoList = qService.getQuestions();
        questionDtoList.forEach(questionDto -> {
            System.out.printf("Question: %s%n", questionDto.getQuestion());
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Choose an answer:");
            questionDto.getAnswerChoice().forEach(System.out::println);
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("Correct answer: %s%n", questionDto.getCorrectAnswer());
            System.out.println("=========================================");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
