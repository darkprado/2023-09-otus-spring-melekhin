package ru.otus.hw.commands;

import java.util.stream.Collectors;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.models.Author;
import ru.otus.hw.services.AuthorService;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    private final AuthorConverter authorConverter;

    @ShellMethod(value = "Find all authors", key = "aa")
    public String findAllAuthors() {
        return authorService.findAll().stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Delete author", key = "da")
    public void deleteGenre(long id) {
        authorService.deleteById(id);
    }

    @ShellMethod(value = "Find author by id", key = "fa")
    public String findById(long id) {
        return authorService.findById(id)
                .map(authorConverter::authorToString)
                .orElse(String.format("Author with id = %s not found", id));
    }

    @ShellMethod(value = "Save author", key = "sa")
    public String saveGenre(String name) {
        Author author = new Author();
        author.setName(name);
        return authorConverter.authorToString(authorService.save(author));
    }

    @ShellMethod(value = "Update genre", key = "ua")
    public String updateGenre(long id, String name) {
        return authorConverter.authorToString(authorService.save(new Author(id, name)));
    }
}
