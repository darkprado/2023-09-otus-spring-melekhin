package ru.otus.hw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class AppConfig implements TestConfig, CsvConfig {

    @Value("${test.rightAnswersCountToPass:1}")
    private int rightAnswersCountToPass;

    @Value("${csv.fileName:file.csv}")
    private String testFileName;

    @Value("${csv.delimiter:''|''}")
    private char delimiter;

    @Value("${csv.skipNumberRecord:0}")
    private int skipNumberRecord;

    @Value("${csv.questionField:question}")
    private String questionField;

    @Value("${csv.answerField:answer}")
    private String answerField;

}
