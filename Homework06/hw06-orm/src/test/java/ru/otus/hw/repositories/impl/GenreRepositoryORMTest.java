package ru.otus.hw.repositories.impl;

import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author s.melekhin
 * @since 10 нояб. 2023 г.
 */
@DisplayName("Репозиторий на основе ORM для работы с жанрами")
@DataJpaTest
@Import(GenreRepositoryORM.class)
class GenreRepositoryORMTest {

    @Autowired
    private GenreRepositoryORM repository;

    private List<Genre> dbGenres;

    @BeforeEach
    void setUp() {
        dbGenres = getDbGenres();
    }

    @DisplayName("Должен загружать жанр по id")
    @ParameterizedTest
    @MethodSource("getDbGenres")
    void shouldReturnCorrectGenreById(Genre expectedGenre) {
        var actualGenre = repository.findById(expectedGenre.getId());
        assertThat(actualGenre).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);
    }

    @DisplayName("Должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenresList() {
        var actualGenres = repository.findAll();
        for (int i = 0; i < actualGenres.size(); i++) {
            assertThat(actualGenres.get(i))
                    .usingRecursiveComparison()
                    .isEqualTo(dbGenres.get(i));
        }
    }

    @DisplayName("Должен сохранять новый жанр")
    @Test
    void shouldSaveNewGenre() {
        var expectedGenre = new Genre(null, "Genre_100500");
        var returnedGenre = repository.save(expectedGenre);
        assertThat(returnedGenre).isNotNull()
                .matches(author -> author.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedGenre);

        assertThat(repository.findById(returnedGenre.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedGenre);
    }

    @DisplayName("Должен сохранять измененный жанр")
    @Test
    void shouldSaveUpdatedGenre() {
        var expectedGenre = new Genre(1L, "Genre_100500");

        assertThat(repository.findById(expectedGenre.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedGenre);

        var returnedGenre = repository.save(expectedGenre);
        assertThat(returnedGenre).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedGenre);

        assertThat(repository.findById(returnedGenre.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(returnedGenre);
    }

    @DisplayName("Должен удалять жанр по id")
    @Test
    void shouldDeleteGenre() {
        long before = repository.findAll().size();
        repository.deleteById(1L);
        long after = repository.findAll().size();
        assertThat(before).isGreaterThan(after);
    }

    private static List<Genre> getDbGenres() {
        return LongStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

}