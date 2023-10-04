package ru.otus.example.utils;

import java.util.List;

import ru.otus.example.dto.QuestionDto;

/**
 * @author s.melekhin
 * @since 04 окт. 2023 г.
 */
public interface Reader {

    List<QuestionDto> readFile();

}
