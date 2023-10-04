package ru.otus.example.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author s.melekhin
 * @since 04 окт. 2023 г.
 */
@Data
@AllArgsConstructor
public class QuestionDto {

    private final String question;
    private final List<String> answerChoice;
    private final String correctAnswer;

}
