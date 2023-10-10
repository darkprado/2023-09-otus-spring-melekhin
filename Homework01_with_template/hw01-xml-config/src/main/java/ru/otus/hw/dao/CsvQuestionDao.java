package ru.otus.hw.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.ColumnPositionMappingStrategyBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.CsvProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final CsvProvider csvProvider;

    @Override
    public List<Question> findAll() {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream(csvProvider.getTestFileName())),
                StandardCharsets.UTF_8)
        )) {
            ColumnPositionMappingStrategy<QuestionDto> strategy =
                    new ColumnPositionMappingStrategyBuilder<QuestionDto>().build();
            strategy.setType(QuestionDto.class);
            String[] columns = new String[] { csvProvider.getQuestionField(), csvProvider.getAnswersField() };
            strategy.setColumnMapping(columns);

            CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder<QuestionDto>(fileReader)
                    .withSeparator(csvProvider.getDelimiter())
                    .withSkipLines(csvProvider.getSkipNumber())
                    .withMappingStrategy(strategy)
                    .build();

            return csvToBean.parse().stream()
                    .map(QuestionDto::toDomainObject)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new QuestionReadException(e.getMessage(), e);
        }
    }
}
