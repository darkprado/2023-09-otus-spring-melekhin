package ru.otus.example.utils.impl;

import java.util.Scanner;

import ru.otus.example.dto.StudentDto;

/**
 * @author s.melekhin
 * @since 04 окт. 2023 г.
 */
public class StudentIO {

    public static StudentDto createStudent() {
        StudentDto student = new StudentDto();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your lastname:");
        student.setLastname(scanner.nextLine());
        System.out.println("Enter your firstname:");
        student.setFirstname(scanner.nextLine());
        return student;
    }

}
