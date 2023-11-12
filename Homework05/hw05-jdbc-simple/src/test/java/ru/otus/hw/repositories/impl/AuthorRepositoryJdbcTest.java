package ru.otus.hw.repositories.impl;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import ru.otus.hw.models.Author;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author s.melekhin
 * @since 10 нояб. 2023 г.
 */
@DisplayName("Репозиторий на основе Jdbc для работы с авторами")
@JdbcTest
@Import(AuthorRepositoryJdbc.class)
class AuthorRepositoryJdbcTest {

    @Autowired
    private AuthorRepositoryJdbc repositoryJdbc;

    private List<Author> dbAuthors;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
    }

    @DisplayName("Должен загружать автора по id")
    @ParameterizedTest
    @MethodSource("getDbAuthors")
    void shouldReturnCorrectAuthorById(Author expectedAuthor) {
        var actualAuthor = repositoryJdbc.findById(expectedAuthor.getId());
        assertThat(actualAuthor).isPresent()
                .get()
                .isEqualTo(expectedAuthor);
    }

    @DisplayName("Должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsList() {
        var actualAuthors = repositoryJdbc.findAll();
        var expectedAuthors = dbAuthors;

        assertThat(actualAuthors).containsExactlyElementsOf(expectedAuthors);
        actualAuthors.forEach(System.out::println);
    }

    @DisplayName("Должен сохранять нового автора")
    @Test
    void shouldSaveNewAuthor() {
        var expectedAuthor = new Author(0, "Author_100500");
        var returnedAuthor = repositoryJdbc.save(expectedAuthor);
        assertThat(returnedAuthor).isNotNull()
                .matches(author -> author.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedAuthor);

        assertThat(repositoryJdbc.findById(returnedAuthor.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedAuthor);
    }

    @DisplayName("Должен сохранять измененного автора")
    @Test
    void shouldSaveUpdatedAuthor() {
        var expectedAuthor = new Author(1L, "Author_100500");

        assertThat(repositoryJdbc.findById(expectedAuthor.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedAuthor);

        var returnedBook = repositoryJdbc.save(expectedAuthor);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedAuthor);

        assertThat(repositoryJdbc.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @DisplayName("Должен удалять автора по id")
    @Test
    void shouldDeleteAuthor() {
        assertThat(repositoryJdbc.findById(1L)).isPresent();
        repositoryJdbc.deleteById(1L);
        assertThat(repositoryJdbc.findById(1L)).isEmpty();
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

}