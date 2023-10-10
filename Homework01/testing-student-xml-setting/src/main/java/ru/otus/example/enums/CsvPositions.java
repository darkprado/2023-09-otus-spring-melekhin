package ru.otus.example.enums;

/**
 * @author s.melekhin
 * @since 04 окт. 2023 г.
 */
public enum CsvPositions {

    QUESTION_POSITION(0),
    ANSWER_CHOICE_POSITION(1),
    CORRECT_ANSWER_POSITION(2);
    private final int val;
    CsvPositions(int v) { val = v; }
    public int getVal() { return val; }

}
