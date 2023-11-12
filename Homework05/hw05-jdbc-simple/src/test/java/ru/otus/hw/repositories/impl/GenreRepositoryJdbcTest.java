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

import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author s.melekhin
 * @since 10 нояб. 2023 г.
 */
@DisplayName("Репозиторий на основе Jdbc для работы с авторами")
@JdbcTest
@Import(GenreRepositoryJdbc.class)
class GenreRepositoryJdbcTest {

    @Autowired
    private GenreRepositoryJdbc repositoryJdbc;

    private List<Genre> dbGenres;

    @BeforeEach
    void setUp() {
        dbGenres = getDbGenres();
    }

    @DisplayName("Должен загружать жанр по id")
    @ParameterizedTest
    @MethodSource("getDbGenres")
    void shouldReturnCorrectGenreById(Genre expectedGenre) {
        var actualGenre = repositoryJdbc.findById(expectedGenre.getId());
        assertThat(actualGenre).isPresent()
                .get()
                .isEqualTo(expectedGenre);
    }

    @DisplayName("Должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenresList() {
        var actualGenres = repositoryJdbc.findAll();
        var expectedGenres = dbGenres;

        assertThat(actualGenres).containsExactlyElementsOf(expectedGenres);
        actualGenres.forEach(System.out::println);
    }

    @DisplayName("Должен сохранять новый жанр")
    @Test
    void shouldSaveNewGenre() {
        var expectedGenre = new Genre(0, "Genre_100500");
        var returnedGenre = repositoryJdbc.save(expectedGenre);
        assertThat(returnedGenre).isNotNull()
                .matches(author -> author.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedGenre);

        assertThat(repositoryJdbc.findById(returnedGenre.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedGenre);
    }

    @DisplayName("Должен сохранять измененный жанр")
    @Test
    void shouldSaveUpdatedGenre() {
        var expectedGenre = new Genre(1L, "Genre_100500");

        assertThat(repositoryJdbc.findById(expectedGenre.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedGenre);

        var returnedBook = repositoryJdbc.save(expectedGenre);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedGenre);

        assertThat(repositoryJdbc.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @DisplayName("Должен удалять жанр по id")
    @Test
    void shouldDeleteGenre() {
        assertThat(repositoryJdbc.findById(1L)).isPresent();
        repositoryJdbc.deleteById(1L);
        assertThat(repositoryJdbc.findById(1L)).isEmpty();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

}