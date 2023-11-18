package ru.otus.hw.repositories.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import ru.otus.hw.models.Author;
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

    @Autowired
    private TestEntityManager em;

    @DisplayName("Должен загружать жанр по id")
    @Test
    void shouldReturnCorrectGenreById() {
        var expectedGenre = em.find(Genre.class, 1);
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
        var expectedAuthors = em.getEntityManager().createQuery("select g from Genre g", Genre.class).getResultList();
        for (int i = 0; i < actualGenres.size(); i++) {
            assertThat(actualGenres.get(i))
                    .usingRecursiveComparison()
                    .isEqualTo(expectedAuthors.get(i));
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

}