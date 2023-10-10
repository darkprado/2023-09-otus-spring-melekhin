package ru.otus.example.service.impl;

import java.util.List;

import lombok.AllArgsConstructor;
import ru.otus.example.dto.QuestionDto;
import ru.otus.example.service.QuestionsService;
import ru.otus.example.utils.Reader;
import ru.otus.example.utils.impl.CsvReader;

/**
 * @author s.melekhin
 * @since 04 окт. 2023 г.
 */
@AllArgsConstructor
public class QuestionsServiceImpl implements QuestionsService {

    private final Reader reader;

    @Override
    public List<QuestionDto> getQuestions() {
        return reader.readFile();
    }

}
