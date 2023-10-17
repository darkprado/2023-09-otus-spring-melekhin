package ru.otus.hw.dao.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.ColumnPositionMappingStrategyBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.CsvConfig;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

@Repository
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final CsvConfig csvConfig;

    @Override
    public List<Question> findAll() {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream(csvConfig.getTestFileName())),
                StandardCharsets.UTF_8)
        )) {
            ColumnPositionMappingStrategy<QuestionDto> strategy =
                    new ColumnPositionMappingStrategyBuilder<QuestionDto>().build();
            strategy.setType(QuestionDto.class);
            String[] columns = new String[] { csvConfig.getQuestionField(), csvConfig.getAnswerField() };
            strategy.setColumnMapping(columns);

            CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder<QuestionDto>(fileReader)
                    .withSeparator(csvConfig.getDelimiter())
                    .withSkipLines(csvConfig.getSkipNumberRecord())
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
