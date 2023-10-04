package ru.otus.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.otus.example.service.StudentTestingService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        StudentTestingService studentTestingService = context.getBean(StudentTestingService.class);
        studentTestingService.test();
        context.close();
    }

}
