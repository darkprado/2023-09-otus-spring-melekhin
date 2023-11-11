package ru.otus.hw.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.service.TestRunnerService;

/**
 * @author s.melekhin
 * @since 17 окт. 2023 г.
 */
@RequiredArgsConstructor
@Component
public class TestingApplicationRunner implements CommandLineRunner {

    private final TestRunnerService testRunnerService;

    @Override
    public void run(String... args) {
        testRunnerService.run();
    }
}
