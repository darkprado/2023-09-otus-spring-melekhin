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

import ru.otus.hw.models.Author;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author s.melekhin
 * @since 10 нояб. 2023 г.
 */
@DisplayName("Репозиторий на основе ORM для работы с авторами")
@DataJpaTest
@Import(AuthorRepositoryORM.class)
class AuthorRepositoryORMTest {

    @Autowired
    private AuthorRepositoryORM repository;

    private List<Author> dbAuthors;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
    }

    @DisplayName("Должен загружать автора по id")
    @ParameterizedTest
    @MethodSource("getDbAuthors")
    void shouldReturnCorrectAuthorById(Author expectedAuthor) {
        var actualAuthor = repository.findById(expectedAuthor.getId());
        assertThat(actualAuthor).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

    @DisplayName("Должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsList() {
        var actualAuthors = repository.findAll();
        for (int i = 0; i < actualAuthors.size(); i++) {
            assertThat(actualAuthors.get(i))
                    .usingRecursiveComparison()
                    .isEqualTo(dbAuthors.get(i));
        }
    }

    @DisplayName("Должен сохранять нового автора")
    @Test
    void shouldSaveNewAuthor() {
        var expectedAuthor = new Author(null, "Author_100500");
        var returnedAuthor = repository.save(expectedAuthor);
        assertThat(returnedAuthor).isNotNull()
                .matches(author -> author.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedAuthor);

        assertThat(repository.findById(returnedAuthor.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedAuthor);
    }

    @DisplayName("Должен сохранять измененного автора")
    @Test
    void shouldSaveUpdatedAuthor() {
        var expectedAuthor = new Author(1L, "Author_100500");

        assertThat(repository.findById(expectedAuthor.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedAuthor);

        var returnedAuthor = repository.save(expectedAuthor);
        assertThat(returnedAuthor).isNotNull()
                .matches(author -> author.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedAuthor);

        assertThat(repository.findById(returnedAuthor.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(returnedAuthor);
    }

    @DisplayName("Должен удалять автора по id")
    @Test
    void shouldDeleteAuthor() {
        long before = repository.findAll().size();
        repository.deleteById(1L);
        long after = repository.findAll().size();
        assertThat(before).isGreaterThan(after);
    }

    private static List<Author> getDbAuthors() {
        return LongStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

}