package ru.otus.example.utils.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import ru.otus.example.dto.QuestionDto;
import ru.otus.example.enums.CsvPositions;
import ru.otus.example.utils.Reader;

/**
 * @author s.melekhin
 * @since 04 окт. 2023 г.
 */
public class CsvReader implements Reader {

    private static final char DELIMITER = ';';
    private static final String ANSWER_DELIMITER = ":";

    private String fileName;

    public CsvReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<QuestionDto> readFile() {

        try(BufferedReader fileReader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName)), StandardCharsets.UTF_8)
        )) {
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.newFormat(DELIMITER));
            return csvParser.getRecords()
                    .stream()
                    .map(csvRecord -> new QuestionDto(csvRecord.get(CsvPositions.QUESTION_POSITION.getVal()),
                            Arrays.asList(csvRecord.get(CsvPositions.ANSWER_CHOICE_POSITION.getVal()).split(ANSWER_DELIMITER)),
                            csvRecord.get(CsvPositions.CORRECT_ANSWER_POSITION.getVal()))).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
