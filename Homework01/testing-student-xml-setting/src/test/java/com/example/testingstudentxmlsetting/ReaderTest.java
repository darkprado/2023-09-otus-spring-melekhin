package com.example.testingstudentxmlsetting;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.otus.example.utils.impl.CsvReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Reader class")
class ReaderTest {

    @DisplayName("check the number of questions")
    @Test
    public void checkNumberQuestions() {
        CsvReader reader = new CsvReader("questions-test.csv");
        assertEquals(2, reader.readFile().size());
    }

}
