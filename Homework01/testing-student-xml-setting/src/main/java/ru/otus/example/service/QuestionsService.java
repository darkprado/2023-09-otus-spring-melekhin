package ru.otus.example.service;

import java.util.List;

import ru.otus.example.dto.QuestionDto;

/**
 * @author s.melekhin
 * @since 04 окт. 2023 г.
 */
public interface QuestionsService {

    List<QuestionDto> getQuestions();

}
