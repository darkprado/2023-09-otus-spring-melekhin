package ru.otus.example.dto;

import lombok.Data;

/**
 * @author s.melekhin
 * @since 04 окт. 2023 г.
 */
@Data
public class StudentDto {

    private String firstname;
    private String lastname;

    @Override
    public String toString() {
        return String.format("%s %s", firstname, lastname);
    }

}
