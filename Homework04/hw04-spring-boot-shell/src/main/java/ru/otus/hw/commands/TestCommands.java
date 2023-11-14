package ru.otus.hw.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.service.TestRunnerService;

/**
 * @author s.melekhin
 * @since 11 нояб. 2023 г.
 */
@RequiredArgsConstructor
@ShellComponent
public class TestCommands {

    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "Start testing", key = "start")
    public void startTesting() {
        testRunnerService.run();
    }

    @ShellMethod(value = "Close program", key = "close")
    public void closeProgram() {
        System.exit(0);
    }

}
